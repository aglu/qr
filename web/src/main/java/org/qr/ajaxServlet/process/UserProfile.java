package org.qr.ajaxServlet.process;

import java.util.HashMap;
import java.util.Map;

import com.qr.dbcontroller.dao.TUserPass;
import com.qr.dbcontroller.dao.TUserSessions;
import com.qr.dbcontroller.dao.TUsers;
import com.qr.dbcontroller.pools.MainPool;
import org.hibernate.Session;
import org.qr.ajaxServlet.ApiException;

public class UserProfile {

    public static void execute(String sessionId, Map<String, Object> request, Map<String, Object> jsonData) throws ApiException {
        try (Session session = MainPool.getPool()) {
            TUsers user;
            try {
                user = TUserSessions.getBySessionKey(session, sessionId).get(0).getUser();
            } catch (Exception e) {
                throw new ApiException("Сессия пользователя истекла");
            }
            Map<String, String> map = new HashMap<>();
            user.getAttributes().forEach(el -> map.put(el.getDicAttributes().getValue(), el.getAttrValue()));
            jsonData.put("userAttributes", map);
            Object[] arr = user.getUserPasses().stream().sorted((TUserPass el1, TUserPass el2) -> el2.getStartDate().compareTo(el1.getStartDate())).toArray();
            jsonData.put("pass", arr);
        }
    }
}
