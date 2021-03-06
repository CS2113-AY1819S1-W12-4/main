//@@author liu-tianhang
package seedu.address.logic.parser.user;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHENTICATION_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;
//@@author tianhang

import seedu.address.authentication.PasswordUtils;
import seedu.address.commons.core.LoginInfo;
import seedu.address.logic.commands.user.CreateAccountCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;

import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.user.AuthenticationLevel;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class CreateAccountCommandParser implements Parser<CreateAccountCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateAccountCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME , PREFIX_PASSWORD, PREFIX_AUTHENTICATION_LEVEL);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME , PREFIX_PASSWORD, PREFIX_AUTHENTICATION_LEVEL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateAccountCommand.MESSAGE_USAGE));
        }

        UserName userName = ParserUtil.parseUserName (argMultimap.getValue(PREFIX_USERNAME).get());
        Password password = ParserUtil.parsePassword (argMultimap.getValue (PREFIX_PASSWORD).get ());
        Password hashedPassword = new Password (PasswordUtils.generateSecurePassword (password.toString ()));
        AuthenticationLevel authenticationLevel = ParserUtil.parseAuthenticationLevel(
                                        argMultimap.getValue (PREFIX_AUTHENTICATION_LEVEL).get ());
        LoginInfo newAccount = new LoginInfo (userName, hashedPassword, authenticationLevel);
        return new CreateAccountCommand (newAccount);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent (ArgumentMultimap argumentMultimap , Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
