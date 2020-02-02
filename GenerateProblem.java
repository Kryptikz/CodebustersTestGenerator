import java.util.*;
import java.io.*;

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
    
    public static String[] patristocrat(String plaintext, boolean hint) {
        //return format: String[]{ciphertext(String), plaintext(String), hint(String)}
        String[] arist = aristocrat(plaintext, hint, false);
        String ciphertext = arist[0].replaceAll("\\s+", "");
        String newcipher = "";
        String temp = "";
        for(char c : ciphertext.toCharArray()) {
            if (c >=65 && c <= 90) {
                temp = temp + c;
            }
        }
        ciphertext = temp;
        for(int i=0;i<ciphertext.length();i++) {
            newcipher = newcipher + ciphertext.toCharArray()[i];
            if (i>0 && i%5 == 0) {
                newcipher = newcipher + " ";
            }
        }
        return new String[]{newcipher,arist[1],arist[2]};
    }
    
    public static String[] caesar(String plaintext) {
        //return format: String[]{ciphertext(String), plaintext(String)}
        int shift = (int)(Math.random()*25)+1;
        String ciphertext = shift(plaintext, shift); 
        String[] caes = new String[]{ciphertext, plaintext};
        return caes;
    }
    
    public static String[] affine(String plaintext) {
        //return format: String[]{ciphertext(String), plaintext(String), a(int), b(int)}
        int a = (int)(Math.random()*26);
        while (a % 2 == 0 || a % 13 == 0) {
            a = (int)(Math.random()*26);
        }
        
        int b = (int)(Math.random()*25) + 1;
        
        char[] orig = plaintext.toCharArray();
        char[] nw = new char[orig.length];
        
        for (int i = 0 ; i < orig.length ; i++) {
            if ((orig[i] - 'A') >= 0 && (orig[i] - 'A') <= 25) {
                char num = (char) (((orig[i] - 'A') * a + b) % 26 + 'A');
                
                nw[i] = num;
            }
            else {
                nw[i] = orig[i];
            }
        }
        
        String ct = new String(nw);
        
        return new String[]{ct, plaintext, Integer.toString(a), Integer.toString(b)};
    }
    
    public static String[] hillMatrix(String plaintext) {
        TreeMap<Integer, Integer> dm = new TreeMap<Integer,Integer>();
        dm.put(1, 1);
        dm.put(3, 9);
        dm.put(5, 21);
        dm.put(7, 15);
        dm.put(9, 3);
        dm.put(11, 19);
        dm.put(15, 7);
        dm.put(17, 23);
        dm.put(19, 11);
        dm.put(21, 5);
        dm.put(23, 17);
        dm.put(25, 25);
        //Return format: String[]{key, decryption key}
        String[] valid = {"ABLE","ABLY","ADZE","ALTO","ARTS","AXLE","BASH","BAWD","BEAR","BEEN","BEEP","BEER","BIND","BIRD","BITT","BORN","BOWL","BRAT","BULB","BUSH","DAFT","DASH","DAUB","DEAD","DEAL","DEAR","DELL","DIED","DIET","DIRT","DISH","DOTH","DOWN","DUFF","DULL","DYED","DYER","ELLS","EXPO","FACT","FALL","FAUX","FEAR","FIND","FISH","FIZZ","FLEX","FLOP","FOIL","FOOD","FOOT","FUZZ","HAIR","HARP","HASH","HEAR","HEIR","HIGH","HOAR","HOER","HOOF","HOWL","HUED","IDLY","IFFY","JAIL","JINX","JOLT","JUMP","JUST","LAID","LAIR","LAND","LEAD","LEAR","LEWD","LIMN","LIMP","LISP","LOLL","LOON","LOOP","LORD","LULL","NEXT","ODDS","ONLY","OPTS","PAID","PANT","PART","PAST","PEAL","PEER","PELF","PELT","PEND","PENT","PEON","PILL","PINT","PITH","PLED","PLOD","POET","POMP","POUT","PUFF","PULL","PUMP","RAFT","RAND","RANT","RAPT","RASH","RASP","REAP","REEL","REIN","RIFF","ROOF","RUST","TAIL","TAMP","TARP","TART","TEXT","TIED","TINT","TOAD","TOED","TOLL","TURN","TWIT","VAMP","VEAR","VIED"};
        String key = valid[(int) (Math.random() * valid.length)];
        char[] temp = key.toCharArray();
        int[] vals = new int[4];
        for (int i = 0 ; i < 4 ; i++) {
            vals[i] = (temp[i] - 'A');
        }
        int[] nv = new int[4];
        nv[0] = vals[2];
        nv[2] = vals[0];
        nv[1] = (26 - vals[1]) % 26;
        nv[3] = (26 - vals[3]) % 26;
        int det = Math.abs((vals[3] - vals[0]) - (vals[2] - vals[1]));
        int dmnt = dm.get(det);
        int[] ns = new int[4];
        for (int i = 0 ; i < 4 ; i++) {
            ns[i] = (nv[i] * dmnt) % 26;
        }
        char[] tmp = new char[4];
        for (int i = 0 ; i < 4 ; i++) {
            tmp[i] = (char) (ns[i] + 'A');
        }
        String s = new String(tmp);
        return new String[]{key, s};
    }
    
    public static String shift(String text, int num) {
        char[] orig = text.toCharArray();
        char[] shifted = new char[orig.length];
        for(int i=0;i<shifted.length;i++) {
            if ((int)orig[i] >= 65 && (int)orig[i] <= 90) {
                int letter = (int)orig[i]-65;
                int newletter = (letter+num)%26;
                char newchar = (char)(newletter+65);
                shifted[i] = newchar;
            } else {
                shifted[i] = orig[i];
            }
        }
        return new String(shifted);
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
            hintid = choice;
        } else {
            int choice = (int)(Math.random()*2);
            hintid = choice*2;
        }
        String hint = "";
        if (hintid == 0) {
            int variant = (int)(Math.random()*4);
            if (variant == 0) {
                hint = "The first word is " + words[0];
            } else if (variant == 1) {
                hint = "The second word is " + words[1];
            } else if (variant == 2) {
                hint = "The third word is " + words[2];
            } else if (variant == 3) {
                hint = "The last word is " + words[words.length-1];
            }
        } else if (hintid == 1) {
            hint = "The word " + mostfreq + " appears " + getNumWord(freqhigh);
        } else if (hintid == 2) {
            hint = "The two most common letters are " + mostCommonTwo(plaintext)[0] + " and " + mostCommonTwo(plaintext)[1];
        }
        return hint;
    }

    private static char[] mostCommonTwo(String plaintext) {
        int[] freqs = new int[26];
        for(char c : plaintext.toCharArray()) {
            if ((int)c >= 65 && (int)c <= 90) {
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
            ciphertext = replace(ciphertext, normalAlpha[i], (char)((int)key[i]+32));
        }
        return ciphertext.toUpperCase();
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