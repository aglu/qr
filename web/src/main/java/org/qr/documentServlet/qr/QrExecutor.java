package org.qr.documentServlet.qr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QrExecutor {

    public static void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long id;
        try {
            id = Long.parseLong(String.valueOf(request.getParameter("id")));
        } catch (NumberFormatException e) {
            throw new Exception("Номер введён неверно");
        }

        response.setHeader("Content-Type", "image/png");
        //Writer writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);
        QRCodeGenerator.generateQRCodeImage("http://localhost:8080/doc/?type=qr&id=" + id, 800, 800, response.getOutputStream());
        //response.getWriter().flush();
        //response.getWriter().close();
    }
}
