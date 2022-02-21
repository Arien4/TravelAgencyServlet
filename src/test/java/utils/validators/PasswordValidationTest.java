package utils.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.olenazaviriukha.travel.common.utils.ValidationUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PasswordValidationTest {
    private String pass1;
    private String pass1Copy;
    private String pass2;
    private String notPass2;

    @BeforeEach
    public void setUp() {
        pass1 = "098iuy";
        pass1Copy = "098iuy";
        pass2 = "[pooii";
        notPass2 = "[pooidddi";
    }

    @Test
    public void shouldReturnTrueForEqualPasswords() {
        assertNull(ValidationUtils.matchPasswordsError(pass1, pass1Copy));
    }

    @Test
    public void shouldReturnFalseForDifferentPasswords() {
        assertEquals(ValidationUtils.matchPasswordsError(pass2, notPass2), "Passwords do not match");
    }
}
