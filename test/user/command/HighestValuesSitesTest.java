package user.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HighestValuesSitesTest {

    @Test
    void shouldCorrectlyParseArguments() {
        HighestValuesSites highestValuesSites = new HighestValuesSites();
        String args = "Parametr 5 2016-12-17 17:00:00";
        String[] argsArr = { "Parametr", "5", "2016-12-17 17:00:00" };
        assertArrayEquals(argsArr, highestValuesSites.parse(args));
    }

    @Test
    void shouldReturnNull() {
        HighestValuesSites highestValuesSites = new HighestValuesSites();
        String args = "Parametr 2016-12-17 17:00:00";
        assertNull(highestValuesSites.parse(args));
        String args1 = "5 2016-12-17 17:00:00";
        assertNull(highestValuesSites.parse(args1));
        String args2 = "Parametr 2016-12-17";
        assertNull(highestValuesSites.parse(args2));
        String args3 = "Parametr 5 2 2016-12-17 17:00:00";
        assertNull(highestValuesSites.parse(args3));
        String args4 = "";
        assertNull(highestValuesSites.parse(args3));
    }
}