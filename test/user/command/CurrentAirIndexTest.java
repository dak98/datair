package user.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrentAirIndexTest {

    @Test
    void shouldCorrectlyParseArguments() {
        CurrentAirIndex currentAirIndex = new CurrentAirIndex();
        String args = "Stacja Nazwa Stacji";
        String[] argsArr = { "Stacja Nazwa Stacji" };
        assertArrayEquals(argsArr, currentAirIndex.parse(args));
    }

    @Test
    void shouldReturnNull() {
        CurrentAirIndex currentAirIndex = new CurrentAirIndex();
        String args = "";
        assertNull(currentAirIndex.parse(args));
    }
}