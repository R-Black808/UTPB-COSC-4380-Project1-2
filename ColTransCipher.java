
import java.util.List;
import java.util.ArrayList;

public class ColTransCipher extends Cipher {
    private String key;
    private boolean padding;
    private boolean debug;

    public ColTransCipher(String key, String[] names, boolean ascending, boolean strict) {
        this.key = key;
        this.padding = false;
        this.debug = false;
    }

    public void setKey(String key, boolean ascending) {
        this.key = key;
    }

    public String encrypt(String plaintext) {
        int cols = key.length();
        int rows = (int) Math.ceil((double) plaintext.length() / cols);
        char[][] grid = new char[rows][cols];

        int idx = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (idx < plaintext.length()) {
                    grid[r][c] = plaintext.charAt(idx++);
                } else {
                    grid[r][c] = 'X'; // pad
                }
            }
        }

        StringBuilder ciphertext = new StringBuilder();
        List<Integer> order = getColumnOrder(key);
        for (int c : order) {
            for (int r = 0; r < rows; r++) {
                ciphertext.append(grid[r][c]);
            }
        }
        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        int cols = key.length();
        int rows = (int) Math.ceil((double) ciphertext.length() / cols);
        char[][] grid = new char[rows][cols];

        List<Integer> order = getColumnOrder(key);
        int idx = 0;
        for (int c : order) {
            for (int r = 0; r < rows; r++) {
                if (idx < ciphertext.length()) {
                    grid[r][c] = ciphertext.charAt(idx++);
                }
            }
        }

        StringBuilder plaintext = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                plaintext.append(grid[r][c]);
            }
        }
        return plaintext.toString();
    }

    private List<Integer> getColumnOrder(String key) {
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < key.length(); i++) order.add(i);
        order.sort((a, b) -> Character.compare(key.charAt(a), key.charAt(b)));
        return order;
    }

    public String crack(String ciphertext) {
        int maxKeyLength = 8;
        double bestScore = Double.NEGATIVE_INFINITY;
        String bestKey = "";
        String bestPlaintext = "";

        for (int keyLen = 2; keyLen <= maxKeyLength; keyLen++) {
            List<List<Integer>> permutations = generatePermutations(keyLen);
            for (List<Integer> perm : permutations) {
                StringBuilder keyBuilder = new StringBuilder();
                for (int digit : perm) keyBuilder.append(digit);
                String testKey = keyBuilder.toString();

                setKey(testKey, true);
                String decrypted = decrypt(ciphertext);

                double score = Bigrams.score(decrypted) + Dictionary.wordCount(decrypted);
                if (score > bestScore) {
                    bestScore = score;
                    bestKey = testKey;
                    bestPlaintext = decrypted;
                }
            }
        }

        System.out.println("Best key found: " + bestKey);
        return bestPlaintext;
    }

    private List<List<Integer>> generatePermutations(int n) {
        List<List<Integer>> result = new ArrayList<>();
        permuteHelper(n, new ArrayList<>(), new boolean[n], result);
        return result;
    }

    private void permuteHelper(int n, List<Integer> current, boolean[] used, List<List<Integer>> result) {
        if (current.size() == n) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                used[i] = true;
                current.add(i);
                permuteHelper(n, current, used, result);
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
    }
}
