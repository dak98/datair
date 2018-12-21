package user.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParameterValueIntegrationTest {
    @Test
    void shouldReturnCorrectValue() {
        ParameterValue parameterValue = new ParameterValue();
        /* Test 1 */
        String command1 = "Działoszyn PM10 2016-12-17 17:00:00";
        String[] args1 = parameterValue.parse(command1);
        assertEquals(-3, parameterValue.outputData(args1));
        /* Test 2 */
        String command2 = "Działoszyn PM 2018-12-17 17:00:00";
        String[] args2 = parameterValue.parse(command2);
        assertEquals(-2, parameterValue.outputData(args2));
        /* Test 3 */
        String command3 = "Dzia PM10 2018-12-17 17:00:00";
        String[] args3 = parameterValue.parse(command3);
        assertEquals(-1, parameterValue.outputData(args3));
        /* Test 4 */
        String command4 = "Dzia P 2018-12-17 17:00:00";
        String[] args4 = parameterValue.parse(command4);
        assertEquals(-1, parameterValue.outputData(args4));
        /* Test 5 */
        String command5 = "Działoszyn PM10 2018-12-17 17:00:00";
        String[] args5 = parameterValue.parse(command5);
        assertEquals(0, parameterValue.outputData(args5));
    }
}