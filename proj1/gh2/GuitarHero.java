package gh2;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class GuitarHero {
    private static final double CONCERT_A = 440.0;
    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        GuitarString currString = new GuitarString(CONCERT_A);

        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                for(int i = 0; i < keyboard.length(); ++i) {
                    if(keyboard.charAt(i) == key) {
                        // create a guitar string for that note
                        double currFreq = CONCERT_A * Math.pow(2, (i - 24) / 12);
                        currString = new GuitarString(currFreq);
                        currString.pluck();

                        break;
                    }
                }
            }

            /* compute the superposition of samples */
            double sample = currString.sample();

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            currString.tic();
        }
    }
}

