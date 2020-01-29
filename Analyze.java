import java.util.*;
import java.io.*;
public class Analyze {
    //the purpose of this class is to perform an analyis of quotes, to generate more accurate frequency tables, and to better understand quote analysis
    static final char[] normalAlpha = new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    static final String quotedir = "quotes";
    static final File texts = new File(quotedir + "/texts.txt");
    static final File authors = new File(quotedir + "/authors.txt");
    
    public static void main(String[] args) throws Exception {
        int[] totals = new int[26];
        int len = quotesLength();
        for(int i=0;i<len;i++) {
            int[] table = getFrequencyTable(getQuote(i)[0]);
            for(int n=0;n<26;n++) {
                totals[n]+=table[n];
            }
        }
        long sum = 0;
        for(int n : totals) {
            sum+=n;
        }
        for(int i=0;i<26;i++) {
            System.out.println(normalAlpha[i] + ": " + ((double)((int)(((double)totals[i]/(double)sum)*10000))/100.0));
        }
        int prevmax = Integer.MAX_VALUE;
        int in=0;
        char[] sorted = new char[26];
        while(in<26) {
            int max=0;
            char maxc = '0';
            for(int i=0;i<totals.length;i++) {
                if (totals[i] > max && totals[i]<prevmax) {
                    max = totals[i];
                    maxc = normalAlpha[i];
                }
                
            }
            prevmax = max;
            sorted[in] = maxc;
            in++;
        }
        System.out.print('\n');
        for(char c : sorted) {
            System.out.print(c);
        }
        System.out.print('\n');
        
        
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