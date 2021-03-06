import java.util.*;
import java.io.*;

public class GenerateTest {
    static final char[] normalAlpha = new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    static final String quotedir = "quotes";
    static final File texts = new File(quotedir + "/texts.txt");
    static final File authors = new File(quotedir + "/authors.txt");
    public static void makeTest() throws Exception {
        /*
         * TEST FORMAT:
         * 2-4 aristocrats, 30% chance for hint, 25% chance for errors
         * 2-4 patristocrats, 20% chance for no hint
         * 0-1 caesar cipher
         * 2-3 affine cipher, 50% encrypting, 50% decrypting *need to add crib attack after 
         * 0-2 vignere *need to add crib attack after
         * 1-2 baconian
         * 1-2 xenocrypt
         * 1-3 hill cipher, 30% encrypting, 30% decrypting, 40% decryption matrix
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
        
        ArrayList<String> answers = new ArrayList<String>();
        
        ArrayList<String> testStrings = new ArrayList<String>();
        
        int t = 0;
        
        //numaristocrats = 20;
        for(int i=0;i<numaristocrats;i++) {
            t++;
            String[] quote = getQuote(85, 130);
            String plaintext = quote[0];
            String author = quote[1];
            boolean hint = false;
            if (Math.random()<.30) {
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
                statement = statement + " The plaintext contains errors.";
            }
            answers.add(aristocrat[1]);
            String testString = "";
            testString += "" + statement + "\n";
            testString += "\n" + aristocrat[0] + "\n\n";
            
            int[] freqtable = getFrequencyTable(aristocrat[0]);
            for(char c : normalAlpha) {
                testString += c + "  ";
            }
            testString += "\n";
            for(int num : freqtable) {
                if ((num+"").length()>1) {
                    testString += num + " ";
                } else {
                    testString += num + "  ";
                }
            }
            testStrings.add(testString);
        }
        
        for(int i=0;i<numpatristocrats;i++) {
            t++;
            String[] quote = getQuote(85, 130);
            String plaintext = quote[0];
            String author = quote[1];
            boolean hint = false;
            if (Math.random()<.30) {
                hint = true;
            }
            String[] patristocrat = GenerateProblem.patristocrat(plaintext, hint);
            String statement = "Solve this patristocrat written by " + author + ".";
            if (hint) {
                statement = statement + " HINT: " + patristocrat[2] + ".";
            }
            answers.add(patristocrat[1]);
            String testString = "";
            testString += "" + statement + "\n";
            testString += "\n" + patristocrat[0] + "\n";
            testString += "\n";
            int[] freqtable = getFrequencyTable(patristocrat[0]);
            for(char c : normalAlpha) {
                testString += c + "  ";
            }
            testString += "\n";
            for(int num : freqtable) {
                if ((num+"").length()>1) {
                    testString += num + " ";
                } else {
                    testString += num + "  ";
                }
            }
            testStrings.add(testString);
        }
        
        for (int i = 0 ; i < numaffine ; i++) {
            t++;
            String[] quote = getQuote(30, 55);
            String plaintext = quote[0];
            System.out.println(plaintext);
            String author = quote[1];
            String[] aff = GenerateProblem.affine(plaintext);
            String probText;
            if (Math.random() <= 0.5) {
                probText = "Encode this quote by " + author + " using the affine cipher with a = " + aff[2] + " and b = " + aff[3] + ":\n\n" + aff[1];
                answers.add(aff[0]);
            }
            else {
                probText = "Decode this quote by " + author + " using the affine cipher:\n\n" + aff[0];
                answers.add(aff[1]);
            }
            
            String testString = "";
            testString = probText;
            testStrings.add(testString);
        }
        
        for (int i = 0; i < numpollux; i++){
            t++;
            String[] quote = getQuote(100, 160);
            String plaintext = quote[0];
            String author = quote[1];
            String[] pollux = GenerateProblem.polluxAndMorbit(plaintext);
            String probText = "Decode this quote by " + author + " using the pollux and morbit cipher: \n"+pollux[2]+"\n\n"+pollux[0];
            answers.add(pollux[1]);
            String testString = "";
            testString = probText;
            testStrings.add(testString);
        }
        
        for (int i = 0 ; i < numhill ; i++) {
            double probb = Math.random();
            t++;
            if (probb <= 0.4) {
                String[] problem = GenerateProblem.hillMatrix();
                String probText = "Find the decryption key of a hill cipher with an encryption key of \"" + problem[0] + "\" .";
                testStrings.add(probText);
                answers.add(problem[1]);
            }
            else if (probb <= 0.7) {
                String[] quote = getQuote(20, 40);
                String pt = quote[0];
                String author = quote[1];
                String[] problem = GenerateProblem.hillEncrypt(pt);
                String probText = "Encode this quote by " + author + " using the hill cipher with a key of \"" + problem[1] + "\".";
                probText = probText + "\n" + pt;
                testStrings.add(probText);
                answers.add(problem[0]);
            }
            else {
                String[] quote = getQuote(20, 40);
                String pt = quote[0];
                String author = quote[1];
                String[] problem = GenerateProblem.hillEncrypt(pt);
                String probText = "Decode this quote by " + author + " using the hill cipher with a key of \"" + problem[1] + "\".";
                probText = probText + "\n" + problem[0];
                testStrings.add(probText);
                answers.add(pt);
            }
        }
        
        ArrayList<String[]> testPairs = new ArrayList<String[]>();
        for (int i = 0 ; i < testStrings.size() ; i++) {
            testPairs.add(new String[]{testStrings.get(i), answers.get(i)});
        }
        
        Collections.shuffle(testPairs);
        
        for (int i = 0 ; i < testPairs.size() ; i++) {
            System.out.println("\n\n" + (i + 1) + ": " + testPairs.get(i)[0]);
        }
        
        System.out.println("\n\n\n\n\n\nAnswers:");
        for(int i=0;i<answers.size();i++) {
            System.out.println((i+1) + ": " + testPairs.get(i)[1]);
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
    public static String[] getQuote(int low, int high) throws Exception {
        String text = "";
        String author = "";
        
        BufferedReader tr = new BufferedReader(new FileReader(texts));
        BufferedReader ar = new BufferedReader(new FileReader(authors));
        ArrayList<String> ts = new ArrayList<String>();
        ArrayList<String> as = new ArrayList<String>();
        for(int i=0;i<5000;i++) {
            ts.add(tr.readLine());
            as.add(ar.readLine());
        }
        while (text.length() < low || text.length() > high) {
            int mr = (int) (Math.random() * 5000);
            text = ts.get(mr).toUpperCase();
            author = as.get(mr);
        }
        return new String[]{text,author};
    }
    public static int[] getFrequencyTable(String ciphertext) {
        int[] ret = new int[26];
        for(int i=0;i<26;i++) {
            ret[i]+=freq(ciphertext.toCharArray(), normalAlpha[i]);
        }
        return ret;
    }
    private static int freq(char[] arr, char c) {
        int sum = 0;
        for(char sc : arr) {
            if (sc == c) {
                sum++;
            }
        }
        return sum;
    }
}