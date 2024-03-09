package com.mycompany.app;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class App
{
    public static String findStats(Integer[] class1, Integer[] class2, char mode, boolean ignore){
        if(class1 == null || class2 == null || class1.length == 0 || class2.length == 0 ||
                (mode != 'w' && mode != 'b' && mode !='a'))
            return "Please enter valid inputs!";

        String result = "";

        if (mode == 'w'){

            int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;

            for (Integer integer : class1) {
                if(!(ignore && integer == 0))
                    min1 = Math.min(min1, integer);
            }
            for (Integer integer : class2) {
                if(!(ignore && integer == 0))
                    min2 = Math.min(min2, integer);
            }
            result = "Worst grade for the first class is " + min1 +
                    "\nWorst grade for the second class is " + min2;

            if (min1 > min2){
                result += "\nFirst class is better than the second class for worst grade";
            }
            else if(min1 == min2)
                result += "\nBoth classes have the same worst grade";
            else
                result += "\nSecond class is better than the first class for worst grade";
        }
        else if(mode == 'b'){

            int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE;

            for (Integer i : class1) {
                if(!(ignore && i == 0))
                    max1 = Math.max(max1, i);
            }
            for (Integer i : class2) {
                if(!(ignore && i == 0))
                    max2 = Math.max(max2, i);
            }
            result = "Best grade for the first class is " + max1 +
                    "\nBest grade for the second class is " + max2;

            if (max1 > max2){
                result += "\nFirst class is better than the second class for best grade";
            }
            else if(max1 == max2)
                result += "\nBoth classes have the same best grade";
            else
                result += "\nSecond class is better than the first class for best grade";

        }
        else if(mode == 'a'){

            double avg1 = 0, avg2 = 0;
            int count1 = 0, count2 = 0;

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("#.##", symbols);

            for (Integer i : class1){
                avg1 += i;
                if(!(ignore && i == 0))
                    count1++;
            }
            for (Integer i : class2){
                avg2 += i;
                if(!(ignore && i == 0))
                    count2++;
            }

            avg1 = (count1 == 0) ? 0 : avg1 / count1;
            avg2 = (count2 == 0) ? 0 : avg2 / count2;

            result = "Average grade for the first class is " + df.format(avg1) +
                    "\nAverage grade for the second class is " + df.format(avg2);

            if (avg1 > avg2){
                result += "\nFirst class is better than the second class on average";
            }
            else if(avg1 == avg2)
                result += "\nBoth classes have the same average grade";
            else
                result += "\nSecond class is better than the first class on average";
        }

        return result;
    }

}
