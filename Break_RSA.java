import java.util.*;
import java.math.BigInteger;

public class Break_RSA {
    /*
    Chris Johnson
    4/26/20
    CS 4050-001 Algorithms
    Project 4 Breaking RSA
    */

    private static BigInteger n = new BigInteger("1029059010426758802790503300595323911");
    private static BigInteger e = new BigInteger("2287529");
    private static BigInteger message = new BigInteger("1027795314451781443748475386257882516");

    public static void main(String[] args) {
        // just a test
        System.out.println(modExp(n, new BigInteger("2"), e));


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