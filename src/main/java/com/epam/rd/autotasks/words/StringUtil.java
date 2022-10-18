package com.epam.rd.autotasks.words;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class StringUtil {
    public static int countEqualIgnoreCaseAndSpaces(String[] words, String sample) {
        if(words == null || sample == null){
            return 0;
        }
        int equalsCounter = 0;
        for(String word : words){
            if(word.trim().equalsIgnoreCase(sample.trim())){
                equalsCounter++;
            }
        }
        return equalsCounter;
    }

    public static String[] splitWords(String text) {
        if(text == null || text.isEmpty() || text.isBlank() || text.matches("[,;:.&!?\\s]+")){
            return null;
        }
        String[] wordsSplitted = Arrays.stream(text.split("[,.;:&!\\s?]+", 0)).filter(e -> e.trim().length()>0).toArray(String[]::new);
        return wordsSplitted;
    }

    public static String convertPath(String path, boolean toWin) {
        if(path == null || path.isEmpty() || path.isBlank()){
            return null;
        }
        Pattern patternWin = Pattern.compile("^(C:)?[^~/:]*");
        Pattern patternUnix = Pattern.compile("^(~)?[^\\\\:~]*");

        if(!Pattern.matches(String.valueOf(patternWin),path) && !Pattern.matches(String.valueOf(patternUnix),path)){
            return null;
        }
        if(Pattern.matches(String.valueOf(patternWin),path) && toWin){
            return path;
        }
        else if(Pattern.matches(String.valueOf(patternUnix),path) && !toWin){
            return path;
        }
        StringBuilder convertedPath = new StringBuilder();

        if(toWin){
            if(path.startsWith("/")){
                convertedPath.append("C:\\");
                convertedPath.append(path.substring(1).replaceAll("/","\\\\"));
            }
            else if(path.contains(("~"))){
                convertedPath.append("C:\\User");
                convertedPath.append(path.substring(1).replaceAll("/","\\\\"));
            }
            else{
                convertedPath.append(path.replaceAll("/","\\\\"));
            }
        }
        else{
            if(path.contains(("C:\\User"))){
                convertedPath.append("~");
                convertedPath.append(path.substring(7).replaceAll("\\\\","/"));
            }
            else if(path.contains("C:\\")){
                convertedPath.append("/");
                convertedPath.append(path.substring(3).replaceAll("\\\\","/"));
            }
            else{
                convertedPath.append(path.replaceAll("\\\\","/"));
            }
        }

        return convertedPath.toString();
    }

    public static String joinWords(String[] words) {
        if(words == null || words.length == 0){
            return null;
        }
        StringJoiner join = new StringJoiner(", ", "[","]");
        for(String word : words){
            if(word.isEmpty() || word.isBlank() || word == null){
                continue;
            }
            join.add(word);
        }
        if(join.length()==2){
            return null;
        }
        return join.toString();
    }

    public static void main(String[] args) {
        System.out.println("Test 1: countEqualIgnoreCaseAndSpaces");
        String[] words = new String[]{" WordS    \t", "words", "w0rds", "WOR  DS", };
        String sample = "words   ";
        int countResult = countEqualIgnoreCaseAndSpaces(words, sample);
        System.out.println("Result: " + countResult);
        int expectedCount = 2;
        System.out.println("Must be: " + expectedCount);

        System.out.println("Test 2: splitWords");
        String text = "   ,, first, second!!!! third";
        String[] splitResult = splitWords(text);
        System.out.println("Result : " + Arrays.toString(splitResult));
        String[] expectedSplit = new String[]{"first", "second", "third"};
        System.out.println("Must be: " + Arrays.toString(expectedSplit));

        System.out.println("Test 3: convertPath");
        String unixPath = "/some/unix/path";
        String convertResult = convertPath(unixPath, true);
        System.out.println("Result: " + convertResult);
        String expectedWinPath = "C:\\some\\unix\\path";
        System.out.println("Must be: " + expectedWinPath);

        System.out.println("Test 4: joinWords");
        String[] toJoin = new String[]{"go", "with", "the", "", "FLOW"};
        String joinResult = joinWords(toJoin);
        System.out.println("Result: " + joinResult);
        String expectedJoin = "[go, with, the, FLOW]";
        System.out.println("Must be: " + expectedJoin);
    }
}