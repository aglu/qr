package org.qr.documentServlet.qr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.qr.Properties;

public class QrExecutor {

    public static void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long id;
        try {
            id = Long.parseLong(String.valueOf(request.getParameter("id")));
        } catch (NumberFormatException e) {
            throw new Exception("Номер введён неверно");
        }
        response.setHeader("Content-Type", "image/png");
        QRCodeGenerator.generateQRCodeImage(Properties.PATH_SITE +"/?passid=" + id, 800, 800, response.getOutputStream());
    }
}
