package org.qr.ajaxServlet.process;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import com.qr.dbcontroller.dao.TUserPass;
import com.qr.dbcontroller.dao.TUserSessions;
import com.qr.dbcontroller.dao.TUsers;
import com.qr.dbcontroller.exceptions.EmptyParamsException;
import com.qr.dbcontroller.exceptions.NotFoundDataExceptions;
import com.qr.dbcontroller.pools.MainPool;
import org.hibernate.Session;
import org.qr.ajaxServlet.ApiException;

public class CreatePass {

    public static void execute(String sessionId, Map<String, Object> request, Map<String, Object> jsonData) throws ApiException, EmptyParamsException, InterruptedException {
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
        long passType;
        try {
            passType = Long.parseLong(String.valueOf(request.get("passType")));
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
