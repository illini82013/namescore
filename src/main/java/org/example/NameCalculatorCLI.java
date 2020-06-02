package org.example;

import org.apache.commons.cli.*;
import org.example.NameCalculator.NameCalculator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/***
 * NameCalculatorCLI is a command line utility that calculates a name score
 * The utility takes a file and a name score algorithm and returns a name score
 * Sample command line arguments:
 * -a <algorithm> -f <pathToFile>
 * -a simple -f path/to/names.txt
 */
public class NameCalculatorCLI {

    public static void main(String[] args) throws ParseException {

        // Map of arguments chosen by the user
        HashMap<String, String> flags = parseCommandLineArguments(args);

        // If either required flag is set to null, the program cannot continue
        if (flags.get("algorithm") == null || flags.get("file") == null) {
            System.out.println("Required parameters are not set");
            return;
        }

        boolean useStreams = false;
        // Optionally, if the -s flag is set, execute the simple name score algorithm using
        // java streams
        if (flags.containsKey("stream")) {
            useStreams = true;
        }

        // Initialize a name calculator with the algorithm chosen by the user
        NameCalculator nameCalculator = new NameCalculator(flags.get("algorithm"));
        long totalScore = -1;

        // Execute the name calculator using a file as an input
        try {
            if (useStreams) {
                totalScore = nameCalculator.calculateScoreFromFileStream(flags.get("file"));
            } else {
                totalScore = nameCalculator.calculateScoreFromFile(flags.get("file"));
            }
        } catch (IOException ioe) {
            System.out.println("Unable to calculate name score");
            ioe.printStackTrace();
        }
        System.out.println("Score: " + totalScore);
    }

    /**
     * Parses the command line arguments
     *
     * @param args command line parameters includes file and algorithm
     * @return returns a hashmap with the arguments chosen by the user
     * @throws ParseException
     */
    public static HashMap<String, String> parseCommandLineArguments(String[] args) throws ParseException {
        String absoluteFilePath = null;

        CommandLineParser parser = new DefaultParser();
        CommandLine cmdLine = null;

        // Set command line options
        Options options = new Options();
        options.addOption("a", "algorithm", true, "Name counter algorithm");
        options.addOption("f", "file", true, "File containing names");
        options.addOption("s", "stream", false, "Enable streaming calculation for the simple name algorithm");

        File inputFile;
        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException pe) {
            System.out.println("An error occurred while parsing input parameters");
            throw pe;
        }

        HashMap<String, String> flags = new HashMap<String, String>();
        if (cmdLine.hasOption("algorithm")) {
            flags.put("algorithm", cmdLine.getOptionValue("algorithm"));
        } else {
            System.out.println("Please enter a valid algorithm");
            return flags;
        }

        if (cmdLine.hasOption("file")) {
            absoluteFilePath = cmdLine.getOptionValue("file");
            inputFile = new File(absoluteFilePath);
            // Check if the path exists and is points to a file
            if (!(inputFile.exists() && !inputFile.isDirectory())) {
                System.out.println(String.format("File path: %s is not valid", absoluteFilePath));
                return flags;
            }
            flags.put("file", absoluteFilePath);
        } else {
            System.out.println("Please enter a file containing names");
            return flags;
        }

        if (cmdLine.hasOption("stream")) {
            flags.put("stream", "true");
        }

        return flags;
    }

}
