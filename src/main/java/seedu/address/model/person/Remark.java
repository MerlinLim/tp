package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 *
 */
public class Remark {

    public static final String MESSAGE_CONSTRAINTS = "A WhiteSpace is not a valid input";
    private static final String WHITESPACE = " ";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param remark A remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    public static boolean isValidRemark(String remark) {
        return !remark.equals(WHITESPACE);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
