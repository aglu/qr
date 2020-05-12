package com.qr.dbcontroller.dao;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.query.Query;
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

@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "t_dic_pass_type", schema = "public", catalog = "qr")
@Indexed
public class TDicPassType {
    @Id
    @Column(name = "id")
    @Getter
    private long id;

    @Column(name = "name")
    @Getter
    private String name;

    @Column(name = "duration")
    @Getter
    private long duration;

    @Column(name = "is_avtive")
    @Getter
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO, termVector = TermVector.YES)
    private boolean isAvtive;

    public static List<TDicPassType> getDicPassType(Session session) throws InterruptedException {
        String sql = "From " + TDicPassType.class.getSimpleName();
        System.out.println("sql = " + sql);

        List<TDicPassType> types = session.createQuery(sql).list();

        return types;
    }

}
