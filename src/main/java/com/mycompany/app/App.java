package com.mycompany.app;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class App {
    public static String findStats(Integer[] class1, Integer[] class2, char mode, boolean ignore) {
        if (class1 == null || class2 == null || class1.length == 0 || class2.length == 0 ||
                (mode != 'w' && mode != 'b' && mode != 'a'))
            return "Please enter valid inputs!";

        for (Integer i : class1) {
            if (i < 0 || i > 100)
                return "Please enter valid inputs!";
        }

        for (Integer i : class2) {
            if (i < 0 || i > 100)
                return "Please enter valid inputs!";
        }

        String result = "";

        if (mode == 'w') {

            int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;

            for (Integer integer : class1) {
                if (!(ignore && integer == 0))
                    min1 = Math.min(min1, integer);
            }
            for (Integer integer : class2) {
                if (!(ignore && integer == 0))
                    min2 = Math.min(min2, integer);
            }

            if(ignore && (min1 == Integer.MAX_VALUE || min2 == Integer.MAX_VALUE)){
                result = "There's nothing to compare if you tell me to ignore all 0's :[";
            }
            else{
                result = "Worst grade for the first class is " + min1 + "." +
                        "\nWorst grade for the second class is " + min2 + ".";

                if (min1 > min2) {
                    result += "\nFirst class is better than the second class for worst grade";
                } else if (min1 == min2)
                    result += "\nBoth classes have the same worst grade";
                else
                    result += "\nSecond class is better than the first class for worst grade";
            }

        } else if (mode == 'b') {

            int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE;

            for (Integer i : class1) {
                if (!(ignore && i == 0))
                    max1 = Math.max(max1, i);
            }
            for (Integer i : class2) {
                if (!(ignore && i == 0))
                    max2 = Math.max(max2, i);
            }

            if (ignore && (max1 == Integer.MIN_VALUE || max2 == Integer.MIN_VALUE)){
                result = "There's nothing to compare if you tell me to ignore all 0's :[";
            }
            else {
                result = "Best grade for the first class is " + max1 + "." +
                        "\nBest grade for the second class is " + max2 + ".";

                if (max1 > max2) {
                    result += "\nFirst class is better than the second class for best grade";
                } else if (max1 == max2)
                    result += "\nBoth classes have the same best grade";
                else
                    result += "\nSecond class is better than the first class for best grade";
            }

        } else if (mode == 'a') {

            double avg1 = 0, avg2 = 0;
            int count1 = 0, count2 = 0;
            boolean allZeros1 = true, allZeros2 = true;

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("#.##", symbols);

            for (Integer i : class1) {
                avg1 += i;
                if (!(ignore && i == 0))
                    count1++;
                if (i != 0)
                    allZeros1 = false;
            }

            for (Integer i : class2) {
                avg2 += i;
                if (!(ignore && i == 0))
                    count2++;
                if (i != 0)
                    allZeros2 = false;
            }

            if (allZeros1 || allZeros2){
                result = "There's nothing to compare if you tell me to ignore all 0's :[";
            }
            else {
                avg1 = (count1 == 0) ? 0 : avg1 / count1;
                avg2 = (count2 == 0) ? 0 : avg2 / count2;

                result = "Average grade for the first class is " + df.format(avg1) + "." +
                        "\nAverage grade for the second class is " + df.format(avg2) + ".";

                if (avg1 > avg2) {
                    result += "\nFirst class is better than the second class on average";
                } else if (avg1 == avg2)
                    result += "\nBoth classes have the same average grade";
                else
                    result += "\nSecond class is better than the first class on average";
            }

        }

        return result;
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());

        get("/", (req, res) -> "Let's Compare Grades :] please add /compute at the end of the url");

        post("/compute", (req, res) -> {

            String result = "";

            try{
                String input1 = req.queryParams("input1");
                Scanner sc1 = new Scanner(input1);
                sc1.useDelimiter("[;\\s]+");
                ArrayList<Integer> inputList = new ArrayList<>();
                while (sc1.hasNext()) {
                    int value = Integer.parseInt(sc1.next().replaceAll("\\s", ""));
                    inputList.add(value);
                }

                Integer[] inputList1 = new Integer[inputList.size()];
                inputList1 = inputList.toArray(inputList1);

                String input2 = req.queryParams("input2");
                sc1 = new Scanner(input2);
                sc1.useDelimiter("[;\\s]+");
                inputList = new ArrayList<>();
                while (sc1.hasNext()) {
                    int value = Integer.parseInt(sc1.next().replaceAll("\\s", ""));
                    inputList.add(value);
                }

                Integer[] inputList2 = new Integer[inputList.size()];
                inputList2 = inputList.toArray(inputList2);

                char input3AsChar = req.queryParams("input3").charAt(0);

                boolean input4AsBoolean = Boolean.parseBoolean(req.queryParams("input4"));

                result = App.findStats(inputList1, inputList2, input3AsChar, input4AsBoolean);

            } catch (Exception e) {
                result = "Please enter valid inputs!";
            }

            Map map = new HashMap();
            map.put("result", result);
            return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());


        get("/compute",
                (rq, rs) -> {
                    Map map = new HashMap();
                    map.put("result", "not compared yet!");
                    return new ModelAndView(map, "compute.mustache");
                },
                new MustacheTemplateEngine());
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
