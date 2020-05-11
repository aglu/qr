package org.qr.ajaxServlet.process;

import java.util.Map;

import com.qr.dbcontroller.exceptions.EmptyParamsException;
import org.qr.ajaxServlet.ApiException;
import org.qr.ajaxServlet.UserSessionHelper;

public class CheckSession {

    public static void execute(String sessionId, Map<String, Object> request, Object jsonData) throws ApiException, EmptyParamsException, InterruptedException {
        UserSessionHelper.checkSessionWithProlong(sessionId);
    }


}
