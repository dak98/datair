package user.command;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LowestValueIntegrationTest {
    @Test
    void shouldReturnCorrectValue() {
        try {
            LowestValue lowestValue = new LowestValue();
            /* Test 1 */
            String command1 = "2019-12-17 17:00:00";
            String[] args1 = lowestValue.parse(command1);
            assertEquals(-7, lowestValue.outputData(args1));
            /* Test 2 */
            String command2 = "2014-12-17 17:00:00";
            String[] args2 = lowestValue.parse(command2);
            assertEquals(-7, lowestValue.outputData(args2));
            /* Test 3 */
            String command3 = "2018-14-17 21:00:00";
            String[] args3 = lowestValue.parse(command3);
            assertEquals(-7, lowestValue.outputData(args3));
            /* Test 4 */
            String command4 = "2018-19-17 17:00:00";
            String[] args4 = lowestValue.parse(command4);
            assertEquals(-7, lowestValue.outputData(args4));
            /* Test 5 */
            String command5 = "2018-12-17 17:00:00";
            String[] args5 = lowestValue.parse(command5);
            assertEquals(0, lowestValue.outputData(args5));
        } catch (IOException e) {

        }
    }
}