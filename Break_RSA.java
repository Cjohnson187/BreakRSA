import java.util.*;
import java.math.BigInteger;

public class Break_RSA {
    /*
    Chris Johnson
    4/26/20
    CS 4050-001 Algorithms
    Project 4 Breaking RSA
    */


    // smaller example for testing
    private static final BigInteger n = new BigInteger("2501");
    private static final BigInteger e = new BigInteger("37");
    private static final BigInteger message = new BigInteger("1173");  // c


    //private static final BigInteger n = new BigInteger("1029059010426758802790503300595323911");
    //private static final BigInteger e = new BigInteger("2287529");
    //private static final BigInteger message = new BigInteger("1027795314451781443748475386257882516");  // c
    // original message is a

    public static void main(String[] args) {

        // initializing variables
        BigInteger p = new BigInteger ("0");
        BigInteger theta = new BigInteger("0");
        BigInteger t = new BigInteger("0");

        // in phase 1
        // change square root of n to negative
        BigInteger sqN = n.sqrt();
        BigInteger oddOrEven = sqN.mod(BigInteger.TWO);
        if (oddOrEven.compareTo(BigInteger.ZERO) == 0) {
            sqN = sqN.subtract(BigInteger.ONE);
        }
        System.out.println("square root of n :  " + sqN + "\nfinished phase 1" );

        // phase 2
        // getting p
        while (sqN.compareTo(BigInteger.ZERO) >= 1){
            if (n.mod(sqN).compareTo(BigInteger.ZERO) == 0) {
                p = sqN;
                break;
            }
            sqN = sqN.subtract(BigInteger.TWO);
        }
        System.out.println("getting gcd of square root of new n :  " + sqN + "\nfinished phase 2" );

        // phase 3
        // getting q  (q = n/p)
        BigInteger q = n.divide(p);
        System.out.println("getting q :  " + q + "\nfinished phase 3" );

        // phase 4
        //get theta -- theta = (p-1)+(q-1)
        theta = ( (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE)) );
        System.out.println("getting theta :  " + sqN + "\nfinished phase 4" );

        // phase 5
        // building table
        BigInteger[][] table = getTable(theta, e);
        System.out.println("getting table :  " + sqN + "\nfinished phase 5" );

        // phase 5
        BigInteger a = modExp(message, table[0][5], n, false);
        System.out.println("a = " + a + "\nfinished phase 5");


        // phase 6
        System.out.println("in phase 6");

        // this is to check for correct a found from c
        BigInteger binEC = new BigInteger("0");
        ArrayList<BigInteger> binVal = new ArrayList<BigInteger>();
        String binE = e.toString(2);

        char one = '1';
        String binER = new StringBuilder(binE).reverse().toString();
        for(int i=0; i < binER.length(); i++) {
            if (binER.charAt(i) == one) {
                binVal.add(  modExp(a, BigInteger.TWO.pow(i), n, false)  ); // c, i, n
            }
        }
        System.out.println("finished phase 6");

        // phase 7
        System.out.println("starting phase 7");

        BigInteger newC = new BigInteger("1");
        for (BigInteger bigInteger : binVal) {
            newC = newC.multiply(bigInteger);
            newC = newC.mod(n);
        }
        System.out.println("binEC finished == " + newC);

        if (newC.compareTo(message) == 0) {
            System.out.println("decrypted correctly, the original message is a=  " + a);
        }

    }

    public static BigInteger[][] getTable( BigInteger f, BigInteger e)
    {
      Scanner keys = new Scanner( System.in );
      BigInteger[][] table = new BigInteger[100][6];
      table[0][0] = f;  table[0][1] = e;
  
      // fill in the first four columns going down to get the gcd:
      int row = 0;
      while( table[row][1].compareTo(BigInteger.ZERO) > 0 )
      {
        table[row][2] = table[row][0].mod( table[row][1] );  // remainder
        table[row][3] = table[row][0].divide( table[row][1] );  // quotient
        table[row+1][0] = table[row][1];  // shift over for next row
        table[row+1][1] = table[row][2];
        row++;
      }
      // go from last row up, filling in the multipliers
      // bottom row multipliers are [1 0] or [1 1]
      if( row % 2 == 1 ) {
        table[row][4] = BigInteger.ONE;
        table[row][5] = BigInteger.ZERO;
      }
      else {
        table[row][4] = BigInteger.ONE;
        table[row][5] = BigInteger.ONE;
      }
  
      // now loop upward
      for( int r=row-1; r>=0; r-- ) {
        table[r][4] = table[r+1][5];
        table[r][5] = table[r+1][4].subtract( table[r+1][5].multiply( table[r][3] ) );
      }
      return table;
    }
 
    
    public static BigInteger modExp( BigInteger a, BigInteger e, BigInteger n,boolean showTrace ){
        BigInteger squares = a;
        BigInteger exp = e;
        BigInteger prod = BigInteger.ONE;

        if( showTrace ) System.out.println("\n");
        while( exp.compareTo( BigInteger.ONE ) >= 0 ){
            if( showTrace ) System.out.printf("%25d %25d ", squares, exp );
            if( exp.mod( BigInteger.TWO ).equals( BigInteger.ONE ) ){// update prod and display
                prod = prod.multiply( squares ).mod( n );
                if( showTrace ) System.out.printf("%25d", prod );
            }
            if( showTrace ) System.out.println();

            // compute next row of values
            squares = squares.multiply( squares ).mod( n );
            exp = exp.divide( BigInteger.TWO );
        }
        return prod;
    }
}