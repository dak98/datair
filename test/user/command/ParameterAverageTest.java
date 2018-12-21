package user.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParameterAverageTest {

    @Test
    void shouldCorrectlyParseArguments() {
        ParameterAverage parameterAverage = new ParameterAverage();
        String args = "PM10 2016-12-17 17:00:00 2016-12-17 17:00:00";
        String[] argsArr = { "Parametr", "2016-12-17 17:00:00", "2016-12-17 17:00:00" };
        assertArrayEquals(argsArr, parameterAverage.parse(args));
    }

    @Test
    void shouldReturnNull() {
        ParameterAverage parameterAverage = new ParameterAverage();
        String args = "Parametr 17:00:00 2016-12-17 17:00:00";
        assertNull(parameterAverage.parse(args));
        String args1 = "Parametr 2016-12-17 17:00:00";
        assertNull(parameterAverage.parse(args1));
        String args2 = "Parametr 2016-12-17 17:00:00 2016-12-17";
        assertNull(parameterAverage.parse(args2));
        String args3 = "";
        assertNull(parameterAverage.parse(args3));
    }
}