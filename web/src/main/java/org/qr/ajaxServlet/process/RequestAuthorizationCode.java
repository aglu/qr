package org.qr.ajaxServlet.process;

import java.util.HashMap;
import java.util.Map;

import com.qr.DBController.dao.TUserAuthorizationCode;
import com.qr.DBController.dao.TUsers;
import com.qr.DBController.exceptions.EmptyParamsException;
import com.qr.DBController.exceptions.NotFoundDataExceptions;
import com.qr.DBController.pools.MainPool;
import org.hibernate.Session;
import org.qr.ajaxServlet.ApiException;

public class RequestAuthorizationCode {

    public static void execute(String sessionId, Map<String, Object> request, Map<String, Object> jsonData) throws ApiException, EmptyParamsException, InterruptedException {
        String phone = String.valueOf(request.get("phone"));
        if (phone.equals("null")) {
            throw new ApiException("Номер телефона не указан или указан неверно");
        }
        try (Session session = MainPool.getPool()) {
            TUsers user = TUsers.getByPhone(session, phone).get(0);
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
