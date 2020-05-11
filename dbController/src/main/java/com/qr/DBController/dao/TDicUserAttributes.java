package com.qr.dbcontroller.dao;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;


import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "t_dic_user_attributes", schema = "public", catalog = "qr")
@Indexed
public class TDicUserAttributes {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Basic
    @Column(name = "value")
    @Getter
    private String value;

    @Basic
    @Column(name = "description")
    @Getter
    private String description;

}
