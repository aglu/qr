package org.qr.ajaxServlet.process;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import com.mchange.v2.cfg.PropertiesConfigSource;
import com.qr.DBController.dao.TUserPass;
import com.qr.DBController.dao.TUserSessions;
import com.qr.DBController.dao.TUsers;
import com.qr.DBController.exceptions.EmptyParamsException;
import com.qr.DBController.exceptions.NotFoundDataExceptions;
import com.qr.DBController.pools.MainPool;
import org.hibernate.Session;
import org.qr.ajaxServlet.ApiException;

public class CreatePass {

    public static void execute(String sessionId, Map<String, Object> request, Map<String, Object> jsonData) throws ApiException, EmptyParamsException, InterruptedException, NotFoundDataExceptions {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        Date datePass;
        try {
            datePass = format.parse(String.valueOf(request.get("datePass")));
        } catch (ParseException e) {
            throw new ApiException("Дата не указана или указана неверно");
        }
        if (datePass.getTime() < new Date().getTime()) {
            throw new ApiException("Дата не может быть в прошлом");
        }
        Long passType;
        try {
            passType = Long.valueOf(String.valueOf(request.get("passType")));
        } catch (NumberFormatException e) {
            throw new ApiException("Не верно указан тип пропуска");
        }

        try (Session session = MainPool.getPool()) {
            TUsers user;
            try {
                user = TUserSessions.getBySessionKey(session, sessionId).get(0).getUser();
            } catch (NotFoundDataExceptions e) {
                throw new ApiException("Пользователь не авторизован");
            }
            TUserPass pass = new TUserPass();
            pass.setUserId(user.getId());
            pass.setStartDate(datePass);
            pass.setPassTypeId(passType);
            pass.setValid(Boolean.TRUE);
            session.beginTransaction();
            session.save(pass);
            jsonData.put("passId", pass.getId());
            session.getTransaction().commit();
        }
    }
}
