package org.olenazaviriukha.travel.common.filters;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
//    private static final Logger log = Logger.getLogger(EncodingFilter.class);
    private String encoding;

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
        String requestEncoding = req.getCharacterEncoding();

        if (requestEncoding == null) {
//            log.trace("Request encoding = null, set encoding --> " + encoding);
            req.setCharacterEncoding(encoding);
        }
//        log.debug("Filter finished");
        chain.doFilter(req, resp);
    }

    /**
     * Init method.
     */
    public void init(FilterConfig config) {
//        log.debug("Filter initialization starts");
        encoding = config.getInitParameter("encoding");
//        log.trace("Encoding from web.xml --> " + encoding);
//        log.debug("Filter initialization finished");
    }
}