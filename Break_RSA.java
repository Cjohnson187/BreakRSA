import java.util.*;
import java.math.BigInteger;

public class Break_RSA {
    /*
    Chris Johnson
    4/26/20
    CS 4050-001 Algorithms
    Project 4 Breaking RSA
    */

    private static final BigInteger n = new BigInteger("1029059010426758802790503300595323911");
    private static final BigInteger e = new BigInteger("2287529");
    private static final BigInteger message = new BigInteger("1027795314451781443748475386257882516");  // c
    // original message is a


    public static void main(String[] args) {
        System.out.println(modExp(n, new BigInteger("2"), e)); // just a test
        //  | theta |  e  |  v  |  q  |  s  |  t  |
        //  ---------------------------------------

        // v = theta % e
        // q = theta/e   -or-  q * e  = theta
        // s = t^prime
        // t = s^prime - (t^prime) * q

        // theta = (p-1) * (q-1)
        // n = p * q   --alt--   n / q = p   --or--   n / p = q

        BigInteger theta, v, q = new BigInteger("0");
        BigInteger s = new BigInteger("1");
        BigInteger t = new BigInteger("0");



    }
    // TODO change to big Int
    public static int[] gcd( int a, int b )
    {
        int[] x = new int[3];
        if( a == 0 )
        {
            x[0] = b; x[1] = 0; x[2] = 1;
            return x;
        }
        else if( b == 0 )
        {
            x[0] = a; x[1] = 1; x[2] = 0;
            return x;
        }
        else
        {
            int q = a/b, r = a%b;
            int[] xprime = gcd( b, r );
            x[0] = xprime[0];
            x[1] = xprime[2]; x[2] = xprime[1] - q*xprime[2];
            return x;
        }
    }
    // gcd method foirm ch4 algo
    public static int gcd( int a, int b )
    {
        if( a == 0 ) return b;
        else if( b == 0 ) return a;
        else{
            int q = a/b, r = a%b;
            return gcd( b, r );
        }
    }

    //get p and q for n tests
    public static BigInteger findPQ() {

    }

    //find s and t
    public static BigInteger up() {

    }

    //reduce theta and e
    public static BigInteger down() {

    }

    // method from Schultz- ModExp.java
    public static BigInteger modExp( BigInteger a, BigInteger e, BigInteger n ) {
        BigInteger squares = a;
        BigInteger exp = e;
        BigInteger prod = one;

        while( exp.compareTo( one ) >= 0 ) {
            if( exp.mod( two ).equals( one ) ) {
                // update prod and display
                prod = prod.multiply( squares ).mod( n );
            }
            // compute next row of values
            squares = squares.multiply( squares ).mod( n );
            exp = exp.divide( two );
        }
        return prod;
    }

}