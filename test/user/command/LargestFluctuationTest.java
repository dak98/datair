package user.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LargestFluctuationTest {

    @Test
    void shouldCorrectlyParseArguments() {
        LargestFluctuation largestFluctuation = new LargestFluctuation();
        String args = "2016-12-17 17:00:00";
        String[] argsArr = { "2016-12-17 17:00:00" };
        assertArrayEquals(argsArr, largestFluctuation.parse(args));
    }

    @Test
    void shouldReturnNull() {
        LargestFluctuation largestFluctuation = new LargestFluctuation();
        String args = "Parametr 2016-12-17 17:00:00";
        assertNull(largestFluctuation.parse(args));
        String args1 = "17:00:00";
        assertNull(largestFluctuation.parse(args1));
        String args2 = "";
        assertNull(largestFluctuation.parse(args2));
    }
}