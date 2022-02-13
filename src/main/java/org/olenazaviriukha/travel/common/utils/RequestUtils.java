package org.olenazaviriukha.travel.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Util class to manipulate with HTTPServletRequest
 */
public class RequestUtils {
    /**
     * @return session attribute by its name
     */

    public static <T> T getSessionAttribute(HttpServletRequest req, String attributeName, Class<T> clazz) {
        HttpSession session = req.getSession(false);
        if (session != null && attributeName != null) {
            Object attribute = session.getAttribute(attributeName);
            if (clazz.isInstance(attribute)) {
                return clazz.cast(attribute);
            }
        }
        return null;
    }

    public static void handleRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {

        PrintWriter out = res.getWriter();
        res.setContentType("text/plain");

        Enumeration<String> parameterNames = req.getParameterNames();

        while (parameterNames.hasMoreElements()) {

            String paramName = parameterNames.nextElement();
            out.write(paramName);
            out.write("\n");

            String[] paramValues = req.getParameterValues(paramName);
            for (int i = 0; i < paramValues.length; i++) {
                String paramValue = paramValues[i];
                out.write("\t" + paramValue);
                out.write("\n");
            }

            for (int i = 0; i < paramValues.length; i++) {
                String paramValue = paramValues[i];
                System.out.println("\t" + paramValue);
                System.out.println("\n");
            }

        }
        out.close();

    }
}
