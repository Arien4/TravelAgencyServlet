package utils.paginator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.olenazaviriukha.travel.common.paginator.Page;
import org.olenazaviriukha.travel.common.paginator.Paginator;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PageIteratorTest {
    private Paginator paginator;
    private Iterator<Page> iterator;
    private HttpServletRequest requestMock;
    private final String uri = "http://localhost:8080/TestProject";

    @BeforeEach
    public void testCaseSetUp() {
        requestMock = Mockito.mock(HttpServletRequest.class);
        Mockito.when(requestMock.getRequestURI()).thenReturn(uri);
        paginator = new Paginator(10, 32, 3, requestMock);
        iterator = paginator.iterator();
    }

    @Test
    public void shouldReturnHasNextTrueOnFirstCall() {
        assertTrue(paginator.iterator().hasNext());
    }

    @Test
    public void shouldReturnPageInstanceOnNext(){
        assertInstanceOf(Page.class, iterator.next());
    }

    @Test
    public void shouldThrowNoSuchElementExceptionWhenHasNextIsFalse() {
        paginator = new Paginator(10, 32, 4, requestMock);
        iterator = paginator.iterator();
        while (iterator.hasNext()) iterator.next();
        Assertions.assertThrows(NoSuchElementException.class, () -> iterator.next());
    }


}
