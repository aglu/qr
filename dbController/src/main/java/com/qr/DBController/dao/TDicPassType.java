package com.qr.DBController.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;


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
    private boolean isAvtive;
}
