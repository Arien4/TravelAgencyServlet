package org.olenazaviriukha.travel.utils;

import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utils for fields validation
 */
public class ValidationUtils {
    private static final String REQUIRED = "This field is required";
    private static final String INVALID_ADDRESS = "Invalid email address";
    private static final String INVALID_FIRST_NAME = "Invalid first name";
    private static final String INVALID_LAST_NAME = "Invalid last name";
    private static final String PASSWORDS_DONT_MATCH = "Passwords do not match";
    private static final String WRONG_HOTEL_TYPE = "Number between 0 and 5 is expected";
    private static final String WRONG_GUESTS_NUMBER = "Number between 1 and 10 is expected";
    private static final String DAY_IN_THE_PAST = "Date cannot be in the past";
    private static final String WRONG_DAYS = "The start date must precede the end date";
    private static final String WRONG_MAX_DISCOUNT = "Number between 0 and 100 is expected";
    private static final String WRONG_DISCOUNT_STEP = "Number between 1 and 99 is expected";

    /**
     * @param email to validate
     * @return validation error as String or null if email is valid
     */
    public static String emailValidationError(String email) {
        if (email.isBlank()) return REQUIRED;
        if (!EmailValidator.getInstance().isValid(email)) return INVALID_ADDRESS;
        return null;
    }

    /**
     * @param name - first or last name to validate
     * @return validation error as String or null if name is valid
     */
    public static String nameValidationError(String name, boolean first) {
        Pattern namePattern = Pattern.compile("^[A-ZА-ЯЇІЄҐ][a-zа-яїіґє']*");
        Matcher nameMatcher = namePattern.matcher(name);
        if (!nameMatcher.matches()) {
            if (first) return INVALID_FIRST_NAME;
            else return INVALID_LAST_NAME;
        }
        return null;
    }

    /**
     * @param password to validate
     * @return validation error as String or null if password is not blank
     */
    public static String blankPasswordError(String password) {
        if (password.isBlank()) return REQUIRED;
        return null;
    }

    /**
     * @return validation error as String or null if passwords match
     */
    public static String matchPasswordsError(String password, String passwordRepeat) {
        if (!password.equals(passwordRepeat)) return PASSWORDS_DONT_MATCH;
        return null;
    }

    /**
     * @param name - hotel name to validate
     * @return validation error as String or null if the name is valid
     */
    public static String hotelNameValidationError(String name) {
        if (name.isBlank()) return REQUIRED;
        return null;
    }

    /**
     * @param type - number of stars of the hotel
     * @return validation error as String or null if the name is valid
     */
    public static String hotelTypeValidationError(Integer type) {
        if (type < 0 || type > 5) return WRONG_HOTEL_TYPE;
        return null;
    }
    public static String guestsNumberValidationError(Integer guests) {
        if (guests == null) return REQUIRED;
        if (guests < 1 || guests > 10) return WRONG_GUESTS_NUMBER;
        return null;
    }

    public static String tourDateError(LocalDate tourDate) {
        if (tourDate == null) return REQUIRED;
        if (tourDate.isBefore(LocalDate.now())) return DAY_IN_THE_PAST;
        return null;
    }

    public static String tourDatesError(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) return WRONG_DAYS;
        return null;
    }

    public static String maxDiscountValidationError(Integer maxDiscount) {
        if (maxDiscount == null) return REQUIRED;
        if (maxDiscount > 100 || maxDiscount < 0) return WRONG_MAX_DISCOUNT;
        return null;
    }

    public static String discountStepValidationError(Integer discountStep, Integer maxDiscount) {
        Integer max = maxDiscount;
        if (maxDiscount == null) {
            max = Integer.valueOf(99);
        }
        if (discountStep > max || discountStep < 1) return WRONG_DISCOUNT_STEP;
        return null;
    }
}
