package org.example.NameCalculator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.example.NameCalculator.NameCalculatorAlgorithm.NameScoreAlgorithm;
import org.example.NameCalculator.NameCalculatorAlgorithm.SimpleNameScoreAlgorithm;
import org.javatuples.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Name calculator uses a name calculator algorithm to calculate
 * a name score
 */
public class NameCalculator {
    private final NameScoreAlgorithm nameScoreAlgorithm;
    private final SimpleNameScoreAlgorithm simpleNameScoreAlgorithm;

    /**
     * Instantiates a new instance of the name calculator, sets the algorithm.
     * If there is no match a Runtime Exception is raised
     *
     * @param algorithm name calculator algorithm
     */
    public NameCalculator(String algorithm) {
        this.simpleNameScoreAlgorithm = new SimpleNameScoreAlgorithm();
        switch (algorithm.toLowerCase()) {
            case "simple":
                this.nameScoreAlgorithm = new SimpleNameScoreAlgorithm();
                break;
            default:
                throw new RuntimeException(String.format("Requested algorithm: %s for calculating name score not found",
                        algorithm));
        }
    }

    /**
     * Calculates the name score for a file line-by-line using the algorithm
     * defined in the constructor.
     *
     * @param inputFilePath path to input file
     * @return The total name score for a file calculated line-by-line.
     * @throws IOException
     */
    public long calculateScoreFromFile(String inputFilePath) throws IOException {
        File inputFile = new File(inputFilePath);
        LineIterator lineIterator = FileUtils.lineIterator(inputFile, "UTF-8");
        long totalScore = 0;
        try {
            while (lineIterator.hasNext()) {
                String rawInputLine = lineIterator.nextLine();
                // Remove all double quotes from the line
                String inputLine = rawInputLine.replaceAll("\"", "");

                // Get an array of names by splitting the line on every comma
                String[] namesInLine = inputLine.split(",");

                // Calculate the name score for a line
                long lineScore = this.nameScoreAlgorithm.calculateScore(namesInLine);
                totalScore += lineScore;
            }
        } finally {
            LineIterator.closeQuietly(lineIterator);
        }
        return totalScore;
    }

    // Implementation of the name score algorithm using java streams
    public long calculateScoreFromFileStream(String inputFile) throws IOException {
        Stream<String> namesFile = Files.lines(Paths.get(inputFile));
        return
                namesFile
                        .map(x -> x.replaceAll("\"", ""))
                        .map(line -> line.split(","))
                        .map(line -> Stream.of(line).sorted().toArray(String[]::new))
                        .map(line -> {
                            return IntStream.range(0, line.length)
                                    // i + 1 is needed in because the first name will be associated with
                                    // the 0th element in the IntStream.  However, the name score algorithm
                                    // needs the first name to have a position of 1
                                    .mapToObj(i -> Pair.with(i + 1, line[i]))
                                    .collect(Collectors.toList());
                        })
                        .map(line -> {
                            return line
                                    .parallelStream()
                                    .map(this.simpleNameScoreAlgorithm::calculateNameScore)
                                    .reduce(0l, (a, b) -> a + b);
                        })
                        .reduce(0l, (a, b) -> a + b);
    }
}
