package antiprimes;

import java.util.ArrayList;
import java.util.List;


/**
 * Represent the sequence of antiprimes found so far.
 */
public class AntiPrimesSequence {

    /**
     * The numbers in the sequence.
     */
    private List<Number> antiPrimes = new ArrayList<>();
    private NumberProcessor processor;
    private List<Observer> observers = new ArrayList<>();

    /**
     * Create a new sequence containing only the first antiprime (the number '1').
     */
    public AntiPrimesSequence() {
        processor = new NumberProcessor(this);
        this.reset();
        processor.start();
    }

    /**
     * Register a new observer.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Clear the sequence so that it contains only the first antiprime (the number '1').
     */
    public void reset() {
        antiPrimes.clear();
        addAntiPrime(new Number(1, 1));
    }

    /**
     * Extend the sequence to include a new antiprime.
     */
    synchronized public void addAntiPrime(Number number) {
        antiPrimes.add(number);
        for (Observer observer : observers)
            observer.update();
    }

    /**
     * Find a new antiprime and add it to the sequence.
     */
    public void computeNext() {
        try {
            processor.nextAntiPrime(getLast());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return the last antiprime found.
     */
    synchronized public Number getLast() {
        int n = antiPrimes.size();
        return antiPrimes.get(n - 1);
    }

    /**
     * Return the last k antiprimes found.
     */
    synchronized public List<Number> getLastK(int k) {
        int n = antiPrimes.size();
        if (k > n)
            k = n;
        return antiPrimes.subList(n - k, n);
    }
}
