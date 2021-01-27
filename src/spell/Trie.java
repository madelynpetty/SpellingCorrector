package spell;

import java.util.Arrays;
import java.util.Objects;

/** Implements the ITrie interface */
public class Trie implements ITrie {
    private final Node root;
    private int uniqueWords = 0;
    private int numNodes = 0;

    /** Initializes data structure */
    public Trie() {
        root = new Node('\0');
        numNodes++;
    }

    /**
     * Adds the specified word to the trie (if necessary) and increments the word's frequency count
     * @param word The word being added to the trie
     */
    @Override
    public void add(String word) {
        Node curr = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (curr.children[c - 'a'] == null) {
                curr.children[c - 'a'] = new Node(c);
                numNodes++;
            } //now we know the node has been created
            curr = curr.children[c - 'a'];
        }
        curr.incrementValue();
        if (curr.getValue() > 1) {
            return;
        }
        uniqueWords++;
    }

    /**
     * Searches the trie for the specified word
     * @param word The word being searched for
     * @return A reference to the trie node that represents the word, or null if the word is not in the trie
     */
    @Override
    public INode find(String word) {
        Node curr = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (curr.children[c - 'a'] == null) {
                return null;
            }
            else {
                curr = curr.children[c - 'a'];
            }
        }
        if (curr.getValue() != 0) {
            curr.incrementValue();
            return curr;
        }
        else {
            return null;
        }
    }

    /**
     * Returns the number of unique words in the trie
     * @return The number of unique words in the trie
     */
    @Override
    public int getWordCount() {
        return uniqueWords;
    }

    /**
     * Returns the number of nodes in the trie
     * @return The number of nodes in the trie
     */
    @Override
    public int getNodeCount() {
        return numNodes;
    }

    /**
     * The toString specification is as follows:
     * For each word, in alphabetical order:
     * <word>\n
     */
    public String toString() {
        StringBuilder output = new StringBuilder();
        StringBuilder currWord = new StringBuilder();
        toStringHelper(root, currWord, output);
        return output.toString();
    }

    private void toStringHelper(Node n, StringBuilder currWord, StringBuilder output) {
        if (n.getValue() > 0) {
            output.append(currWord.toString());
            output.append("\n");
        }
        for (int i = 0; i < n.getChildren().length; i++) {
            Node child =(Node) n.getChildren()[i];
            if (child != null) {
                char childLetter = (char)('a' + i);
                currWord.append(childLetter);
                toStringHelper(child, currWord, output);
                currWord.deleteCharAt(currWord.length() - 1);
            }
        }
    }

    @Override
    public int hashCode() {
        for (int i = 0; i < root.children.length; i++) {
            if (root.children[i] != null) {
                root.i = i;
            }
        }
        return root.i * numNodes * uniqueWords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Trie trie = (Trie) o;
//        return uniqueWords == trie.uniqueWords &&
//                numNodes == trie.numNodes &&
//                Objects.equals(root, trie.root);
        if ((trie.getWordCount() != getWordCount()) || (trie.root.getValue() != root.getValue()) || (trie.numNodes != numNodes)) {
            return false;
        }
        return equalsHelper(trie.root, root);
    }

    private boolean equalsHelper(Node node1, Node node2) {
        for (int i = 0; i < 26; i++) {
            if ((node1.children[i] == null && node2.children[i] != null) || (node1.children[i] != null && node2.children[i] == null)) {
                return false;
            }
            if (node1.children[i] == null && node2.children[i] == null) continue;
            if (node1.children[i].getValue() == node2.children[i].getValue()) {
                Node temp1 = node1.children[i];
                Node temp2 = node2.children[i];
                if (! equalsHelper(temp1, temp2)) {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        if (node1.getValue() == node2.getValue()) {
            return true;
        }
        else {
            return false;
        }
    }
}
