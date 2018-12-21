package utility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatesComparatorTest {

    @Test
    void shouldBeAnEalierDate() {
        DatesComparator datesComparator = new DatesComparator();
        String date1 = "2014-12-11 14:53:21";
        String[] date2 = { "2015-12-11 14:53:21", "2014-12-12 14:53:21", "2014-12-11 14:53:22" };
        for (String date : date2) {
            assertTrue(datesComparator.isEarlierDate(date1, date));
        }
    }

    @Test
    void shouldBeAnLaterDate() {
        DatesComparator datesComparator = new DatesComparator();
        String date1 = "2014-12-11 14:53:21";
        String[] date2 = { "2013-12-11 14:53:21", "2014-11-12 14:53:21", "2014-12-11 14:52:21" };
        for (String date : date2) {
            assertTrue(datesComparator.isLaterDate(date1, date));
        }
    }

    @Test
    void shouldBeBetweenDates() {
        DatesComparator datesComparator = new DatesComparator();
        String date1 = "2014-12-11 14:53:21";
        String[] date2 = { "2013-12-11 14:53:21", "2014-11-12 14:53:21", "2014-12-11 14:52:21" };
        String date3 = "2014-12-11 15:53:21";
        for (String date : date2) {
            assertTrue(datesComparator.isBetweenDates(date1, date, date3));
        }
    }
}