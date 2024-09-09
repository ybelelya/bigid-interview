package com.bigid.interview.matcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import java.util.Objects;
import java.util.Set;

public final class Names {

    private static final Set<String> content;

    static {
        try (InputStream inputStream = Names.class.getResourceAsStream("/names.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream),
                     StandardCharsets.UTF_8))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            content = Set.of(stringBuilder.toString().split(","));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Names() {

    }

    public static Set<String> content() {
        return content;
    }
}
