package org.qr.ajaxServlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.qr.ajaxServlet.process.AuthorizeUser;
import org.qr.ajaxServlet.process.CheckSession;
import org.qr.ajaxServlet.process.CreatePass;
import org.qr.ajaxServlet.process.LogoutUser;
import org.qr.ajaxServlet.process.RegistrationUser;
import org.qr.ajaxServlet.process.RequestAuthorizationCode;
import org.qr.ajaxServlet.process.ShowPassData;
import org.qr.ajaxServlet.process.UserProfile;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AjaxRouter {

    private static final String ROUTE_ATTRIBUTE = "action";
    private static final ApiException EMPTY_ACTION_EXCEPTION = new ApiException("Не заданы параметры AJAX");
    private static final int BUFFER_SIZE = 4096;


    public static void processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(true);
        String sessionId = session.getId();
        boolean isSuccess = true;
        String error = null;
        Map<String, Object> jsonData = new HashMap<>();
        try {
            String inputData = getStreamData(request);

            if (inputData.equals("")) {
                throw EMPTY_ACTION_EXCEPTION;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(inputData, new TypeReference<Map<String, Object>>() {
            });
            String action = (String) map.get(ROUTE_ATTRIBUTE);
            if (action == null) {
                throw EMPTY_ACTION_EXCEPTION;
            }
            switch (action) {
                case "check_session" -> CheckSession.execute(sessionId, map, jsonData);
                case "authorize_user" -> AuthorizeUser.execute(sessionId, map, jsonData);
                case "registration_user" -> RegistrationUser.execute(sessionId, map, jsonData);
                case "request_code" -> RequestAuthorizationCode.execute(sessionId, map, jsonData);
                case "create_pass" -> CreatePass.execute(sessionId, map, jsonData);
                case "data_pass" -> ShowPassData.execute(sessionId, map, jsonData);
                case "user_data" -> UserProfile.execute(sessionId, map, jsonData);
                case "logout" -> LogoutUser.execute(sessionId, map, jsonData);
                default -> throw EMPTY_ACTION_EXCEPTION;
            }
        } catch (ApiException e) {
            isSuccess = false;
            error = e.getMessage();
        }
        JsonResponce gsonResponce = new JsonResponce(isSuccess, error, jsonData);
        response.getWriter().write(gsonResponce.toJson());
        response.getWriter().flush();
        response.getWriter().close();
    }

    private static String getStreamData(HttpServletRequest reqt) throws IOException {
        String requestData;
        InputStream requestStream = reqt.getInputStream();
        ByteArrayOutputStream cloneableStream = new ByteArrayOutputStream();
        copyStream(requestStream, cloneableStream);
        InputStream inputStreamForParsing = new ByteArrayInputStream(cloneableStream.toByteArray());
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            copyStream(inputStreamForParsing, result);
            requestData = result.toString(StandardCharsets.UTF_8.name());
        } finally {
            try {
                inputStreamForParsing.close();
            } catch (IOException e1) {
                log.warn("Can not close input stream: ", e1);
            }
        }
        return requestData;
    }

    public static long copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] temp = new byte[BUFFER_SIZE];
        long total = 0;
        while (true) {
            int read = input.read(temp);
            if (read < 0)
                break;
            output.write(temp, 0, read);
            total += read;
        }
        return total;
    }

}
