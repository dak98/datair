package user.command;

import user.menu.Menu;

/**
 * Contains methods responsible for running
 * command given the the program by the user.
 */
public class RunCommand {
    /**
     * Runs command specified by the user.
     * @param command
     *         String with command. It must start with the command.
     */
    public void run(String command) {
        if (command != null) {
            if (command.equals("exit")) {
                System.exit(0);
            }
            if (command.equals("help")) {
                Menu menu = new Menu();
                menu.help();
            } else {
                Command command1 = getCommandType(command);
                if (command1 == null) {
                    System.out.println("Unknown command type.");
                }
                String[] args = command1.parse(getCommandArgumentsString(command));
                if (args == null) {
                    System.out.println("Incorrect arguments.");
                } else {
                    checkCommandOutput(command1.outputData(args));
                }
            }
        }
    }
    /**
     * @param command
     *         Command specified by the user.
     * @return Instance of class representing command specified
     *         by the user.
     *         null if command is unknown.
     */
    private Command getCommandType(String command) {
        String commandType = command.substring(0, command.indexOf(" "));
        switch (commandType) {
            case "cai": {
                return new CurrentAirIndex();
            }
            case "pv": {
                return new ParameterValue();
            }
            case "pa": {
                return new ParameterAverage();
            }
            case "lf": {
                return new LargestFluctuation();
            }
            case "lv": {
                return new LowestValue();
            }
            case "hvs": {
                return new HighestValuesSites();
            }
            case "epv": {
                return new ExtremeParameterValues();
            }
            case "cg": {
                return new ChangeGraph();
            }
            default: {
                return null;
            }
        }
    }
    /**
     * Removes command type from command.
     * @param command
     *         Command specified by the user.
     * @return String starting with arguments from
     *         the command.
     */
    private String getCommandArgumentsString(String command) {
        return command.substring(command.indexOf(" ")).trim();
    }
    /**
     * Checks output of command and appropriate writes error if necessary.
     * @param output
     *         Output of command
     */
    private void checkCommandOutput(int output) {
        switch (output) {
            case -1: {
                System.out.println("Unknown station name.");
                break;
            }
            case -2: {
                System.out.println("Specified parameter is not checked by station.");
                break;
            }
            case -3: {
                System.out.println("No measurement was made on specified date.");
                break;
            }
            case -4: {
                System.out.println("Unknown parameter code.");
                break;
            }
            case -5: {
                System.out.println("No measurements between specified dates.");
                break;
            }
            case -6: {
                System.out.println("No measurements recorded after specified date.");
                break;
            }
            case -7: {
                System.out.println("No measurements recorded at specified date.");
                break;
            }
            case -8: {
                System.out.println("Parameter is not checked by specified number of stations.");
                break;
            }
            case -9: {
                System.out.println("Parameter is not checked by any station.");
                break;
            }
        }
    }
}
