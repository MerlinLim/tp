package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.ModelPerson.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.person.Address;
import seedu.address.model.person.person.Email;
import seedu.address.model.person.person.Name;
import seedu.address.model.person.person.Person;
import seedu.address.model.person.person.Phone;
import seedu.address.model.person.person.tag.Tag;


/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";
    public static final String MESSAGE_REMARK_PERSON_SUCCESS = "Remarked placed: Index: %1$d, Remark: %2$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "r/ [REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "r/ Likes to swim.";

    private final Index index;
    private final Remark remark;

    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param remark of the person to be updated to
     */
    public RemarkCommand(Index index, Remark remark) {
        requireAllNonNull(index, remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(ModelPerson modelPerson) throws CommandException {
        requireNonNull(modelPerson);
        List<Person> lastShownList = modelPerson.getFilteredPersonList();

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Name updateName = personToEdit.getName();
        Phone updatePhone = personToEdit.getPhone();
        Address updateAddress = personToEdit.getAddress();
        Email updateEmail = personToEdit.getEmail();
        Set<Tag> updateTagSet = personToEdit.getTags();

        Person editedPerson = new Person (updateName, updatePhone, updateEmail, updateAddress, updateTagSet, remark);

        modelPerson.setPerson(personToEdit, editedPerson);
        modelPerson.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMARK_PERSON_SUCCESS, index.getOneBased(), remark));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }
}
