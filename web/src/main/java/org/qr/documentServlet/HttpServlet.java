package org.qr.documentServlet;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.qr.documentServlet.qr.QrExecutor;


import lombok.extern.slf4j.Slf4j;

@WebServlet(name = "HttpServlet", urlPatterns = {"/doc"})
@Slf4j
public class HttpServlet extends javax.servlet.http.HttpServlet {

    public final static int SUCCESS_RESPONCE = HttpServletResponse.SC_OK;
    public final static Charset CHARSET = StandardCharsets.UTF_8;
    private final static int BAD_RESPONCE = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

    private void doRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding(CHARSET.name());
            response.setStatus(SUCCESS_RESPONCE);
            response.setCharacterEncoding(CHARSET.name());

            String document = String.valueOf(request.getParameter("type"));
            if (document == null) {
                throw new Exception("Не указан тип документа");
            }
            switch (document) {
                case ("qr") -> QrExecutor.execute(request, response);
                default -> throw new Exception("Не указан тип документа");
            }

        } catch (Exception e) {
            log.error("Error servlet AJAX", e);
            response.setStatus(BAD_RESPONCE);
            response.setContentType(CHARSET.name());

        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        doRequest(request, response);
    }
}
