package com.crater.pa.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReadFileUtils {
    public static Optional<List<String>> readFile(File file, String retrievalStartsRegex, String retrievalEndRegex) {
        List<String> result = new ArrayList<>();
        try (var reader = new BufferedReader(new FileReader(file))) {
            String oneLineContent;
            boolean isInScope = false;
            while ((oneLineContent = reader.readLine()) != null) {
                if (oneLineContent.matches(retrievalStartsRegex)) {
                    isInScope = true;
                } else if (oneLineContent.matches(retrievalEndRegex)) {
                    isInScope = false;
                }
                if (isInScope) {
                    result.add(oneLineContent);
                }
            }
            return result.isEmpty() ? Optional.empty() : Optional.of(result);
        } catch (IOException e) {
            throw new RuntimeException("read file fail", e);
        } catch (Exception e) {
            throw new RuntimeException("read file have unknown error", e);
        }
    }
}
