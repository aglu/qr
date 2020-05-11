package org.qr.ajaxServlet;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qr.dbcontroller.pools.MainPool;


import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@WebServlet(name = "QRweb", urlPatterns = {"/web"})

@NoArgsConstructor
@Slf4j
public class HttpServlet extends javax.servlet.http.HttpServlet {

    public final static Charset CHARSET = StandardCharsets.UTF_8;
    private final static int SUCCESS_RESPONCE = HttpServletResponse.SC_OK;
    private final  static int BAD_RESPONCE = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

    private void doRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding(CHARSET.name());
            response.setStatus(SUCCESS_RESPONCE);
            response.setCharacterEncoding(CHARSET.name());
            response.setHeader("Content-Type", "text/html; charset=utf-8");

            response.setContentType("application/json; charset=" + CHARSET.name());
            AjaxRouter.processAjax(request, response);


        } catch (Exception e) {
            log.error("Error servlet DOCUMENT", e);
            response.setStatus(BAD_RESPONCE);
            response.setContentType(CHARSET.name());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        doRequest(request, response);
    }

    @Override
    public void init() {
        MainPool.initialize();
    }


}
