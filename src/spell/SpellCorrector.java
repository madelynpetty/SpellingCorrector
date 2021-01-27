package spell;

import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

public class SpellCorrector implements ISpellCorrector {
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        Scanner scanner = new Scanner(dictionaryFileName);
        scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+"); //technically could do everything after the | since we don't have comments in the file
        while (scanner.hasNext()) {
            String str = scanner.next();
        }

        TreeSet <Node> words;

    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        return null;
    }
}
