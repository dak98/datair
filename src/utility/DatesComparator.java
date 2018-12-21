package utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Group of methods for comparing dates.
 * Date format     : yyyy-mm-dd yyyy-mm-dd hh:mm:ss.
 */
public class DatesComparator {
    /**
     * @param date1S
     *         String with first date.
     * @param date2S
     *         String with second date.
     * @return True if date1 is earlier than date2 (or equal).
     *         False otherwise (and if date could not be parsed).
     */
    public boolean isEarlierDate(String date1S, String date2S) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(date1S);
            date2 = sdf.parse(date2S);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Could not parse date.");
            return false;
        }
        return !date1.after(date2);
    }
    /**
     * @param date1S
     *         String with first date.
     * @param date2S
     *         String with second date.
     * @return True if date1 is later than date2 (or equal).
     *         False otherwise (and if date could not be parsed).
     */
    public boolean isLaterDate(String date1S, String date2S) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(date1S);
            date2 = sdf.parse(date2S);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Could not parse date.");
            return false;
        }
        return !date1.before(date2);
    }
    /**
     * @param date
     *         String with date to be checked.
     * @param lowerBoundDate
     *         String with date being lower bound.
     * @param upperBoundDate
     *         String with date being upper bound.
     * @return True if date is between lower and upper bound dates (inclusive).
     *         False otherwise (and if date could not be parsed).
     */
    public boolean isBetweenDates(String date, String lowerBoundDate, String upperBoundDate) {
        return isLaterDate(date, lowerBoundDate) && isEarlierDate(date, upperBoundDate);
    }
}
