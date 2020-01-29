import java.util.*;
import java.io.*;
public class GenerateTest {
    static final String quotedir = "quotes";
    static final File texts = new File(quotedir + "/texts.txt");
    static final File authors = new File(quotedir + "/authors.txt");
    public static void makeTest() throws Exception {
        /*
         * TEST FORMAT:
         * 2-4 aristocrats, 75% chance for hint, 25% chance for errors
         * 2-4 patristocrats, 20% chance for no hint
         * 0-1 caesar cipher
         * 2-3 affine cipher, 50% encrypting, 50% decrypting *need to add crib attack after 
         * 0-2 vignere *need to add crib attack after
         * 1-2 baconian
         * 1-2 xenocrypt
         * 1-3 hill cipher, 50% encrypting, 50% decrypting *need to add crib attack after
         * 1-2 pollux *need to add crib attack after
         * 1-2 morbit *need to add crib attack after
         * 1-2 RSA
         */
        
        int numaristocrats = 2+(int)(Math.random()*3);
        int numpatristocrats = 2+(int)(Math.random()*3);
        int numcaesar = (int)(Math.random()*2);
        int numaffine = 2+(int)(Math.random()*2);
        int numvignere = (int)(Math.random()*3);
        int numbaconian = 1+(int)(Math.random()*2);
        int numxenocrypt = 1+(int)(Math.random()*2);
        int numhill = 1+(int)(Math.random()*3);
        int numpollux = 1+(int)(Math.random()*2);
        int nummorbit = 1+(int)(Math.random()*2);
        int numRSA = 1+(int)(Math.random()*2);
        
        int numquotes = quotesLength();
        
        for(int i=0;i<numaristocrats;i++) {
            String[] quote = getQuote((int)(Math.random()*numquotes));
            String plaintext = quote[0];
            String author = quote[1];
            boolean hint = false;
            if (Math.random()<.75) {
                hint = true;
            }
            boolean errors = false;
            if (Math.random()<.25) {
                errors = true;
            }
            String[] aristocrat = GenerateProblem.aristocrat(plaintext, hint, errors);
            String statement = "Solve this aristocrat written by " + author + ".";
            if (hint) {
                statement = statement + " HINT: " + aristocrat[2] + ".";
            }
            if (errors) {
                statement = statement + " The plaintext contains errors";
            }
            
            System.out.println("\n\n" + statement + "\n");
            System.out.println(aristocrat[0]);
            System.out.println(aristocrat[1]);
            
            
        }
        
        
        
        
        
        
        
    }
    public static int quotesLength() throws Exception {
        BufferedReader r = new BufferedReader(new FileReader(texts));
        int sum=0;
        while(r.readLine() != null) {
            sum++;
        }
        r.close();
        return sum;
    }
    public static String[] getQuote(int index) throws Exception {
        String text = "";
        String author = "";
        BufferedReader tr = new BufferedReader(new FileReader(texts));
        BufferedReader ar = new BufferedReader(new FileReader(authors));
        for(int i=0;i<index-1;i++) {
            tr.readLine();
            ar.readLine();
        }
        text = tr.readLine().toUpperCase();
        author = ar.readLine();
        return new String[]{text,author};
    }
}