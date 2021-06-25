/**
 * Exercise for learning how the debug, breakpoint, and step-into features
 * work.
 */
public class DebugExercise1 {
    public static int divideThenRound(int top, int bottom) {
        // bug here!
        // int quotient = top / bottom;
        // int result = Math.round(quotient);

        // we'll try not use Math lib.
        double quotient = top * 1.0 / bottom;
        int result = (int) (quotient + 0.5);
        return result;
    }

    public static void main(String[] args) {
        int t = 10;
        int b = 2;
        int result = divideThenRound(t, b);
        System.out.println("round(" + t + "/" + b + ")=" + result);

        int t2 = 9;
        int b2 = 4;
        int result2 = divideThenRound(t2, b2);
        System.out.println("round(" + t2 + "/" + b2 + ")=" + result2);

        int t3 = 3;
        int b3 = 4;
        int result3 = divideThenRound(t3, b3);
        System.out.println("round(" + t3 + "/" + b3 + ")=" + result3);
    }
}
