package org.example.NameCalculator.NameCalculatorAlgorithm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SimpleCalculateScoreTest{
    String[] testNamesInLine;
    long expectedResult;

    public SimpleCalculateScoreTest(String[] testNamesInLine, long expectedResult) {
        this.testNamesInLine = testNamesInLine;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters(name = "Run {index}: testNamesInLine={0}, expectedResult={1}")
    public static Iterable<Object[]> data() throws Throwable
    {
        return Arrays.asList(new Object[][] {
                { new String[] {"JEFF"}, 27 },
                { new String[] {"ABLE", "DON", "JEFF"}, 167 },
                { new String[] {"JEFF", "ABLE", "DON"}, 167 }
        });
    }

    @Test
    public void testCalculateScore() {
        SimpleNameScoreAlgorithm nameCalculatorAlgorithm = new SimpleNameScoreAlgorithm();
        assertEquals(expectedResult, nameCalculatorAlgorithm.calculateScore(testNamesInLine));
    }
}
