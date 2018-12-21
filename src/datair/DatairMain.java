package datair;

import data.collector.AirDataCollector;
import user.command.RunCommand;
import user.menu.Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DatairMain {
    public static void main(String[] args) {
        /* Command - wzorzec projektowy */
        /* Visitor - wzorzec projektowy */
        /* Iterator - wzorzec projektowy - HighestValuesSites */
        Menu menu = new Menu();
        RunCommand runCommand = new RunCommand();
        AirDataCollector airDataCollector = new AirDataCollector();
        airDataCollector.load();
        menu.greetins();
        if (args.length == 0) {
            menu.help();
        } else {
            StringBuilder command = new StringBuilder();
            for (String arg : args) {
                command.append(arg + " ");
            }
            runCommand.run(command.toString().trim());
        }
        while (true) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                runCommand.run(reader.readLine());
            } catch (IOException e) {
                System.out.println("Could not read the input.");
            }
        }
    }
}
