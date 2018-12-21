package user.menu;

/**
 * Displays user menus.
 */
public class Menu {
    /**
     * Displays default start menu.
     */
    public void greetins() {
        System.out.println("Welcome to Datair!");
        System.out.println("Type one of the available commands or \"help\" for help: ");
        System.out.println("\"exit\" quits the program.");
    }
    /**
     * Displays list of commands with their arguments.
     */
    public void help() {
        System.out.println("List of available commands: ");
        System.out.println("- cai station_name                                -- current air index for station_name.");
        System.out.println("- pv  station_name parameter day time             -- value of parameter for station_name and date.");
        System.out.println("- pa  parameter startDay startTime endDay endTime -- average value of parameter in time interval.");
        System.out.println("- lf  day time                                    -- parameter with largest fluctuation since given date.");
        System.out.println("- lv  day time                                    -- parameter with lowest value on given date.");
        System.out.println("- hvs parameter number_of_stations day time       -- stations with larges value of given parameter on specified date.");
        System.out.println("- epv parameter                                   -- dates and stations with largest and lowest values of parameter.");
        System.out.println("- cg  parameter startDay startTime endDay endTime -- graphs changes of parameter.");


        System.out.println("\nFormatting of the arguments:");
        System.out.println("- Parameter can be one of: ST, SO2, NO2, CO, PM10, PM25, O3, C6H6. It must be written with capital letters.");
        System.out.println("- Day format: yyyy-mm-dd");
        System.out.println("- Time format: hh-mm-ss");

    }
}
