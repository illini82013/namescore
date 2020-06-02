package org.example.NameCalculator.NameCalculatorAlgorithm;

import org.javatuples.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class SimpleCalculateNameScoreTest {

    Pair<Integer, String> testNameTuple;
    long expectedResult;

    public SimpleCalculateNameScoreTest(Pair<Integer, String> testNameTuple, long expectedResult) {
        this.testNameTuple = testNameTuple;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters(name = "Run {index}: testNameTuple={0}, expectedResult={1}")
    public static Iterable<Object[]> data() throws Throwable
    {
        return Arrays.asList(new Object[][] {
                { new Pair<Integer, String>(1, "A"), 1 },
                { new Pair<Integer, String>(2, "A"), 2 },
                { new Pair<Integer, String>(10, "JEFF"), 270 }
        });
    }

    @Test
    public void testCalculateNameScore() {
        SimpleNameScoreAlgorithm nameCalculatorAlgorithm = new SimpleNameScoreAlgorithm();
        assertEquals(expectedResult, nameCalculatorAlgorithm.calculateNameScore(testNameTuple));
    }
}

