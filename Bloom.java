import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.*; 
import java.util.Random;

public class Bloom
{
  static boolean[] bloomFilter;
  static int hashFunctionsSize;
  static int bloomFilterSize;
  // number of strings to read from the file
  final static int MAX_FILE_READ = 500000;


  public static void main(String[] args)
  {

    // int FPT = 49;
    // double FPRateT = (double) FPT / 100;
    // System.out.println(FPRateT);


    //  always 4 parameters
    if (args.length < 4) {
      System.out.println("Check parameters: [bloomFilterSize] [hashFunctionsSize] [insert words] [words file path]");
      System.exit(0);
    }

    bloomFilterSize = Integer.parseInt(args[0]);
    hashFunctionsSize = Integer.parseInt(args[1]);
    // number of strings to insert
    int insertSize = Integer.parseInt(args[2]);
    String stringsPath = args[3];
    int testSize = Integer.parseInt(args[4]);

    bloomFilter = new boolean[bloomFilterSize];
    reset();

    List<String> wordsList = new ArrayList<String> (); 
    List<String> insertedWordsList = new ArrayList<String> ();
    int[] insertedIndexes = new int[insertSize];

    //  read file words
    try {
      File myObj = new File(stringsPath);
      Scanner myReader = new Scanner(myObj);
      int counter = 0;
      while (myReader.hasNextLine()) {
        String word = myReader.nextLine();
        wordsList.add(word);
        //  insert(word);
        //  System.out.println(word);
        if(++counter >= MAX_FILE_READ) break;
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
      e.printStackTrace();
    }

    // insert specified random words from the list
    // index is updated after removing
    Random rn = new Random();
    for(int i = 0; i < insertSize; i++) {
      // get random number
      int randomIndex = rn.nextInt( (MAX_FILE_READ - 1 - i) - 0 + 1) + 0;

      // insert
      String word = wordsList.get(randomIndex);
      insert( word );
      insertedWordsList.add(word);
      insertedIndexes[i] = randomIndex;

      wordsList.remove(randomIndex);
    }

    // no need since index is updated after remove
    // remove inserted words from the list
    // for(int i = 0; i < insertSize; i++) {
    //   wordsList.remove( insertedIndexes[i] );
    // }

    // another way to reset the list
    // wordsList = Arrays.asList( wordsList.toArray() );

    // no need since index is updated after remove
    // reset list indexes
    // List<String> wordsList2 = new ArrayList<String> (); 
    // for(String s : wordsList) {
    //   wordsList2.add(s);
    // }
    // wordsList = wordsList2;
    // wordsList2 = new ArrayList<String> ();

    //  check the list size
    //  System.out.println(wordsList.size());

    // test 100 non-inserted random words
    // False Positive counter
    int FP = 0;
    for(int i = 0; i < testSize; i++) {
      // get random number
      int randomIndex = rn.nextInt( (wordsList.size() - 1) - 0 + 1) + 0;

      //
      String word = wordsList.get(randomIndex);
      if ( testmembership(word) )
        FP++;
      
      double FPRate = (double) FP / (i+1);
      System.out.println(FPRate);
    }

    // double FPRate = (double) FP / 100;
    // System.out.println(FPRate);


    Scanner keyboard = new Scanner(System.in);
    int choice = 0; //////////// change
    while(choice != 0) {
      System.out.println("\nSelect:\n1 \t Insert");
      System.out.println("2 \t Test Membership ");
      System.out.println("3 \t Reset");
      System.out.println("0 \t Exit");
      choice = Integer.parseInt( keyboard.nextLine() );

      //  insert
      if(choice == 1) {
        System.out.print("String: ");
        String s = keyboard.nextLine();
        insert(s);
        System.out.println("");
      }
      //  test
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

 //  hash function
 static long sfold(String s, int seed, int M) {
     int intLength = s.length() / 4;
     long sum = 0;
     for (int j = 0; j < intLength; j++) {
       char c[] = s.substring(j * 4, (j * 4) + 4).toCharArray();
       long mult = 1;
       for (int k = 0; k < c.length; k++) {
        sum += c[k] * mult;
        mult *= 256;
       }
     }

     char c[] = s.substring(intLength * 4).toCharArray();
     long mult = 1;
     for (int k = 0; k < c.length; k++) {
       sum += c[k] * mult;
       mult *= 256;
     }

     return( (Math.abs(sum) + seed) % M);
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