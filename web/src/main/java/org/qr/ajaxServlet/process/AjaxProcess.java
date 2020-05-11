package org.qr.ajaxServlet.process;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.qr.ajaxServlet.ApiException;

public interface AjaxProcess {

    public static void execute(HttpSession session, HttpServletRequest request, Object jsonData) throws ApiException {

    }
}
