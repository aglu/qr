package org.qr.ajaxServlet.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.qr.dbcontroller.dao.TUserAttributes;
import com.qr.dbcontroller.dao.TUsers;
import com.qr.dbcontroller.exceptions.EmptyParamsException;
import com.qr.dbcontroller.exceptions.NotFoundDataExceptions;
import com.qr.dbcontroller.pools.MainPool;
import org.hibernate.Session;
import org.qr.ajaxServlet.ApiException;
import org.qr.ajaxServlet.UserRegistrationData;

public class RegistrationUser {


    public static void execute(String sessionId, Map<String, Object> request, Object jsonData) throws ApiException, EmptyParamsException, InterruptedException {
        UserRegistrationData regData = new UserRegistrationData();
        regData.writeFromData(request);
        try (Session session = MainPool.getPool()) {
            boolean isExistUser = true;
            try {
                TUsers.getByPhone(session, regData.getPhone());
            } catch (NotFoundDataExceptions notFoundDataExceptions) {
                isExistUser = false;
            }
            if (isExistUser) {
                throw new ApiException("Пользователь уже зарегистрирован");
            }
            session.beginTransaction();
            TUsers newUser = new TUsers();
            List<TUserAttributes> userAttributes = new ArrayList<>();
            newUser.setPhone(regData.getPhone());
            session.save(newUser);
            userAttributes.add(new TUserAttributes().setUserId(newUser.getId())
                    .setAttrName(1L)
                    .setAttrValue(regData.getFirstName()));
            userAttributes.add(new TUserAttributes().setUserId(newUser.getId())
                    .setAttrName(2L)
                    .setAttrValue(regData.getLastName()));
            userAttributes.add(new TUserAttributes().setUserId(newUser.getId())
                    .setAttrName(3L)
                    .setAttrValue(regData.getMiddleName()));
            userAttributes.add(new TUserAttributes().setUserId(newUser.getId())
                    .setAttrName(4L)
                    .setAttrValue(regData.getDocNumber()));
            userAttributes.forEach(session::save);
            session.getTransaction().commit();
        }
    }
}