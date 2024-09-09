package com.bigid.interview.matcher;

import com.bigid.interview.model.Occurrence;

import java.util.*;
import java.util.regex.Pattern;

public class Matcher {


    public Map<String, List<Occurrence>> match(String text, int lineOffset) {
        Map<String, List<Occurrence>> occurrencesMap = new HashMap<>();
        Pattern pattern = Pattern.compile("\\b(" + String.join("|", Names.content()) + ")\\b");
        java.util.regex.Matcher matcher = pattern.matcher(text);

        int charOffset = 0;
        while (matcher.find()) {
            String name = matcher.group();
            int matchStart = matcher.start();
            long lineNum = (int) text.substring(0, matchStart).chars().filter(ch -> ch == '\n').count();

            Occurrence occurrence = new Occurrence(lineOffset + lineNum, charOffset + matchStart);
            occurrencesMap.computeIfAbsent(name, k -> new ArrayList<>()).add(occurrence);
        }
        return occurrencesMap;
    }

}
