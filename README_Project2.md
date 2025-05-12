
# Project 2 - Columnar Transposition Cipher Cracker

## 🧩 Overview

This project implements a **columnar transposition cipher** with encryption, decryption, and cracking functionality using frequency analysis and dictionary scoring.

The core class `ColTransCipher.java` handles:
- Encryption using a columnar transposition key (e.g., "34152")
- Decryption using the same key
- Cracking using permutations of key orders, scored by:
  - English bigram frequency (`bigrams.txt`)
  - English word matches (`dict.txt`)

---

## 📂 File Structure

```
ColTransCipher.java       # Cipher logic and crack() method
Bigrams.java              # Loads bigram frequencies and scores strings
bigrams.txt               # File with normalized bigram log-probabilities
Dictionary.java           # Counts English words in a string
dict.txt                  # Dictionary word list
CrackTest.java            # Driver program to test encryption and cracking
```

---

## ✅ How to Run

1. **Open a terminal** in the extracted folder.
2. Compile:
   ```bash
   javac ColTransCipher.java Bigrams.java Dictionary.java CrackTest.java
   ```
3. Run:
   ```bash
   java CrackTest
   ```

---

## 🧪 Test Logic

- Encrypts a known plaintext:  
  `"thequickbrownfoxjumpedoverthelazydogs"`
- Using key `"34152"`  
- Then attempts to crack the ciphertext using permutations of column keys.
- Best scoring result (based on bigram frequency + dictionary hits) is printed.

---



## ✅ Final Output

```text
Original plaintext: thequickbrownfoxjumpedoverthelazydogs
Ciphertext: eknuohyXuropeloXtioxeraghcwjdtzsqbfmvedX
Best key found: 23041
Cracked plaintext: thequickbrownfoxjumpedoverthelazydogs
Crack successful? true
```

---

## 👤 Author

Roy Lujan — COSC Cryptography Project 2  
 OpenAI guidance link for padding issue https://chatgpt.com/share/682177b5-16f0-8002-a1b4-caf440d1ba88
