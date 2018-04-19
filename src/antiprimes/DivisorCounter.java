package antiprimes;


import java.util.logging.Logger;

/**
 * Independent thread that counts the divisors of numbers.  This class is used by NumberProcessorMT to make parallel
 * the search for antiprime numbers.
 */
public class DivisorCounter extends Thread {

    private final static Logger LOGGER = Logger.getLogger(DivisorCounter.class.getName());

    /**
     * Corresponding number processor.
     */
    NumberProcessorMT numberProcessor;

    /**
     * Create a new instance for the givev processor.
     */
    public DivisorCounter(NumberProcessorMT np) {
        numberProcessor = np;
    }

    /**
     * Thread body.
     */
    public void run() {
        for (;;) {
            try {
                LOGGER.info(currentThread().getName() + ": ask for a new integer to process");
                long n = numberProcessor.nextNumberToProcess();
                LOGGER.info(currentThread().getName() + " start to process " + n);
                long d = AntiPrimes.countDivisors(n);
                LOGGER.info(currentThread().getName() + " found that " + n + " has " + d + " divisors");
                numberProcessor.passResult(new Number(n, d));
            } catch (InterruptedException e) {
                LOGGER.severe(e.getMessage());
            }
        }
    }
}
