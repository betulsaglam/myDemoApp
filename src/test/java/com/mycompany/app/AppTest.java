package com.mycompany.app;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testDifferentWorst1(){
        Integer[] arr1 = {95, 43, 56, 77, 12, 30, 84};
        Integer[] arr2 = {62, 34, 15, 79, 91, 53, 80};

        String expected = "Worst grade for the first class is 12." +
                "\nWorst grade for the second class is 15." +
                "\nSecond class is better than the first class for worst grade";

        assertEquals(expected, App.findStats(arr1, arr2, 'w', false));
    }

    public void testDifferentWorst2(){
        Integer[] arr1 = {95, 43, 56, 77, 0, 30, 84};
        Integer[] arr2 = {62, 34, 15, 79, 91, 53, 80};

        String expected = "Worst grade for the first class is 0." +
                "\nWorst grade for the second class is 15." +
                "\nSecond class is better than the first class for worst grade";

        assertEquals(expected, App.findStats(arr1, arr2, 'w', false));
    }

    public void testSameWorst(){
        Integer[] arr = {12, 90, 77, 46, 73, 29, 57, 99, 43, 0};

        String expected = "Worst grade for the first class is 12." +
                "\nWorst grade for the second class is 12." +
                "\nBoth classes have the same worst grade";

        assertEquals(expected, App.findStats(arr, arr, 'w', true));
    }

    public void testDifferentBest(){
        Integer[] arr1 = {41, 85, 99, 65, 60, 24, 19, 52, 2};
        Integer[] arr2 = {6, 91, 58, 86, 54, 43, 85, 72, 64};

        String expected = "Best grade for the first class is 99." +
                "\nBest grade for the second class is 91." +
                "\nFirst class is better than the second class for best grade";

        assertEquals(expected, App.findStats(arr1, arr2, 'b', false));
    }

    public void testSameBest(){
        Integer[] arr = {23, 45, 88, 91, 36, 19, 72, 67, 31, 55};

        String expected = "Best grade for the first class is 91." +
                "\nBest grade for the second class is 91." +
                "\nBoth classes have the same best grade";

        assertEquals(expected, App.findStats(arr, arr, 'b', false));
    }

    public void testAverage(){
        Integer[] arr1 = {9, 46, 61, 21, 56, 98, 67, 41, 87};
        Integer[] arr2 = {43, 79, 90, 34, 67, 33, 69, 35, 100};

        String expected = "Average grade for the first class is 54." +
                "\nAverage grade for the second class is 61.11." +
                "\nSecond class is better than the first class on average";

        assertEquals(expected, App.findStats(arr1, arr2, 'a', false));
    }

    public void testEmptyArray(){
        Integer[] arr1 = {};
        Integer[] arr2 = {11,23,64,78,57};

        String expected = "Please enter valid inputs!";

        assertEquals(expected, App.findStats(arr1, arr2, 'a', false));
    }

    public void testIgnoreZero(){
        Integer[] arr1 = {9, 46, 61, 21, 56, 98, 67, 41, 87};
        Integer[] arr2 = {20, 51, 90, 82, 49, 78, 45, 62, 24, 0, 0, 0, 0};

        String expected = "Average grade for the first class is 54." +
                "\nAverage grade for the second class is 55.67." +
                "\nSecond class is better than the first class on average";

        assertEquals(expected, App.findStats(arr1, arr2, 'a', true));
    }

    public void testIgnoreAllZeros(){
        Integer[] arr1 = {0, 0, 0, 0, 0, 0};
        Integer[] arr2 = {12, 0, 32, 86, 65, 20, 0};

        String expected = "There's nothing to compare if you tell me to ignore all 0's :(";

        assertEquals(expected, App.findStats(arr1, arr2, 'a', true));
    }

    public void testNull(){
        String expected = "Please enter valid inputs!";

        assertEquals(expected, App.findStats(null, null, 'b', false));
    }

    public void testBelowMinimum(){
        Integer[] arr1 = {-32, 23, 65, 39, 96, 54};
        Integer[] arr2 = {23, 87, 54, 82, 73, 39};

        String expected = "Please enter valid inputs!";

        assertEquals(expected, App.findStats(arr1, arr2, 'a', false));
    }

    public void testAboveMaximum(){
        Integer[] arr1 = {12, 84, 28, 11, 43, 65, 84, 61};
        Integer[] arr2 = {42, 66, 48, 90, 76, 75, 46, 110, 89};

        String expected = "Please enter valid inputs!";

        assertEquals(expected, App.findStats(arr1, arr2, 'a', false));
    }

}
