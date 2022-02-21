package utils.validators;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.olenazaviriukha.travel.common.utils.ValidationUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DateValidatorTest {
    private LocalDate startDay;
    private LocalDate endDay;

    @BeforeEach
    public void before() {
        startDay = LocalDate.of(2023,10,10);
        endDay = LocalDate.of(2023, 10, 20);
    }

    @Test
    public void shouldReturnTrueIfDatesAreValid() {
        String expected = ValidationUtils.tourDatesError(startDay, endDay);
        assertNull(expected);
    }

    @Test
    public void shouldReturnFalseIfDatesAreNotValid() {
        String expected = ValidationUtils.tourDatesError(endDay, startDay);
        assertEquals(expected, "The start date must precede the end date");
    }

    @Test
    public void shouldReturnTrueIfDateISInTheFuture() {
        String expected = ValidationUtils.tourDateError(endDay.plusYears(12));
        assertNull(expected);
    }

    @Test
    public void shouldReturnFalseIfDateISInThePast() {
        String expected = ValidationUtils.tourDateError(endDay.minusYears(5));
        assertEquals(expected, "Date cannot be in the past");
    }
}
