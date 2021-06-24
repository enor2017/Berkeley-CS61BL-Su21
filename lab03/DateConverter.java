import java.io.*;

public class DateConverter {
    /**
     * Given a day number in 2021, an integer between 1 and 365, as a
     * command-line argument, prints the date in month/day format.
     *
     *     java DateConverter 365
     *
     * should print 12/31
     */
    public static void main(String[] args) {
        int dayOfYear = 0;
        try {
            dayOfYear = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        int month, dateInMonth, daysInMonth;
        month = 1;
        daysInMonth = 31;
        while (dayOfYear > daysInMonth) {
            // Added: two lines below
            month++;
            dayOfYear -= daysInMonth;

            if (month == 2) {
                daysInMonth = 28;
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                daysInMonth = 30;
            } else {
                daysInMonth = 31;
            }
        }
        dateInMonth = dayOfYear;
        System.out.println(month + "/" + dateInMonth);
    }
}
/* Some tests:

    javac DateConverter.java
    java DateConverter 1
    java DateConverter 2
    java DateConverter 31
    java DateConverter 32
    java DateConverter 59
    java DateConverter 60
    java DateConverter 61
    java DateConverter 100
    java DateConverter 200
    java DateConverter 330
    java DateConverter 364
    java DateConverter 365

Output:
    1/1
    1/2
    1/31
    2/1
    2/28
    3/1
    3/2
    4/10
    7/19
    11/26
    12/30
    12/31
 */