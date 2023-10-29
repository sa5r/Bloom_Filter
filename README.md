# Bloom Filter Implementation

[What is Bloom Filter?](https://en.wikipedia.org/wiki/Bloom_filter)

Implementation of a Bloom Filter and then analyzing its performance with respect to its false positive rate.

The hash function used if the [sfold](https://research.cs.vt.edu/AVresearch/hashing/strings.php) method

## Performance Study
In addition to implementing the bloom filter, you will also conduct a performance study on the bloom filter with respect to the false positive rate. For this, you will instantiate bloom filters of sizes 24, 28, 212, 216 and 220 bits. For each bloom filter size, you will experiment with 4 different number of hash functions (for example you can use 2, 4, 8 and 16). You will insert 100, 250, 500, 750, 1000, 2500, 5000, 7500, 10,000 and 25,000 random words into the bloom filter and test with random strings that have not been inserted (you should run the test until the false positive rate has stabilized. You will then plot the false positive rate for each bloom filter size (X axis will be the number of words inserted into the filter, Y axis will be the FP rate). There will be four curves in each graph corresponding to the number of hash function. Choose linear or logarithmic scales for the axes as appropriate. You can use the strings in one or more of the word lists available at https://www.keithv.com/software/wlist/ for this performance study (or you can use the word list of your choice provided it has enough words).

## Running Code
To run the code compile it with Java 11.

Execute the Java program with the following command line parameters

```
java Bloom [Bloom filter size] [Hash functions] [Inserted words] wlist_match1.txt [words to test]
```

Command parameters:

- Bloom filter size
- Number of hash functions
- Number of inserted words
