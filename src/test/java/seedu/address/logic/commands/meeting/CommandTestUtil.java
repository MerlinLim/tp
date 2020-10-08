package seedu.address.logic.commands.meeting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.logic.parser.meeting.CliSyntax.PREFIX_CONTACTS;
import static seedu.address.logic.parser.meeting.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.meeting.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.meeting.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.meeting.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.meeting.CliSyntax.PREFIX_TO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.meeting.MeetingBook;
import seedu.address.model.meeting.ModelMeeting;
import seedu.address.model.meeting.meeting.Meeting;
import seedu.address.model.meeting.meeting.TitleContainsKeywordsPredicate;
//import seedu.address.testutil.EditPersonDescriptorBuilder;
//import seedu.address.testutil.MeetingBuilder;

public class CommandTestUtil {

    public static final String VALID_TITLE_A = "Meeting 1";
    public static final String VALID_TITLE_B = "Meeting 2";
    public static final String VALID_DESCRIPTION_A = "With business associates";
    public static final String VALID_DESCRIPTION_B = "With product designers";
    public static final String VALID_FROM_A = "01-01-2020 14:00";
    public static final String VALID_FROM_B = "01-01-2020 12:00";
    public static final String VALID_TO_A = "02-01-2020 14:00";
    public static final String VALID_TO_B = "02-01-2020 12:00";
    ;
    public static final String VALID_CONTACTS_A = "1,2,3";
    public static final String VALID_CONTACTS_B = "4,5,6";
    public static final String VALID_LOCATION_A = "Singapore";
    public static final String VALID_LOCATION_B = "Jarkata";

    public static final String TITLE_DESC_A = " " + PREFIX_TITLE + VALID_TITLE_A;
    public static final String TITLE_DESC_B = " " + PREFIX_TITLE + VALID_TITLE_B;
    public static final String DESCRIPTION_DESC_A = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_A;
    public static final String DESCRIPTION_DESC_B = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_B;
    public static final String FROM_DESC_A = " " + PREFIX_FROM + VALID_FROM_A;
    public static final String FROM_DESC_B = " " + PREFIX_FROM + VALID_FROM_B;
    public static final String TO_DESC_A = " " + PREFIX_TO + VALID_TO_A;
    public static final String TO_DESC_B = " " + PREFIX_TO + VALID_TO_B;
    public static final String CONTACTS_DESC_A = " " + PREFIX_CONTACTS + VALID_CONTACTS_A;
    public static final String CONTACTS_DESC_B = " " + PREFIX_CONTACTS + VALID_CONTACTS_B;
    public static final String LOCATION_DESC_A = " " + PREFIX_LOCATION + VALID_LOCATION_A;
    public static final String LOCATION_DESC_B = " " + PREFIX_LOCATION + VALID_LOCATION_B;

    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + "Meeting&"; // '&' not allowed in names
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + ""; // 'a' not allowed in phones
    public static final String INVALID_FROM_DESC = " " + PREFIX_FROM + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_TO_DESC = " " + PREFIX_TO; // empty string not allowed for addresses
    public static final String INVALID_CONTACTS_DESC = " " + PREFIX_CONTACTS + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION + "Home*"; // 'friend' not allowed in roles

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

//    public static final Meeting DESC_A;
//    public static final Meeting DESC_B;
//
//    static {
//        DESC_A = new MeetingBuilder().withTitle(VALID_TITLE_A)
//                .withDescription(VALID_DESCRIPTION_A).withFrom(VALID_FROM_A).withTo(VALID_TO_A)
//                .withContacts(VALID_CONTACTS_A).withLocation(VALID_LOCATION_A).build();
//        DESC_B = new MeetingBuilder().withTitle(VALID_TITLE_B)
//                .withDescription(VALID_DESCRIPTION_B).withFrom(VALID_FROM_B).withTo(VALID_TO_B)
//                .withContacts(VALID_CONTACTS_B).withLocation(VALID_LOCATION_B).build();
//    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, ModelMeeting actualModelMeeting,
                                            CommandResult expectedCommandResult, ModelMeeting expectedModelPerson) {
        try {
            CommandResult result = command.execute(actualModelMeeting);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModelPerson, actualModelMeeting);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, ModelMeeting, CommandResult, ModelMeeting)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, ModelMeeting actualModelMeeting, String expectedMessage,
                                            ModelMeeting expectedModelMeeting) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModelMeeting, expectedCommandResult, expectedModelMeeting);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, ModelMeeting actualModelMeeting, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        MeetingBook expectedMeetingBook = new MeetingBook(actualModelMeeting.getMeetingBook());
        List<Meeting> expectedFilteredList = new ArrayList<>(actualModelMeeting.getFilteredMeetingList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModelMeeting));
        assertEquals(expectedMeetingBook, actualModelMeeting.getMeetingBook());
        assertEquals(expectedFilteredList, actualModelMeeting.getFilteredMeetingList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showMeetingAtIndex(ModelMeeting modelMeeting, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < modelMeeting.getFilteredMeetingList().size());

        Meeting meeting = modelMeeting.getFilteredMeetingList().get(targetIndex.getZeroBased());
        final String[] splitName = meeting.getTitle().value.split("\\s+");
        modelMeeting.updateFilteredMeetingList(new TitleContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, modelMeeting.getFilteredMeetingList().size());
    }
}