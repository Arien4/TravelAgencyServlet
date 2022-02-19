package org.olenazaviriukha.travel.common.paginator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PageIterator implements Iterator<Page> {
    private int cursor;
    private final Paginator paginator;

    @Override
    public boolean hasNext() {
        return cursor * paginator.getLimit() < paginator.getTotal();
    }

    /**
     *
     * @return next page
     * @throws NoSuchElementException if active page is last
     */
    @Override
    public Page next() {
        if (!hasNext()) throw new NoSuchElementException();
        Page page = new Page(cursor == paginator.getActivePageIndex(), cursor, paginator.getLimit(), paginator.getUriTemplate());
        cursor++;
        return page;
    }

    /**
     * Iterator for paginator
     * @param self is paginator
     */
    public PageIterator(Paginator self) {
        this.cursor = 0;
        this.paginator = self;
    }


}
