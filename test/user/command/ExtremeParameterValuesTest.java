package user.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtremeParameterValuesTest {

    @Test
    void shouldCorrectlyParseArguments() {
        ExtremeParameterValues extremeParameterValues = new ExtremeParameterValues();
        String args = "Parameter";
        String[] argsArr = { "Parameter" };
        assertArrayEquals(argsArr, extremeParameterValues.parse(args));
    }

    @Test
    void shouldReturnNull() {
        ExtremeParameterValues extremeParameterValues = new ExtremeParameterValues();
        String args = "Parametr 2016-12-17 17:00:00";
        assertNull(extremeParameterValues.parse(args));
        String args1 = "5 Parametr";
        assertNull(extremeParameterValues.parse(args1));
        String args2 = "Parametr Parametr";
        assertNull(extremeParameterValues.parse(args2));
        String args3 = "";
        assertNull(extremeParameterValues.parse(args3));
    }
}