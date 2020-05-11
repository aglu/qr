package com.qr.dbcontroller.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.search.annotations.Indexed;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "t_user_attributes", schema = "public", catalog = "qr")
@Indexed
@Accessors(chain = true)
public class TUserAttributes {
    @Id
    @Column(name = "id")
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    @Getter
    @Setter
    private long userId;

    @Column(name = "attr_name")
    @Getter
    @Setter
    private long attrName;

    @Column(name = "attr_value")
    @Getter
    @Setter
    private String attrValue;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @Getter
    @JsonIgnore
    private TUsers user;

    @ManyToOne
    @JoinColumn(name = "attr_name", insertable = false, updatable = false)
    @Getter
    @JsonIgnore
    private TDicUserAttributes dicAttributes;

}
