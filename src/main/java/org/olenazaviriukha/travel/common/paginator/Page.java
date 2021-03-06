package org.olenazaviriukha.travel.common.paginator;

/**
 * Class used for pagination
 */
public class Page {
    private final boolean active;
    private final int index;  // 0-based
    private final int limit;
    private final String uri;

    public Page(boolean active, int index, int limit, String uriTemplate) {
        this.active = active;
        this.index = index;
        this.limit = limit;
        int count = index + 1;
        this.uri = uriTemplate + count;
    }

    public Integer getIndex() {
        return index;
    }

    public Integer getCount() {
        return index + 1;
    }

    public Integer getOffset() {
        return index * limit;
    }

    public boolean isActive() {
        return active;
    }

    public String getURI() {
        return uri;
    }
}
