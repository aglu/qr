package com.qr.DBController.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
import org.hibernate.search.annotations.TermVector;
import org.hibernate.search.query.dsl.QueryBuilder;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "t_user_sessions", schema = "public", catalog = "qr")
@Indexed
public class TUserSessions {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Column(name = "user_id")
    @Getter
    @Setter
    private long userId;

    @Column(name = "session_key")
    @Getter
    @Setter
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO, termVector = TermVector.YES)
    private String sessionKey;

    @Column(name = "session_date")
    @Getter
    @Setter
    private Date sessionDate;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @Getter
    private TUsers user;

    public static List<TUserSessions> getBySessionKey(Session session, String key) throws EmptyParamsException, NotFoundDataExceptions, InterruptedException {
        if (key == null) {
            throw new EmptyParamsException("key");
        }

        FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(session);
        fullTextSession.createIndexer().startAndWait();
        QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(TUserSessions.class).get();
        org.apache.lucene.search.Query query = qb.keyword().onField("sessionKey")
                .matching(key).createQuery();
        List<TUserSessions> sessions = fullTextSession.createFullTextQuery(query).list();
        if (MiscUtil.isEmpty(sessions)) {
            throw new NotFoundDataExceptions("TUserSessions");
        }
        return sessions;
    }
}
