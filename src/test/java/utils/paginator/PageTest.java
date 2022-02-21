package utils.paginator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.olenazaviriukha.travel.common.paginator.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PageTest {
    private Page page;

    @BeforeEach
    public void testCaseSetUp(){
        int limit = 10;
        int pageIndex = 3;
        String uriTemplate = "http://localhost:8080/TestProject?hotel_type=2&guests_number=3&page=";
        page = new Page(true, pageIndex, limit, uriTemplate);
    }

    @Test
    public void shouldReturnURIWithPageCount() {
        assertEquals(page.getURI(), "http://localhost:8080/TestProject?hotel_type=2&guests_number=3&page=4");
    }

    @Test
    public void shouldReturnOffset() {
        assertEquals(page.getOffset(), 30);
    }

    @Test
    public void shouldReturnIndex() {
        assertEquals(page.getIndex(), 3);
    }

    @Test
    public void shouldReturnActiveState() {
        assertTrue(page.isActive());
    }

    @Test
    public void shouldReturnPageCount() {
        assertEquals(page.getCount(), 4);
    }
}
