package spell;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Character.valueOf;

public class SpellCorrector implements ISpellCorrector {
    private final Trie trie;
    private final TreeSet <String> words;
    private final TreeSet <String> words2;

    public SpellCorrector() {
        trie = new Trie();
        words = new TreeSet<>();
        words2 = new TreeSet<>();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+"); //technically could do everything after the | since we don't have comments in the file

        while (scanner.hasNext()) {
            trie.add(scanner.next());
        }

//        Iterator<String> iterator = words.iterator();
//        while (iterator.hasNext()) {
//            suggestSimilarWord(iterator.next());
//        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        String word = null;
        if (trie.find(inputWord) != null) { //if the input string is found in the Trie (meaning it is spelled correctly)
            return inputWord;
        }
        else { //not in the trie
            deletion(inputWord, words);
            transposition(inputWord, words);
            alteration(inputWord, words);
            insertion(inputWord, words);

            word = suggestSimilarWordHelper(words);
            if (word == null) {
                for (String s : words) {
                    deletion(s, words2);
                    transposition(s, words2);
                    alteration(s, words2);
                    insertion(s, words2);
                }
                word = suggestSimilarWordHelper(words2);
            }
        }

        return word;
    }

    private String suggestSimilarWordHelper(Set<String> words) {
        String word = null;
        int max = 0;

        for (String s : words) {
            if (trie.find2(s) != null) {
                Node node = (Node) trie.find(s);
                if (node.value > max) {
                    max = node.getValue();
                    word = s;
                }
            }
        }
        return word;
    }

    private void deletion(String inputWord, Set<String> words) {
        for (int i = 0; i < inputWord.length(); i++) {
            for (int j = 0; j < inputWord.length(); j++) {
                StringBuilder stringbuilder = new StringBuilder(inputWord);
                if (j != i) {
                    stringbuilder.deleteCharAt(j);
                    String word = stringbuilder.toString();
                    words.add(word);
                }
            }
            StringBuilder stringbuilder = new StringBuilder(inputWord);
        }
    }

    private void transposition(String inputWord, Set<String> words) {
        for (int i = 0; i < inputWord.length(); i++) {
            for (int j = 0; j < inputWord.length(); j++) {
                if (i - j == -1) {
                    StringBuilder stringbuilder = new StringBuilder(inputWord);
                    char iChar = stringbuilder.charAt(i);
                    char jChar = stringbuilder.charAt(j);
                    stringbuilder.setCharAt(i, jChar);
                    stringbuilder.setCharAt(j, iChar);
                    String word = stringbuilder.toString();
                    words.add(word);
                }
            }
            StringBuilder stringbuilder = new StringBuilder(inputWord);
        }

//        char[] chars = new char[inputWord.length()];
//        for (int i = 0; i < inputWord.length(); i++) {
//            inputWord.getChars(0, i, chars,0);
//            for (int j = 0; j < inputWord.length(); j++) {
//                if (i - j == -1) {
////                    int alpha = j + 97;
//                    char temp = chars[i];
//                    chars[i] = chars[j];
//                    chars[j] = temp;
//
//                    inputWord.getChars(i, inputWord.length(), chars, i);
//                    words.add(new String(chars));
//                }
//            }
//        }
    }

    private void alteration(String inputWord, Set<String> words) {
        for (int i = 0; i < inputWord.length(); i++) {
            for (int j = 0; j < 26; j++) {
                int alpha = (char) j + 97;
                StringBuilder stringbuilder = new StringBuilder(inputWord);
                stringbuilder.setCharAt(i, (char) alpha);
                String word = stringbuilder.toString();
                words.add(word);
            }
            StringBuilder stringbuilder = new StringBuilder(inputWord);
        }

//        char[] chars = new char[inputWord.length()];
//        for (int i = 0; i < inputWord.length(); i++) {
//            inputWord.getChars(0, i, chars,0);
//            for (int j = 0; j < 26; j++) {
//                int alpha = j + 97;
//                chars[i] = (char) alpha;
//
//                inputWord.getChars(i, inputWord.length(), chars, 0);
//                words.add(String.valueOf(chars));
//            }
//        }
    }

    private void insertion(String inputWord, Set<String> words) {
        char[] chars = new char[inputWord.length() + 1];

        for (int i = 0; i < inputWord.length() + 1; i++) {
            inputWord.getChars(0, i, chars,0);
            for (int j = 0; j < 26; j++) {
                int alpha = j + 97;
                chars[i] = (char) alpha;
                inputWord.getChars(i, inputWord.length(), chars, i + 1);
                words.add(new String(chars));
            }
        }
    }
}
