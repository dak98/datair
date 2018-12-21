package user.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrentAirIndexIntegrationTest {
    @Test
    void shouldReturnCorrectValue() {
        CurrentAirIndex currentAirIndex = new CurrentAirIndex();
        /* Test 1 */
        String command1 = "Stacja Nazwa Stacji";
        String[] args1 = currentAirIndex.parse(command1);
        assertEquals(-1, currentAirIndex.outputData(args1));
        /* Test 2 */
        String command2 = "Diałoszyn";
        String[] args2 = currentAirIndex.parse(command2);
        assertEquals(-1, currentAirIndex.outputData(args2));
        /* Test 3 */
        String command3 = "Dzialoszyn";
        String[] args3 = currentAirIndex.parse(command3);
        assertEquals(-1, currentAirIndex.outputData(args3));
        /* Test 4 */
        String command4 = "Kraków ul. dr hab Jerzego Wenty";
        String[] args4 = currentAirIndex.parse(command4);
        assertEquals(-1, currentAirIndex.outputData(args4));
        /* Test 5 */
        String command5 = "Kraków ul Krasińskiego";
        String[] args5 = currentAirIndex.parse(command5);
        assertEquals(-1, currentAirIndex.outputData(args5));
        /* Test 4 */
        String command6 = "Działoszyn";
        String[] args6 = currentAirIndex.parse(command6);
        assertEquals(0, currentAirIndex.outputData(args6));
    }

}