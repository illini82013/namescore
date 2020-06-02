package org.example.NameCalculator.NameCalculatorAlgorithm;

/**
 * Algorithm for calculating a name score
 *
 */
public interface NameScoreAlgorithm {
    /**
     * Algorithm for calculating name scores.  The algorithm works on an
     * array of names at a time.
     * @param namesInLine Array of names for calculating scores
     * @return the total score for all of the names in the array
     */
    long calculateScore(String[] namesInLine);
}
