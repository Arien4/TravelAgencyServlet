package org.olenazaviriukha.travel.common.paginator;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Paginator implements Iterable<Page>, Serializable {
    public final static String QUERY_PARAM_NAME = "page";

    private final Integer limit;
    private final Integer activePageIndex; //0-base
    private final Integer total;
    private final String uriTemplate;
    private final String RE_GROUP_PAGE = "pageParam";
    private final Pattern pagePattern = Pattern.compile("(?<" + RE_GROUP_PAGE + ">page=\\w*&?)", Pattern.CASE_INSENSITIVE);

    public Paginator(Integer limit, Integer total, Integer activePageCount, HttpServletRequest req) {
        this.limit = limit;
        this.activePageIndex = activePageCount - 1;
        this.total = total;
        String uri = req.getRequestURI();
        String queryString = req.getQueryString();

        try {
            Matcher matcher = pagePattern.matcher(queryString);
            while (matcher.find()) {
                String pageGroup = matcher.group(RE_GROUP_PAGE);
                queryString = queryString.replace(pageGroup, "");
            }
        } catch (NullPointerException e) {
            queryString = "";
        }

        if (queryString.length() > 0 && !queryString.endsWith("&")) queryString += "&";
        this.uriTemplate = uri + "?" + queryString + "page=";

    }

    @Override
    public Iterator<Page> iterator() {
        return new PageIterator(this);
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getActivePageIndex() {
        return activePageIndex;
    }

    public Integer getTotal() {
        return total;
    }

    public Page getActivePage() {
        return new Page(true, activePageIndex, limit, uriTemplate);
    }

    public String getUriTemplate() {
        return uriTemplate;
    }
}
