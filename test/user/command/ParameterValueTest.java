package user.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParameterValueTest {

    @Test
    void shouldCorrectlyParseArguments() {
        ParameterValue parameterValue = new ParameterValue();
        String args = "Stacja Parametr 2016-12-17 17:00:00";
        String[] argsArr = { "Stacja", "Parametr", "2016-12-17 17:00:00" };
        assertArrayEquals(argsArr, parameterValue.parse(args));
    }

    @Test
    void shouldReturnNull() {
        ParameterValue parameterValue = new ParameterValue();
        String args = "Parametr 2016-12-17 17:00:00";
        assertNull(parameterValue.parse(args));
        String args1 = "Stacja 2016-12-17 17:00:00";
        assertNull(parameterValue.parse(args1));
        String args2 = "Stacja Parametr 2016-12-17";
        assertNull(parameterValue.parse(args2));
        String args3 = "";
        assertNull(parameterValue.parse(args3));
    }
}