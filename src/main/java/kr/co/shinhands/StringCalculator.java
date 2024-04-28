package kr.co.shinhands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    private boolean isBlank(String text) {
        return text == null || text.isEmpty();
    }

    private String[] split(String text) {
        Matcher matcher = Pattern.compile("//(.)\n(.*)").matcher(text);
        if (matcher.find()) {
            String customDelimiter = matcher.group(1);
            return matcher.group(2).split(customDelimiter);
        }
        return text.split(",|:");
    }

    int add(String text) {
        if (isBlank(text)) {
            return 0;
        }
        if (text.contains(",") || text.contains(":") || text.contains(";")) {
            String[] values = split(text);
            return sum(toInts(values));
        }
        return Integer.parseInt(text);
    }

    private int[] toInts(String[] values) {
        int[] numbers = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            numbers[i] = toPositiveInt(values[i]);
        }
        return numbers;
    }

    private static int toPositiveInt(String values) {
        int number = Integer.parseInt(values);
        if (number < 0) {
            throw new RuntimeException();
        }
        return number;
    }

    private int sum(int[] numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }
}