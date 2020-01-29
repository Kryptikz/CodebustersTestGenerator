import java.util.*;
public class GenerateProblem {
    static final char[] normalAlpha = new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    static final double errorChance = .06;
    public static String[] aristocrat(String plaintext, boolean hint, boolean error) {
        //return format: String[]{ciphertext(String), plaintext(String), hint(String), errors(boolean)}
        char[] alphabet = genAlpha(true);
        String ciphertext = encryptMono(plaintext, alphabet);
        String hintstr = "";
        if (hint) {
            hintstr = generateAristocratHint(plaintext);
        }
        String[] arist = new String[]{ciphertext,plaintext,hintstr,""+error};
        return arist; 
    }

    public static String generateAristocratHint(String plaintext) {
        /*
         * POSSIBLE HINTS:
         * "the first/second/third/last word is 'x'" ID 0
         * "the word 'x' shows up 2 times" ID 1
         * "'a' and 'b' are the two most common letters" ID 2
         */
        String[] words = plaintext.split(" ");
        int freqhigh = 1;
        String mostfreq = null;
        for(String w : words) {
            int wf = freq(words,w);
            if (wf > freqhigh) {
                freqhigh = wf;
                mostfreq = w;
            }
        }
        int hintid = 0;
        if (mostfreq != null) {
            int choice = (int)(Math.random()*3);
            switch(choice) {
                case(0): hintid = 0;
                case(1): hintid = 1;
                case(2): hintid = 2;
            }
        } else {
            int choice = (int)(Math.random()*2);
            switch(choice) {
                case(0): hintid = 0;
                case(1): hintid = 2;
            }
        }
        String hint = "";
        if (hintid == 0) {
            int variant = (int)(Math.random()*4);
            switch(variant) {
                case(0): hint = "The first word is " + words[0];
                case(1): hint = "The second word is " + words[1];
                case(2): hint = "The third word is " + words[2];
                case(3): hint = "The last word is " + words[words.length-1];
            }
        } else if (hintid == 1) {
            hint = "The word " + mostfreq + " appears " + getNumWord(freqhigh);
        } else if (hintid == 2) {
            hint = "The two most common letters are " + mostCommonTwo(plaintext)[0] + " and " + mostCommonTwo(plaintext)[1];
        }
        return hint;
    }

    public static void test() {
        char c = 'A';
        System.out.println((int)c);
    }

    private static char[] mostCommonTwo(String plaintext) {
        int[] freqs = new int[26];
        for(char c : plaintext.toCharArray()) {
            if (c != ' ') {
                freqs[(int)(c)-65]++;
            }
        }
        char[] ret = new char[2];
        for(int ci=0;ci<2;ci++) {
            int greatest = 0;
            int gin = 0;
            for(int i=0;i<freqs.length;i++) {
                if (freqs[i] > greatest) {
                    greatest = freqs[i];
                    gin = i;
                }
            }
            freqs[gin] = -1;
            ret[ci] = normalAlpha[gin];
        }
        return ret;
    }

    private static String getNumWord(int num) {
        HashMap<Integer,String> map = new HashMap<Integer,String>();
        map.put(1, "once");
        map.put(2, "twice");
        map.put(3, "three times");
        map.put(4, "four times");
        map.put(5, "five times");
        map.put(6, "six times");
        map.put(7, "seven times");
        map.put(8, "eight times");
        map.put(9, "nine times");
        map.put(10, "ten times");
        map.put(11, "eleven times");
        map.put(12, "twelve times");
        map.put(13, "thirteen times");
        map.put(14, "fourteen times");
        map.put(15, "fifteen times");
        map.put(16, "sixteen times");
        map.put(17, "seventeen times");
        map.put(18, "eighteen times");
        map.put(19, "nineteen times");
        map.put(20, "twenty times");
        if (num > 20) {
            return "more than 20 times";
        } else {
            return map.get(num);
        }
    }

    private static int freq(String[] arr, String text) {
        int sum = 0;
        for(String s : arr) {
            if (s.equals(text)) {
                sum++;
            }
        }
        return sum;
    }

    public static String addErrors(String plaintext) {
        String retstr = "";
        for(char c : plaintext.toCharArray()) {
            if (Math.random() < errorChance) {
                retstr = retstr + normalAlpha[(int)(Math.random()*26)];
            } else {
                retstr = retstr + c;
            }
        }
        return retstr;
    }

    public static String encryptMono(String plaintext, char[] key) {
        String ciphertext = plaintext;
        for(int i=0;i<26;i++) {
            ciphertext = replace(ciphertext, normalAlpha[i], key[i]);
        }
        return ciphertext;
    }

    public static String replace(String original, char toReplace, char replaceWith) {
        String retstr = "";
        for(char c : original.toCharArray()) {
            if (c == toReplace) {
                retstr = retstr + replaceWith;
            } else {
                retstr = retstr + c;
            }
        }
        return retstr;
    }

    public static char[] genAlpha(boolean english) {
        char[] normal = new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        ArrayList<Double> arrvals = new ArrayList<Double>();
        char[] alpha = new char[26];
        for(int i=0;i<26;i++) {
            arrvals.add(Math.random());
        }
        int charin = 0;
        while(charin<26) {
            double lownum = 2;
            int lowin = 0;
            for(int i=0;i<arrvals.size();i++) {
                if (arrvals.get(i) < lownum) {
                    lownum = arrvals.get(i);
                    lowin = i;
                }
            }
            arrvals.set(lowin,1000.0);
            alpha[charin] = normal[lowin];
            charin++;
        }
        boolean complete = false;
        while(!complete) {
            boolean valid = true;
            for(int i=0;i<26;i++) {
                if (normal[i] == alpha[i]) {
                    valid=false;
                    break;
                }
            }
            if (valid) {
                complete = true;
                break;
            } else {
                for(int i=0;i<26;i++) {
                    if(normal[i] == alpha[i]) {
                        int newin = i+1;
                        if (newin>=26) {
                            newin=0;
                        }
                        char temp = alpha[newin];
                        alpha[newin] = alpha[i];
                        alpha[i] = temp;
                    }
                }
            }
        }
        return alpha;
    }
}