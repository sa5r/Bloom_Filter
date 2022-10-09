import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Bloom
{
  static boolean[] bloomFilter;
  static int hashFunctionsSize;
  static int bloomFilterSize;

  public static void main(String[] args)
  {
    // always 4 parameters
    if (args.length < 4) {
      System.exit(0);
    }

    bloomFilterSize = Integer.parseInt(args[0]);
    hashFunctionsSize = Integer.parseInt(args[1]);
    int insertSize = Integer.parseInt(args[2]);
    String stringsPath = args[3];

    bloomFilter = new boolean[bloomFilterSize];

    reset();

    // read file words
    try {
      File myObj = new File(stringsPath);
      Scanner myReader = new Scanner(myObj);
      int counter = 0;
      while (myReader.hasNextLine()) {
        String word = myReader.nextLine();
        insert(word);
        // System.out.println(word);
        if(++counter >= insertSize) break;
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
      e.printStackTrace();
    }

    Scanner keyboard = new Scanner(System.in);
    int choice = -1;
    while(choice != 0) {
      System.out.println("\nSelect:\n1 \t Insert");
      System.out.println("2 \t Test Membership ");
      System.out.println("3 \t Reset");
      System.out.println("0 \t Exit");
      choice = Integer.parseInt( keyboard.nextLine() );

      // insert
      if(choice == 1) {
        System.out.print("String: ");
        String s = keyboard.nextLine();
        insert(s);
        System.out.println("");
      }
      // test
      else if(choice == 2) {
        System.out.print("String: ");
        String s = keyboard.nextLine();
        boolean result = testmembership(s);
        if(result){
          System.out.println("Found");
        } else {
          System.out.println("Not found");
        }
      } else if(choice == 3) {
        reset();
      }
    }
  
  }

 // hash function
 static long sfold(String s, int seed, int M) {
     int intLength = s.length() / seed;
     long sum = 0;
     for (int j = 0; j < intLength; j++) {
       char c[] = s.substring(j * seed, (j * seed) + seed).toCharArray();
       long mult = 1;
       for (int k = 0; k < c.length; k++) {
        sum += c[k] * mult;
        mult *= 256;
       }
     }

     char c[] = s.substring(intLength * seed).toCharArray();
     long mult = 1;
     for (int k = 0; k < c.length; k++) {
       sum += c[k] * mult;
       mult *= 256;
     }

     return(Math.abs(sum) % M);
   }

   static void insert(String s) {
     for(int i = 1; i <= hashFunctionsSize; i++) {
       int index = (int) sfold(s, i, bloomFilterSize) ;
       bloomFilter[index] = true;

     }
   }

   static void reset() {
     for(int i = 0; i < bloomFilterSize; i++) {
      bloomFilter[i] = false;
    }
   }

   static boolean testmembership(String s) {
     for(int i = 1; i <= hashFunctionsSize; i++) {
       int index = (int) sfold(s, i, bloomFilterSize) ;
       if( bloomFilter[index] == false ) {
         return false;
       }
     }
     return true;
   }

}