package datair;

import user.command.RunCommand;
import user.menu.Menu;

import java.io.IOException;

public class DatairMain {
    public static void main(String[] args) {
        /* Command - wzorzec projektowy */
        /* Visitor - wzorzec projektowy */
        /* Iterator - wzorzec projektowy - HighestValuesSites */
        Menu menu = new Menu();
        menu.greetins();
        if (args.length == 0) {
            menu.help();
        } else {
            RunCommand runCommand = new RunCommand();
            try {
                runCommand.run(argsToCommand(args));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
    }

    private static String argsToCommand(String[] args) {
        StringBuilder command = new StringBuilder();
        for (String arg : args) {
            command.append(arg)
                   .append(" ");
        }
        return command.toString().trim();
    }
}
