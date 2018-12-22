package user.command;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LargestFluctuationIntegrationTest {
    @Test
    void shouldReturnCorrectValue() {
        try {
            LargestFluctuation largestFluctuation = new LargestFluctuation();
            /* Test 1 */
            String command1 = "2019-12-17 17:00:00";
            String[] args1 = largestFluctuation.parse(command1);
            assertEquals(-6, largestFluctuation.outputData(args1));
            /* Test 2 */
            String command2 = "2018-12-20 17:00:00";
            String[] args2 = largestFluctuation.parse(command2);
            assertEquals(-6, largestFluctuation.outputData(args2));
            /* Test 3 */
            String command3 = "2018-14-17 21:00:00";
            String[] args3 = largestFluctuation.parse(command3);
            assertEquals(-6, largestFluctuation.outputData(args3));
            /* Test 4 */
            String command4 = "2018-19-17 17:00:00";
            String[] args4 = largestFluctuation.parse(command4);
            assertEquals(-6, largestFluctuation.outputData(args4));
            /* Test 5 */
            String command5 = "2018-12-17 17:00:00";
            String[] args5 = largestFluctuation.parse(command5);
            assertEquals(0, largestFluctuation.outputData(args5));
        } catch (IOException e) {

        }
    }
}