package antiprimes;


/**
 * Class containing auxiliary methods for the computation of antiprime numbers.
 */
public class AntiPrimes {

    /**
     * Return the smallest number greater than ap having more divisors than ap itself.
     */
    static public Number nextAntiPrimeAfter(Number ap) {
        long n = ap.getValue();
        long divisors;
        do {
            n++;
            divisors = countDivisors(n);
        } while (divisors <= ap.getDivisors());
        return new Number(n, divisors);
    }

    /**
     * Count how many integers exactly divide n.
     */
    static public long countDivisors(long n) {
        long c = 1;
        for (long i = 2; i <= n; i++)
            if (n % i == 0)
                c++;
        return c;
    }
}
