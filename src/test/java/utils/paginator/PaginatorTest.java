package utils.paginator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.olenazaviriukha.travel.common.paginator.Paginator;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaginatorTest {
    private final int limit = 10;
    private final int activePageCount = 3;
    private final int total = 340;
    private final String uri = "http://localhost:8080/TestProject";
    private HttpServletRequest requestMock;


    @BeforeEach
    public void testCaseSetup() {
        requestMock = Mockito.mock(HttpServletRequest.class);
        Mockito.when(requestMock.getRequestURI()).thenReturn(uri);
    }

    @Test
    public void shouldFormatURITemplateWhenQueryStringIsEmpty() {
        Mockito.when(requestMock.getQueryString()).thenReturn(null);
        Paginator paginator = new Paginator(limit, total, activePageCount, requestMock);
        assertEquals(paginator.getUriTemplate(), uri + "?page=");
    }

    @Test
    public void shouldFormatURITemplateWhenQueryStringHasNoPageParameter() {
        String queryString = "hotel_type=2&guests_number=3";
        Mockito.when(requestMock.getQueryString()).thenReturn(queryString);
        Paginator paginator = new Paginator(limit, total, activePageCount, requestMock);
        assertEquals(paginator.getUriTemplate(), uri + "?" + queryString + "&page=");
    }

    @Test
    public void shouldFormatURITemplateWhenQueryStringHasPageParameter() {
        String queryString = "page=2&hotel_type=2&guests_number=3";
        Mockito.when(requestMock.getQueryString()).thenReturn(queryString);
        Paginator paginator = new Paginator(limit, total, activePageCount, requestMock);
        assertEquals(paginator.getUriTemplate(), uri + "?hotel_type=2&guests_number=3&page=");
    }

    @Test
    public void shouldReturnActivePage() {
        Paginator paginator = new Paginator(limit, total, activePageCount, requestMock);
        assertTrue(paginator.getActivePage().isActive());
    }

    @Test
    public void shouldReturnActivePageWithExpectedCount() {
        Paginator paginator = new Paginator(limit, total, activePageCount, requestMock);
        assertEquals(paginator.getActivePage().getCount(), activePageCount);
    }
}
