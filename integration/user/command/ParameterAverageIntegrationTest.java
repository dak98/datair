package user.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParameterAverageIntegrationTest {
    @Test
    void shouldReturnCorrectValue() {
        ParameterAverage parameterAverage = new ParameterAverage();
        /* Test 1 */
        String command1 = "N 2018-12-17 17:00:00 2018-12-17 18:00:00";
        String[] args1 = parameterAverage.parse(command1);
        assertEquals(-4, parameterAverage.outputData(args1));
        /* Test 2 */
        String command2 = "PM10 2018-12-17 17:00:01 2018-12-17 17:30:00";
        String[] args2 = parameterAverage.parse(command2);
        assertEquals(-5, parameterAverage.outputData(args2));
        /* Test 3 */
        String command3 = "NO2 2019-12-17 17:00:00 2019-12-17 18:00:00";
        String[] args3 = parameterAverage.parse(command3);
        assertEquals(-5, parameterAverage.outputData(args3));
        /* Test 4 */
        String command4 = "NO 2018-12-17 17:00:01 2018-12-17 17:30:00";
        String[] args4 = parameterAverage.parse(command4);
        assertEquals(-4, parameterAverage.outputData(args4));
        /* Test 5 */
        String command5 = "NO2 2018-12-17 17:00:00 2018-12-18 9:00:00";
        String[] args5 = parameterAverage.parse(command5);
        assertEquals(0, parameterAverage.outputData(args5));
    }
}