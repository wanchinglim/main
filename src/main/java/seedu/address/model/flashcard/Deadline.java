package seedu.address.model.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Calendar;

/**
 * Represents a flashcard's deadline in the flash book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */

public class Deadline {

    public static final String MESSAGE_CONSTRAINTS =
                                            "Flashcard deadlines must be valid and should only be of the format "
                                + "DD-MM-YYYY, where DD, MM and YYYY are numbers.\n DD must be in the range of 01 "
                                + "to 31, MM must be in the range of 01 to 12 and YYYY must be after 2018.\n"
                                + "Deadlines can also be left blank.";

    public static final String DEADLINE_VALIDATION_REGEX = "[\\d]{2}" + "-" + "[\\d]{2}" + "-" + "[\\d]{4}";

    private static final int DAY_MAX_LIMIT_LONG = 31;
    private static final int DAY_MAX_LIMIT_SHORT = 30;
    private static final int DAY_MAX_LIMIT_FEB = 28;
    private static final int DAY_MAX_LIMIT_FEB_LEAP = 29;
    private static final int DAY_MIN_LIMIT = 1;
    private static final int MONTH_MAX_LIMIT = 12;
    private static final int MONTH_MIN_LIMIT = 1;
    private static final int YEAR_MIN_LIMIT = 2018;

    public final String value;

    /**
     * Constructs an {@code Deadline}
     * @param deadline A valid deadline.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);
        checkArgument(isValidDeadline(deadline), MESSAGE_CONSTRAINTS);
        value = deadline;
    }

    /**
     * Returns if a given string is a valid deadline.
     */

    public static boolean isValidDeadline(String test) {
        if (test.matches(DEADLINE_VALIDATION_REGEX)) {
            String[] numbers = test.split("-");
            int day = Integer.parseInt(numbers[0]);
            int month = Integer.parseInt(numbers[1]);
            int year = Calendar.getInstance().get(Calendar.YEAR);

            //invalid month
            if (month > MONTH_MAX_LIMIT || month < MONTH_MIN_LIMIT) {
                return false;
            } else if (month == 1 || month == 3 || month == 5
                    || month == 7 || month == 8 || month == 10 || month == 12) {
                //invalid day
                if (day > DAY_MAX_LIMIT_LONG || day < DAY_MIN_LIMIT) {
                    return false;
                }
                return true;
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                //invalid day
                if (day > DAY_MAX_LIMIT_SHORT || day < DAY_MIN_LIMIT) {
                    return false;
                }
                return true;
            } else {
                //leap year
                if (((year % 4 == 0) && (year % 100 == 0) && (year % 400 == 0))
                    || ((year % 4 == 0) && (year % 100 != 0))) {
                    //invalid day
                    if (day > DAY_MAX_LIMIT_FEB_LEAP || day < DAY_MIN_LIMIT) {
                        return false;
                    }
                    return true;
                } else {
                    //invalid day
                    if (day > DAY_MAX_LIMIT_FEB || day < DAY_MIN_LIMIT) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
            || (other instanceof Deadline // instanceof handles nulls
            && value.equals(((Deadline) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
