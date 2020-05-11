package org.qr.ajaxServlet.process;

import java.util.Map;

import com.qr.dbcontroller.dao.TUserAuthorizationCode;
import com.qr.dbcontroller.dao.TUsers;
import com.qr.dbcontroller.exceptions.EmptyParamsException;
import com.qr.dbcontroller.exceptions.NotFoundDataExceptions;
import com.qr.dbcontroller.pools.MainPool;
import org.hibernate.Session;
import org.qr.ajaxServlet.ApiException;

public class RequestAuthorizationCode {

    public static void execute(String sessionId, Map<String, Object> request, Map<String, Object> jsonData) throws ApiException, EmptyParamsException, InterruptedException {
        String phone = String.valueOf(request.get("phone"));
        if (phone.equals("null")) {
            throw new ApiException("Номер телефона не указан или указан неверно");
        }
        try (Session session = MainPool.getPool()) {
            TUsers user;
            try {
                user = TUsers.getByPhone(session, phone).get(0);
            } catch (NotFoundDataExceptions e) {
                throw new ApiException("Пользователь не найден");
            }
            TUserAuthorizationCode newCode = new TUserAuthorizationCode();
            newCode.setUserId(user.getId());
            newCode.setKey(String.valueOf(Math.round(Math.random() * ((9999 - 1000) + 1)) + 1000));
            jsonData.put("authCode", newCode.getKey());
            session.beginTransaction();
            session.save(newCode);
            session.getTransaction().commit();
        }
    }
}
