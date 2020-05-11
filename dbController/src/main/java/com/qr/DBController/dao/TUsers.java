package com.qr.dbcontroller.dao;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.qr.dbcontroller.util.MiscUtil;
import com.qr.dbcontroller.exceptions.EmptyParamsException;
import com.qr.dbcontroller.exceptions.NotFoundDataExceptions;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TermVector;
import org.hibernate.search.query.dsl.QueryBuilder;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "t_users", schema = "public", catalog = "qr")
@Indexed
public class TUsers {

    @Id
    @Column(name = "id")
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "phone")
    @Getter
    @Setter
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO, termVector = TermVector.YES)
    private String phone;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    @Getter
    @Setter
    private List<TUserAttributes> attributes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    @Getter
    private List<TUserPass> userPasses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    @Getter
    private List<TUserAuthorizationCode> keys;

    /**
     * Поиск пользователя по ID
     *
     * @param session Текущая сессия
     * @param id      Идентификатор пользователя
     * @return Пользователь
     */
    public static TUsers getById(Session session, Long id) {
        if (id == null) {
            return null;
        }
        return session.get(TUsers.class, id);
    }

    public static List<TUsers> getByPhone(Session session, String phone) throws EmptyParamsException, InterruptedException, NotFoundDataExceptions {
        if (phone == null) {
            throw new EmptyParamsException("phone");
        }
        FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(session);
        fullTextSession.createIndexer().startAndWait();
        QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(TUsers.class).get();
        org.apache.lucene.search.Query query = qb.keyword().onField("phone")
                .matching(phone).createQuery();
        List<TUsers> users = fullTextSession.createFullTextQuery(query).list();
        if (MiscUtil.isEmpty(users)) {
            throw new NotFoundDataExceptions("TUsers");
        }
        return users;
    }

}
