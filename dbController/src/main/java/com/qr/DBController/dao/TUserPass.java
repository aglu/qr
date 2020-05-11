package com.qr.DBController.dao;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.qr.DBController.exceptions.EmptyParamsException;
import com.qr.DBController.exceptions.NotFoundDataExceptions;
import org.hibernate.Session;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public static TUserPass getById(Session session, Long id) throws EmptyParamsException, NotFoundDataExceptions {
        if (id == null) {
            throw new EmptyParamsException("id");
        }
        TUserPass userPass = session.get(TUserPass.class, id);
        if (userPass == null) {
            throw new NotFoundDataExceptions("TUserPass");
        }
        return userPass;
    }

}
