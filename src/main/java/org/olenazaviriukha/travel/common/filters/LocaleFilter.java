package org.olenazaviriukha.travel.common.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocaleFilter implements Filter {
    //    private static final Logger log = Logger.getLogger(EncodingFilter.class);
    private final String LOCALE = "locale";
    private final String PARAM_LANG = "lang_code";


    /**
     * Destroy method.
     */
//    public void destroy() {
//        log.debug("Filter destruction starts");
//        // do nothing
//        log.debug("Filter destruction finished");
//    }

    /**
     * Main method.
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
//        log.debug("Filter starts");
        HttpSession session = ((HttpServletRequest) req).getSession();
        String newLocale = req.getParameter(PARAM_LANG);
        if (newLocale != null) {
            session.setAttribute(LOCALE, newLocale);
        }
        chain.doFilter(req, resp);
    }
}