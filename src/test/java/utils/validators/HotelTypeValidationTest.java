package utils.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.olenazaviriukha.travel.common.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class HotelTypeValidationTest {
    private List<Integer> validHotelTypes;
    private List<Integer> invalidHotelTypes;

    @BeforeEach
    public void setUp() {
        validHotelTypes = List.of(1, 2, 3, 4, 5);
        invalidHotelTypes = List.of(-7, 9, 90, 12, 34);
    }

    @Test
    public void shouldReturnTrueForValidHotelTypes() {
        List<String> actual = new ArrayList<>();
        for (Integer type : validHotelTypes) {
            actual.add(ValidationUtils.hotelTypeValidationError(type));
        }
        List<String> expected = new ArrayList<>(Collections.nCopies(5, null));
        assertIterableEquals(expected, actual);
    }

    @Test
    public void shouldReturnFalseForInvalidHotelTypes() {
        List<String> actual = new ArrayList<>();
        for (Integer type : invalidHotelTypes) {
            actual.add(ValidationUtils.hotelTypeValidationError(type));
        }
        List<String> expected = new ArrayList<>(Collections.nCopies(5, "Number between 0 and 5 is expected"));
        assertIterableEquals(expected, actual);
    }
}
