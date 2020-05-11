package org.qr.ajaxServlet.process;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qr.DBController.dao.TUserAttributes;
import com.qr.DBController.dao.TUserPass;
import com.qr.DBController.exceptions.EmptyParamsException;
import com.qr.DBController.exceptions.NotFoundDataExceptions;
import com.qr.DBController.pools.MainPool;
import org.hibernate.Session;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.qr.ajaxServlet.ApiException;

public class ShowPassData {

    public static void execute(String sessionId, Map<String, Object> request, Map<String, Object> jsonData) throws ApiException, EmptyParamsException, InterruptedException, NotFoundDataExceptions {

        Long passId;
        try {
            passId = Long.valueOf(String.valueOf(request.get("passId")));
        } catch (NumberFormatException e) {
            throw new ApiException("Идентификатор пропуска задан неверно");
        }
        try (Session session = MainPool.getPool()) {
            TUserPass userPass = TUserPass.getById(session, passId);
            jsonData.put("passId", userPass.getId());
            jsonData.put("typeId", userPass.getPassTypeId());
            jsonData.put("typeName", userPass.getPassType().getName());
            jsonData.put("startDate", userPass.getStartDate());
            jsonData.put("endDate", new Date(userPass.getStartDate().getTime() + userPass.getPassType().getDuration() * 60 * 1000));
            Map<String,String> map = new HashMap<>();
            userPass.getUser().getAttributes().stream().forEach(el -> map.put(el.getDicAttributes().getValue(), el.getAttrValue()));
        jsonData.put("userAttributes", map);
        }
    }
}
