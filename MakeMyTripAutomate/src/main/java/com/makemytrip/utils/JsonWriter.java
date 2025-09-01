package com.makemytrip.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter {

    public static void writeJsonToFile(String fileName, String key, String value) {
        // Ensure test-output directory exists
//        File dir = new File(System.getProperty("user.dir") + "/src/test/resources/output");
        File dir = new File(ConfigReader.getProperty("output"));

    	if (!dir.exists())
            dir.mkdirs();

        File out = new File(dir, fileName);
        String json = "{\n  \"" + key + "\": \"" + value + "\"\n}\n";

        try (FileWriter fw = new FileWriter(out, false)) {
            fw.write(json);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write JSON file: " + out.getAbsolutePath(), e);
        }
    }
}
