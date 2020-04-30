import java.util.*;
import java.math.BigInteger;
//import examples.GCD.GCD;


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
        BigInteger p = new BigInteger ("0");
        BigInteger theta = new BigInteger("0");
        BigInteger t = new BigInteger("0");



        BigInteger eE = new BigInteger ("37");
        BigInteger sqN = nE.sqrt();
        BigInteger sqNtemp = sqN.subtract(BigInteger.ONE);

        System.out.println(eE.compareTo(BigInteger.ZERO));
        System.out.println("sqnTemp -- " + sqNtemp);

        // get p
        while (sqNtemp.compareTo(BigInteger.ZERO) >= 1){
            System.out.println("sqnTemp -- " + sqNtemp + " n%sqnTemp -- " + nE.mod(sqNtemp));
            if (nE.mod(sqNtemp).compareTo(BigInteger.ZERO) == 0) {
                p = sqNtemp;
                break;
            }
            sqNtemp = sqNtemp.subtract(BigInteger.TWO);
        }

        // q = n/p
        BigInteger q = nE.divide(p);
        System.out.println(q + "good q");

        //get theta
        // theta = (p-1)+(q-1)
        theta = ( (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE)) );
        System.out.println("theta = " + theta );

        BigInteger[][] table = getTable(theta, eE);
        System.out.println("t = " + table[0][5]);



        BigInteger binEC = new BigInteger("0");
        String binE = eE.toString(2);
        char one = 1;
        System.out.println("binE"+binE+"length = " + binE.length());

        for(int i=0; i < binE.length(); i++) {
            System.out.println("char at - " + binE.charAt(i));
            System.out.println(Character.compare(binE.charAt(i), one).getClass.getName() + "  at i - " + i);
            if (Character.compare(binE.charAt(i), one) == 47) {
                BigInteger exponent = new BigInteger(String.valueOf(i));
                binEC.add(BigInteger.TWO.pow(i));
                System.out.println("binEC length = "+binE.length()+  "binEC = " + binEC);
            
            }
        }
        System.out.println("binEC finished == " + binEC);

        BigInteger modded = modExp(cE, table[0][5], nE, true);
        System.out.println("modded = " + modded);
        System.out.println("37 = " + eE.toString(2));






    }

    public static BigInteger[][] getTable( BigInteger f, BigInteger e)
    {
      Scanner keys = new Scanner( System.in );
      System.out.println("This program will find the GCD of two positive integers");
      //System.out.println("Enter f---the larger of the two integers:\n");
      //BigInteger f = new BigInteger( keys.nextLine() );
      //System.out.println("\nEnter e---the smaller of the two integers:\n");
      //BigInteger e = new BigInteger( keys.nextLine() );
  
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
      if( row % 2 == 1 )
      {
        table[row][4] = BigInteger.ONE;
        table[row][5] = BigInteger.ZERO;
      }
      else
      {
        table[row][4] = BigInteger.ONE;
        table[row][5] = BigInteger.ONE;
      }
  
      // now loop upward
      for( int r=row-1; r>=0; r-- )
      {
        table[r][4] = table[r+1][5];
        table[r][5] = table[r+1][4].subtract( table[r+1][5].multiply( table[r][3] ) );
      }

      
      /*
      // display the entire table:
      System.out.printf("\n%10s %10s %10s %10s %10s %10s\n\n", "f", "e", "f % e", "f / e", "g", "d" );
      for( int r=0; r<=row; r++ )
      {
        System.out.printf("%10d %10d %10d %10d %10d %10d\n", table[r][0], table[r][1],
                                                   table[r][2], table[r][3],
                                                   table[r][4], table[r][5] );
      } */
  
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