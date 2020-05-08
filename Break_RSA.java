import java.util.*;
import java.math.BigInteger;

public class Break_RSA {
    /*
    Chris Johnson
    4/26/20
    CS 4050-001 Algorithms
    Project 4 Breaking RSA
    */

    private static final BigInteger hundred = new BigInteger("100");

    private static final BigInteger n = new BigInteger("1029059010426758802790503300595323911");
    private static final BigInteger e = new BigInteger("2287529");
    private static final BigInteger message = new BigInteger("1027795314451781443748475386257882516");  // c
    // original message is a

    public static void main(String[] args) {

        // initializing variables
        BigInteger p = new BigInteger ("0");
        BigInteger theta = new BigInteger("0");

        //from wolfram- greatest prime factor(n), p = larger of the two returned
        p = new BigInteger("4758131272895389067293");

        // getting q  (q = n/p)
        BigInteger q = n.divide(p);

        //get theta -- theta = (p-1)+(q-1)
        theta = ( (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE)) );

        // building table from schultz program "getTable"(theta, e)
        BigInteger[][] table = getTable(theta, e);
        BigInteger a = modExp(message, table[0][5], n, false);
        System.out.println("decrypted message a = " + a);


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

        BigInteger newC = new BigInteger("1");
        for (BigInteger bigInteger : binVal) {
            newC = newC.multiply(bigInteger);
            newC = newC.mod(n);
        }
        System.out.println("binEC finished == " + newC);

        if (newC.compareTo(message) == 0) {
            System.out.println("decrypted correctly, the original message is a=  " + a);
        }

        // decode with schultz program decode
        decode(a);

    }

    // from schultz
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

    // from schultz
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

    // from schultz
    public static void decode(BigInteger a) {
        System.out.print("Decoding a \n");

        String s = "";
        while( a.compareTo( BigInteger.ZERO ) > 0 ) {
            int sym = a.mod( hundred ).intValue();
            a = a.divide( hundred );
            s = ""+(char)(sym+31) + s;
        }

        System.out.println("The decoded message is:\n" + s );
    }

}

