package user.command;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ExtremeParameterValuesIntegrationTest {
    @Test
    void shouldReturnCorrectValue() {
        try {
            ExtremeParameterValues extremeParameterValues = new ExtremeParameterValues();
            /* Test 1 */
            String command1 = "N";
            String[] args1 = extremeParameterValues.parse(command1);
            assertEquals(-4, extremeParameterValues.outputData(args1));
            /* Test 2 */
            String command2 = "PM";
            String[] args2 = extremeParameterValues.parse(command2);
            assertEquals(-4, extremeParameterValues.outputData(args2));
            /* Test 3 */
            String command3 = "ST";
            String[] args3 = extremeParameterValues.parse(command3);
            assertEquals(-9, extremeParameterValues.outputData(args3));
            /* Test 4 */
            String command4 = "PM25";
            String[] args4 = extremeParameterValues.parse(command4);
            assertEquals(-9, extremeParameterValues.outputData(args4));
            /* Test 5 */
            String command5 = "NO2";
            String[] args5 = extremeParameterValues.parse(command5);
            assertEquals(0, extremeParameterValues.outputData(args5));
        } catch (IOException e) {

        }
    }

}