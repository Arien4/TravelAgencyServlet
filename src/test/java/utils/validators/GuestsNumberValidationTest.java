package utils.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.olenazaviriukha.travel.common.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class GuestsNumberValidationTest {
    private List<Integer> validNumbers;
    private List<Integer> invalidNumbers;

    @BeforeEach
    public void setUp() {
        validNumbers = List.of(2, 5, 1, 7, 10);
        invalidNumbers = List.of(0, -1, 100, 45, 11);
    }

    @Test
    public void shouldReturnTrueForValidNumbers() {
        List<String> actual = new ArrayList<>();
        for (int number : validNumbers) {
            actual.add(ValidationUtils.guestsNumberValidationError(number));
        }
        List<String> expected = new ArrayList<>(Collections.nCopies(5, null));
        assertIterableEquals(expected, actual);
    }

    @Test
    public void shouldReturnFalseForInvalidNumbers() {
        List<String> actual = new ArrayList<>();
        for (int number : invalidNumbers) {
            actual.add(ValidationUtils.guestsNumberValidationError(number));
        }
        List<String> expected = new ArrayList<>(Collections.nCopies(5, "Number between 1 and 10 is expected"));
        assertIterableEquals(expected, actual);
    }
}
