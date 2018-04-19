package antiprimes;


import java.util.logging.Logger;

/**
 * Class representing a thread which searches antiprime numbers in background.
 *
 * After the thread is started the clients will ask to search for the successor of a given antiprime
 * (which is stored in the 'request' attribute).
 *
 * When 'request' is changed the thread starts searching a bigger antiprime.  When the search finishes the result
 * is notified to the sequence object associated to the thread.
 *
 * The attribute 'request' is set to null when there are no requests.
 */
public class NumberProcessor extends Thread {

    private final static Logger LOGGER = Logger.getLogger(NumberProcessor.class.getName());

    /**
     * The antiprime of which the successor must be computed.
     */
    Number request = null;

    /**
     * The sequence of antiprimes that is extended by the processor.
     */
    AntiPrimesSequence sequence;

    /**
     * Initialize the processor.
     *
     * @param sequence the sequence where the new number found are added
     */
    public NumberProcessor(AntiPrimesSequence sequence) {
        this.sequence = sequence;
    }

    /**
     * Main method of the thread.
     */
    public void run() {
        LOGGER.info("Processor ready");
        acceptRequests();
        // Process forever.  It would be better to design a mechanism to gracefully shutdown the thread.
        for ( ; ; ) {
            try {
                LOGGER.info("Waiting a new request");
                Number n = getNextToProcess();
                LOGGER.info("Searching the successor of " + n.getValue() + " ...");
                Number m = AntiPrimes.nextAntiPrimeAfter(n);
                LOGGER.info("Found " + m.getValue() + " with " + m.getDivisors() + " divisors");
                sequence.addAntiPrime(m);
                acceptRequests();
            } catch (InterruptedException e) {
                LOGGER.severe(e.getMessage());
                break;
            }
        }
    }

    /**
     * Ask the processor to compute the successor of n in the antiprime sequence.
     * 
     * If the processor is busy the caller will block until the processor can receive the request.
     * The method will return without waiting the end of the computation.
     */
    synchronized public void nextAntiPrime(Number n) throws InterruptedException {
        while (request != null) {
            if (request.getValue() == n.getValue())
                return;
            wait();
        }
        request = n;
        notify();
    }

    /**
     * Retrieve the next request.  Wait for a new request if there are none.
     */
    synchronized private Number getNextToProcess() throws InterruptedException {
        while (request == null)
            wait();
        return request;
    }

    /**
     * Make the processor ready for new computations.
     */
    synchronized private void acceptRequests() {
        request = null;
        notify();
    }
}
