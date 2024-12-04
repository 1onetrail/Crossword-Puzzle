
// Assignment 1 - Word Search
// Agastya Terrenz Aquila Mamahit
// Date: 10/22/2024
// CS 145
// ITERATION/VERSION: 3.0

// NOTE: NOT ABLE TO MAKE "Scanner scan" ONLY ACCEPT ALPHABETS FOR create();
// CREDIT: Code's reference model (for when I get stuck) https://gist.github.com/mhowse/bfdf8d350e2c6e1cefdd

import java.util.*;
public class AgastyaWordSearchAssignment1 { // This assignment's main purpose is to create a word search generator
                                            // the user will input their words and the word search will be generated
    private static int width;
    private static int length;
    private static int wordCount;
    private static  ArrayList<String> words;
    private static  int[] positions;
    private static char [][] search;
    private static char [][] answer; 
    private  static String input;

    public static void  main  (String [] args){ // The main method, to do a while loop so that the program runs until
        int x;                                  // the user decides to quit.
        char selection; 
        
        do {
            selection = menu();
            x = menuSelection(selection);
            System.out.println();
        } while (x == 0);
    } // end main

    public static char menu() { // Acting as the menu of the entire program allowing the user to choose between different options.
        Scanner console = new Scanner(System.in);
        char selection;

        System.out.println("Welcome to Word Search!");
        System.out.println("This program will generate a Word Search upon creation.");
        System.out.println("You must create a Word Search first before viewing it!");
        System.out.println("You can view the solution to the Word Search if it gets too hard!");
        System.out.println();
        System.out.println("c to create a Word Search Puzzle.");
        System.out.println("v to view the Word Search Puzzle created.");
        System.out.println("s to view the solution to the Word Search Puzzle.");
        System.out.println("q to exit the program, all changes will not be saved.");
        System.out.println();

        while (true) {
            input = console.nextLine().trim();
            if (input.length() == 1) {
                selection = Character.toLowerCase(input.charAt(0));
                break;
            } else {
                System.out.println("Please enter a valid command.");
              }
            console.close();
       }
        return selection;
    } // end menu
    public static int menuSelection(char selection) { // To call methods so the program would run accordingly with the given option.
        int x = 0;
        int fillopt = 0;
        switch (selection) {
            case 'c': // For when the user wants to create the word search.
                clear();
                create();
                measurer();
                wordfiller();
                x = 0;
                break;
            case 'v': // For when the user wants to view the word search.
               System.out.println("Generating Word Search");
               System.out.println();
                emptyslots(1);
                printer(1);
                x = 0;
                break;
            case 's': // For when the user wants to see the answers to the word search.
               System.out.println("Showing Word Search answer");
               System.out.println();
                emptyslots(2);
                printer(2);
                x = 0;
                break;
            case 'q': // To exit out from the word search program.
                System.out.printf("Exiting Word Search..%n");
                x = 1;
                break;
            default: // Whenever the user inputs an invalid command.
                System.out.printf("Please enter a valid command.%n");
                x = 0;
                break;
       }
        return x;
    } // end selection

    public static void create() { // To prompt the user to create the words/answers for the Word Search
      Scanner scan = new Scanner(System.in); // NOTE: (Scanner isn't closed as when it does close the entire program breaks.)
      wordCount = 0; 
      words = new ArrayList<String>(); 
      System.out.println("Type each word on its own line, don't exceed 8 characters." );
      System.out.println("Hit 'Enter' when you're finished with your word!" );
      System.out.println("It will automatically end when your list of words contains 8 words or if you type 'end'.");
      while (wordCount <= 8) {
          try {
            input = scan.next().toLowerCase(); 
            if(input.equals("end")) { // Stops the prompt and creates using the words given when typed 'end'.
              break;
            }
            else if(input.length()<=8) { // Allows any words typed thats 8 letters or lower to be added.
              wordCount++;
              words.add(input);
            }
            else { // Stops any words typed longer than 8 letters.
                System.out.println("The word must be 8 characters long!");
            }
      
          } catch (Exception e) {
              System.out.println("Invalid input, try again.");
            }
       }
    }// end create

      public static void measurer() { // To then use the words prompted to measure how large the grid should be.
        int i;
        for(i =0; i<words.size(); i++){
          if(words.get(i).length() > width){
            width = words.get(i).length();
         }
       }
        width = width *2; 
        length = width + (width/3);
        search = new char [width][length];
        answer = new char [width][length]; 
    } // end measurer

      public static void clear() { // To clear all data that was stored in the previous iteration.
        width = 0;
        length = 0;
        wordCount = 0;
        words = new ArrayList<>();
        positions = null;
        search = null;
        answer = null;
    } // end clear

