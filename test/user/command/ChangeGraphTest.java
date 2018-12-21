package user.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangeGraphTest {

    @Test
    void shouldCorrectlyParseArguments() {
        ChangeGraph changeGraph = new ChangeGraph();
        String args = "Parametr 2016-12-17 17:00:00 2016-12-17 19:00:00";
        String[] argsArr = { "Parametr", "2016-12-17 17:00:00", "2016-12-17 19:00:00" };
        assertArrayEquals(argsArr, changeGraph.parse(args));
    }

    @Test
    void shouldReturnNull() {
        ChangeGraph changeGraph = new ChangeGraph();
        String args = "Parametr 2016-12-17 17:00:00";
        assertNull(changeGraph.parse(args));
        String args1 = "Parametr 2016-12-17";
        assertNull(changeGraph.parse(args1));
        String args2 = "2016-12-17";
        assertNull(changeGraph.parse(args2));
        String args3 = "";
        assertNull(changeGraph.parse(args3));
        String args4 = "Parametr Parametr 2016-12-17 17:00:00 2016-12-17 19:00:00";
        assertNull(changeGraph.parse(args4));
        String args5 = "Parametr Parametr 2016-12-17 19:00:00";
        assertNull(changeGraph.parse(args5));
    }
}