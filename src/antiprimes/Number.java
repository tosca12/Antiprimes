package antiprimes;

/**
 * A number whose divisors have been counted.
 */
public class Number {
    private long value;
    private long divisors;

    /**
     * Create a new number.
     *
     * @param value the number itself
     * @param divisors the number of its divisors
     */
    public Number(long value, long divisors) {
        this.value = value;
        this.divisors = divisors;
    }

    /**
     * Return the actual value of the number.
     */
    public long getValue() {
        return value;
    }

    /**
     * Return how many integers divide the number.
     */
    public long getDivisors() {
        return divisors;
    }
}
