package com.qr.DBController.dao;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.qr.DBController.Util.MiscUtil;
import com.qr.DBController.exceptions.EmptyParamsException;
import com.qr.DBController.exceptions.NotFoundDataExceptions;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.query.dsl.QueryBuilder;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "t_user_authorization_code", schema = "public", catalog = "qr")
@Indexed
public class TUserAuthorizationCode {

    @Id
    @Column(name = "id")
    @Getter
    private long id;

    @Column(name = "key")
    @Getter
    @Setter
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String key;

    @Column(name = "user_id")
    @Getter
    @Setter
    private long userId;

    @Column(name = "is_using")
    @Getter
    @Setter
    private boolean isUsing;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @Getter
    private TUsers user;


    public static List<TUserAuthorizationCode> checkAuthCode(Session session, long userId, String key) throws EmptyParamsException, NotFoundDataExceptions {
        if (MiscUtil.isEmpty(userId)) {
            throw new EmptyParamsException("Идентификатор пользователя");
        }
        if (MiscUtil.isEmpty(key)) {
            throw new EmptyParamsException("Ключ идентификации");
        }
        FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(session);
        QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(TUserAuthorizationCode.class).get();
        org.apache.lucene.search.Query query = qb.keyword().wildcard()
                .onField("key").matching(key)
                .createQuery();
        List<TUserAuthorizationCode> result = fullTextSession.createFullTextQuery(query).list();
        if (MiscUtil.isEmpty(result)) {
            throw new NotFoundDataExceptions("TUserAuthorizationCode");
        }
        return result;
    }

}
