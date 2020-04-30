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
        /*
        //System.out.println(modExp(n, new BigInteger("2"), e)); // just a test
        //  | theta |  e  |  v  |  q  |  s  |  t  |
        //  ---------------------------------------

        // v = theta % e
        // q = theta/e   -or-  q * e  = theta
        // s = t^prime
        // t = s^prime - (t^prime) * q

        // d*e = Z sub phi
        // theta = (p-1) * (q-1)
        // n = p * q   --alt--   n / q = p   --or--   n / p = q
         */

        // exam question below
        BigInteger cE = new BigInteger ("1173");
        BigInteger nE = new BigInteger ("2501");
        BigInteger P = new BigInteger ("0");

        BigInteger eE = new BigInteger ("2501");
        BigInteger sqN = nE.sqrt();
        BigInteger sqNtemp = sqN.subtract(BigInteger.ONE);

        System.out.println(eE.compareTo(BigInteger.ZERO));
        System.out.println("sqnTemp -- " + sqNtemp);


        while (sqNtemp.compareTo(BigInteger.ZERO) >= 1){
            System.out.println("sqnTemp -- " + sqNtemp + " n%sqnTemp -- " + nE.mod(sqNtemp));
            if (nE.mod(sqNtemp).compareTo(BigInteger.ZERO) == 0) {
                P = sqNtemp;
                break;
            }
            sqNtemp = sqNtemp.subtract(BigInteger.TWO);
        }

        // q = n/p
        BigInteger q = nE.divide(P);
        System.out.println(q + "good q");


    }
}