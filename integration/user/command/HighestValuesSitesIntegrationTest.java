package user.command;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HighestValuesSitesIntegrationTest {
    @Test
    void shouldReturnCorrectValue() {
        try {
            HighestValuesSites highestValuesSites = new HighestValuesSites();
            /* Test 1 */
            String command1 = "N 2 2018-12-17 17:00:00";
            String[] args1 = highestValuesSites.parse(command1);
            assertEquals(-4, highestValuesSites.outputData(args1));
            /* Test 2 */
            String command2 = "PM 2 2018-12-17 21:00:01";
            String[] args2 = highestValuesSites.parse(command2);
            assertEquals(-4, highestValuesSites.outputData(args2));
            /* Test 3 */
            String command3 = "NO2 5 2018-12-17 7:00:01";
            String[] args3 = highestValuesSites.parse(command3);
            assertEquals(-8, highestValuesSites.outputData(args3));
            /* Test 4 */
            String command4 = "O3 2 2018-12-13 17:00:00";
            String[] args4 = highestValuesSites.parse(command4);
            assertEquals(-8, highestValuesSites.outputData(args4));
            /* Test 5 */
            String command5 = "NO2 5 2018-12-17 17:00:00";
            String[] args5 = highestValuesSites.parse(command5);
            assertEquals(0, highestValuesSites.outputData(args5));
        } catch (IOException e) {

        }
    }
}