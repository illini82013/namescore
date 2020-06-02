package org.example.NameCalculator.NameCalculatorAlgorithm;


import org.javatuples.Pair;

import java.util.Arrays;

/**
 * <h1>Simple algorithm for calculating name scores</h1>
 */
public class SimpleNameScoreAlgorithm implements NameScoreAlgorithm {
    /**
     * Calculates a name score for a given name
     *
     * @param nameTuple Tuple where the first value is the name's position
     *                  in the names' array.  The second value is the name.
     * @return the name's score
     */
    public long calculateNameScore(Pair<Integer, String> nameTuple) {
        long nameScore = 0;
        int namePosition = nameTuple.getValue0();
        String rawName = nameTuple.getValue1();

        // Remove all non-alphabet characters
        String sanitizedName = rawName.replaceAll("[^a-zA-Z]", "");
        char[] characters = sanitizedName.toLowerCase().toCharArray();

        // For each character look up the character's score and add it to the name score
        for (char c : characters) {
            // Determines where in the alphabet a letter is. I.E. B = 2, D = 4
            nameScore += (int) c & 31;
        }
        return namePosition * nameScore;
    }

    /**
     * Calculates the total name score for an array of names
     *
     * @param namesInLine Array of names for calculating scores
     * @return the total score for all of the names in the array
     */
    @Override
    public long calculateScore(String[] namesInLine) {
        long totalLineScore = 0;
        int namePosition = 1;

        Arrays.sort(namesInLine);
        // Iterate over each name in the sorted array, calculate each name's score in list
        // and sum the result for all names in the array
        for (String name : namesInLine) {
            long nameScore = this.calculateNameScore(new Pair<Integer, String>(namePosition, name));
            totalLineScore += nameScore;
            namePosition++;
        }
        return totalLineScore;
    }
}
