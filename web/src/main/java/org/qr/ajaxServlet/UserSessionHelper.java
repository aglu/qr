package org.qr.ajaxServlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qr.DBController.dao.TUserAuthorizationCode;
import com.qr.DBController.dao.TUserSessions;
import com.qr.DBController.dao.TUsers;
import com.qr.DBController.exceptions.EmptyParamsException;
import com.qr.DBController.exceptions.NotFoundDataExceptions;
import com.qr.DBController.pools.MainPool;
import org.hibernate.Session;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserSessionHelper {
    private static final Integer MAX_LENGTH_USER_SESSION = 15 * 60 * 1000;

    public static TUsers checkSessionWithProlong(String sessionId) throws ApiException, EmptyParamsException, InterruptedException {
        try (Session session = MainPool.getPool()) {
            List<TUserSessions> sessions;
            try {
                sessions = TUserSessions.getBySessionKey(session, sessionId);
            } catch (NotFoundDataExceptions e) {
                throw new ApiException("Сессия пользователя не найдена. Необходима авторизация", true);
            }
            TUserSessions userSession = sessions.get(0);
            Date currentDate = new Date();
            if (userSession.getSessionDate().getTime() + MAX_LENGTH_USER_SESSION < currentDate.getTime()) {
                session.beginTransaction();
                session.delete(userSession);
                session.getTransaction().commit();
                throw new ApiException("Сессия пользователя истекла. Необходима авторизация");
            }
            userSession.setSessionDate(currentDate);
            return userSession.getUser();
        }
    }

    public static void authorizeUser(String phone, String code, String sessionId) throws ApiException, EmptyParamsException, InterruptedException {
        try (Session session = MainPool.getPool()) {
            List<TUsers> users;
            users = TUsers.getByPhone(session, phone);
            if (users.isEmpty() || users.size() > 1) {
                throw new ApiException("Пользователь не найден");
            }
            if (users.get(0).getKeys().stream().noneMatch(el -> el.getKey().equals(code))) {
                throw new ApiException("Неверный ключ авторизации");
            }
            session.beginTransaction();
            TUserAuthorizationCode codeUser = users.get(0).getKeys().stream().filter(el -> el.getKey().equals(code)).findFirst().orElse(new TUserAuthorizationCode());
            codeUser.setUsing(false);
            session.delete(codeUser);
            TUserSessions sessions = new TUserSessions();
            sessions.setUserId(users.get(0).getId());
            sessions.setSessionKey(sessionId);
            sessions.setSessionDate(new Date());
            session.save(sessions);
            session.getTransaction().commit();
        }
    }

}
