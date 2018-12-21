package user.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LowestValueTest {

    @Test
    void shouldCorrectlyParseArguments() {
        LowestValue lowestValue = new LowestValue();
        String args = "2016-12-17 17:00:00";
        String[] argsArr = { "2016-12-17 17:00:00" };
        assertArrayEquals(argsArr, lowestValue.parse(args));
    }

    @Test
    void shouldReturnNull() {
        LowestValue lowestValue = new LowestValue();
        String args = "Parametr 2016-12-17 17:00:00";
        assertNull(lowestValue.parse(args));
        String args1 = "17:00:00";
        assertNull(lowestValue.parse(args1));
        String args2 = "";
        assertNull(lowestValue.parse(args2));
    }
}