      public static void wordfiller(){ // To fill the words prompted from create into the grid.
        int mid;
        int midl;
        int wordlong;
        int x, y ;
        positions = new int[wordCount]; 
      
        for(int i =0; i < wordCount; i++){
          Random random = new Random();
          int randomNumber = random.nextInt(3); // To randomly decide whether its horizontal, vertical, or diagonal.
          int flip = random.nextInt(2);
          switch (randomNumber) {

          case 0:  // Horizontal arrangement.

            wordlong = words.get(i).length();
            mid = width - wordlong; 
            y = randomRange(0, mid);
            x = randomRange(0, length-5); 
            if(search(positions,  x) && answer(positions, x) ){
              x++; 
            }
            positions[i] = x; 
            if  (flip == 0) { // Horizontal.

              for(int j =0; j < wordlong; j++){
                if (search[x][y] == 0 && answer[x][y] == 0) { 
                  search[x][y] = words.get(i).charAt(j);
                  answer[x][y] = words.get(i).charAt(j);
                }
                y++;
           }
         } 
            if  (flip == 1){ // Flipped Horizontal.

              for(int j = wordlong - 1; j >= 0; j--){
                if (search[x][y] == 0 && answer[x][y] == 0) { 
                  search[x][y] = words.get(i).charAt(j);
                  answer[x][y] = words.get(i).charAt(j);
                }
                y++;
           }
         } 
          break;

          case 1:  // Vertical arrangement.

            wordlong = words.get(i).length();
            mid = width - wordlong; 
            x = randomRange(0, mid); 
            y = randomRange(0, length-5); 
            if(search(positions,  y) && answer(positions, y) ){
              y++; 
            }
            positions[i] = y; 
            if  (flip == 0){ // Vertical.

              for(int j =0; j < wordlong; j++){
                if (search[x][y] == 0 && answer[x][y] == 0) { 
                  search[x][y] = words.get(i).charAt(j);
                  answer[x][y] = words.get(i).charAt(j);
                }
                x++;
           }
         } 
            if  (flip == 1){ // Flipped Vertical.

              for(int j = wordlong - 1; j >= 0; j--){
                if (search[x][y] == 0 && answer[x][y] == 0) { 
                  search[x][y] = words.get(i).charAt(j);
                  answer[x][y] = words.get(i).charAt(j);
                }
                x++;
         }
       } 
          break;

          case 2:  // Diagonal arrangement.

            wordlong = words.get(i).length();
            mid = width - wordlong;
            midl = length - wordlong;
            x = randomRange(0,mid);
            y = randomRange(0, midl);
            if  (flip == 0){ // Diagonal.

              for(int j =0; j < wordlong; j++){
                if (search[x][y] == 0 && answer[x][y] == 0) { 
                  search[x][y] = words.get(i).charAt(j);
                  answer[x][y] = words.get(i).charAt(j);
                }
                x++;
                y++;
         }
       } 
            if  (flip == 1){ // Flipped Diagonal.

              for(int j = wordlong - 1; j >= 0; j--){
                if (search[x][y] == 0 && answer[x][y] == 0) { 
                  search[x][y] = words.get(i).charAt(j);
                  answer[x][y] = words.get(i).charAt(j);
                }
                x++;
                y--;
         }
       } 
            break;
            
            default: // For when miraculously random breaks.
            break;
         }
       }
    } // end wordfiller

    public static void emptyslots(int fillopt) { // To fill in the empty slots left by wordfiller.
        switch (fillopt) {                      // Depends on the prompt, either random letters or '.' for solution.
        case 1: // Random letters               // Uses the integer fillopt to decide which one to use.
          
           for (int i =0; i <length; i ++){ 
            for (int j =0; j < width; j++){
             if(search[j][i] == 0){
              search[j][i] = (char) randomRange(97, 122);
           }
         } 
       }
          break;

        case 2: // '.'

       for (int i =0; i <length; i ++){
     for (int j =0; j< width; j++){
       if(answer[j][i] == 0){
         answer[j][i] = '.';
           }
         }
       }
     break;

     default: // For when miraculously fillopt breaks.
     break;
        }
      } // end emptyslots

      public static boolean search(int [ ] numbers, int key) { // To take the words from create alongside the random letters
        for (int index = 0; index < numbers.length; index++)    {
          if ( numbers[index] == key ){
            return true; 
          }
        }
        return false;
      } // end search
      public static boolean answer(int [ ] numbers, int key) { // To take the words from create alongside '.'
        for (int index = 0; index < numbers.length; index++)    {
          if ( numbers[index] == key ){
            return true; 
          }
        }
        return false;
      } // end answer
      
      public static int randomRange(int low, int high){ // Bounding range values
        Random random = new Random();
        return random.nextInt(high-low+1) + low;
      } //end randomrange

      public static void printer(int fillopt){ // To print the entire word search for the user
        switch (fillopt) {                   // Uses fillopt to decide which one to print, either the search or answer.
          case 1: // Word Search printing.
          for(int i =0; i <width; i++){
          for(int j =0; j < length; j ++){
            System.out.print(search[i][j]+ " ");
          }
          System.out.println(); 
        }
        break;
      
        case 2: // Word Search Solution printing.
          for(int i =0; i <width; i++){
          for(int j =0; j < length; j ++){
            System.out.print(answer[i][j]+ " ");
          }
          System.out.println(); 
        }
        break;

        default: // For when fillopt miraculously breaks.
        break;
        }
        System.out.println();
        System.out.println("Finished");
        System.out.println( width + " Lines deep");
        System.out.println( length +" lines wide");
        System.out.println();
        System.out.println("Find these words");
        for(int i =0; i<wordCount; i++){
          System.out.println(words.get(i));
        }                    
      } // end printer
    } // end class