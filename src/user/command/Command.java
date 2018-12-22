package user.command;

import java.io.IOException;

/**
 * Abstract class for commands available for the user of the program.
 */
public abstract class Command {
    /**
     * Parses given command to get the arguments
     * or return an error if some of them are missing.
     *
     * @param args
     *         String  with arguments.
     * @return Array of arguments.
     *         null if not enough arguments.
     */
    public abstract String[] parse(String args);
    /**
     * Present data return from given command.
     * @param args
     *         Array of arguments.
     * @return Negative integer - error code.
     *         0 - correct execution.
     */
    public abstract int outputData(String[] args) throws IOException;
}
