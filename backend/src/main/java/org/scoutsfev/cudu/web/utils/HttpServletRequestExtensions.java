package org.scoutsfev.cudu.web.utils;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestExtensions {

    public static String auditUrl(HttpServletRequest request) {
        if (request == null)
            return "(null)";
        String queryString = request.getQueryString();
        return request.getMethod() + " " + request.getRequestURL().toString()
                + (queryString != null ? "?" + queryString : "");
    }
}
