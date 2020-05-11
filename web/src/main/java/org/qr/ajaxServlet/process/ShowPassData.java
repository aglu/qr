package org.qr.ajaxServlet.process;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.qr.dbcontroller.dao.TUserPass;
import com.qr.dbcontroller.exceptions.EmptyParamsException;
import com.qr.dbcontroller.exceptions.NotFoundDataExceptions;
import com.qr.dbcontroller.pools.MainPool;
import org.hibernate.Session;
import org.qr.ajaxServlet.ApiException;

public class ShowPassData {

    public static void execute(String sessionId, Map<String, Object> request, Map<String, Object> jsonData) throws ApiException, EmptyParamsException {

        long passId;
        try {
            passId = Long.parseLong(String.valueOf(request.get("passId")));
        } catch (NumberFormatException e) {
            throw new ApiException("Идентификатор пропуска задан неверно");
        }
        try (Session session = MainPool.getPool()) {
            TUserPass userPass;
            try {
                userPass = TUserPass.getById(session, passId);
            } catch (NotFoundDataExceptions e) {
                throw new ApiException("Пропуск не найден");
            }
            jsonData.put("passId", userPass.getId());
            jsonData.put("typeId", userPass.getPassTypeId());
            jsonData.put("typeName", userPass.getPassType().getName());
            jsonData.put("startDate", userPass.getStartDate());
            jsonData.put("endDate", new Date(userPass.getStartDate().getTime() + userPass.getPassType().getDuration() * 60 * 1000));
            Map<String, String> map = new HashMap<>();
            userPass.getUser().getAttributes().forEach(el -> map.put(el.getDicAttributes().getValue(), el.getAttrValue()));
            jsonData.put("userAttributes", map);
            jsonData.put("urlQr", "/api/doc?type=qr&id=" + passId);
        }
    }
}
