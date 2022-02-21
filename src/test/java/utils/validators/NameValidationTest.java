package utils.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.olenazaviriukha.travel.common.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class NameValidationTest {
    private List<String> validNames;
    private List<String> invalidNames;

    @BeforeEach
    public void setUp() {
        validNames = List.of("Olena", "Vasyl", "Петренко", "Василишин", "Максим", "П'яточкін", "Нетудихата");
        invalidNames = List.of("lll", "petro", "x0", "ааДД", "ллл", "жа)", "9887?");
    }

    @Test
    public void shouldReturnTrueForValidNames() {
        List<String> actual = new ArrayList<>();
        for (String name : validNames) {
            actual.add(ValidationUtils.nameValidationError(name, true));
        }
        List<String> expected = new ArrayList<>(Collections.nCopies(7, null));
        assertIterableEquals(expected, actual);
    }

    @Test
    public void shouldReturnFalseForInvalidNames() {
        List<String> actual = new ArrayList<>();
        for (String name : invalidNames) {
            actual.add(ValidationUtils.nameValidationError(name, false));
        }
        List<String> expected = new ArrayList<>(Collections.nCopies(7, "Invalid last name"));
        assertIterableEquals(expected, actual);


    }
}
