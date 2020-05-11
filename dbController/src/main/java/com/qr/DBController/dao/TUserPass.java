package com.qr.DBController.dao;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "t_user_pass", schema = "public", catalog = "qr")
@Indexed
public class TUserPass {

    @Id
    @Column(name = "id")
    @Getter
    private long id;

    @Column(name = "pass_type_id")
    @Getter
    @Setter
    private long passTypeId;

    @Column(name = "user_id")
    @Getter
    @Setter
    private long userId;

    @Column(name = "start_date")
    @Getter
    @Setter
    private Date startDate;

    @Column(name = "is_valid")
    @Getter
    @Setter
    private boolean isValid;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @Getter
    private TUsers user;

    @ManyToOne
    @JoinColumn(name = "pass_type_id", insertable = false, updatable = false)
    @Getter
    private TDicPassType passType;


}
