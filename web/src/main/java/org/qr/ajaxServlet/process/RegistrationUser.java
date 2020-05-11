package org.qr.ajaxServlet.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.qr.DBController.dao.TUserAttributes;
import com.qr.DBController.dao.TUsers;
import com.qr.DBController.exceptions.EmptyParamsException;
import com.qr.DBController.exceptions.NotFoundDataExceptions;
import com.qr.DBController.pools.MainPool;
import org.hibernate.Session;
import org.qr.ajaxServlet.ApiException;
import org.qr.ajaxServlet.UserRegistrationData;

public class RegistrationUser {


    public static void execute(String sessionId, Map<String, Object> request, Object jsonData) throws ApiException {
        UserRegistrationData regData = new UserRegistrationData();
        regData.writeFromData(request);
        try (Session session = MainPool.getPool()) {
            session.beginTransaction();
            TUsers newUser = new TUsers();
            List<TUserAttributes> userAttributes = new ArrayList<>();
            newUser.setPhone(regData.getPhone());
            session.save(newUser);
            userAttributes.add(new TUserAttributes().setUserId(newUser.getId())
                    .setAttrName(1l)
                    .setAttrValue(regData.getFirstName()));
            userAttributes.add(new TUserAttributes().setUserId(newUser.getId())
                    .setAttrName(2l)
                    .setAttrValue(regData.getLastName()));
            userAttributes.add(new TUserAttributes().setUserId(newUser.getId())
                    .setAttrName(3l)
                    .setAttrValue(regData.getMiddleName()));
            userAttributes.add(new TUserAttributes().setUserId(newUser.getId())
                    .setAttrName(4l)
                    .setAttrValue(regData.getDocNumber()));
            userAttributes.forEach(session::save);
            session.getTransaction().commit();
        }
    }
}