package com.jsondiff.service;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import org.springframework.stereotype.Service;

import java.io.StringReader;

@Service
public class JsonValidationService {

    public String validateJson(String jsonStr) {
        if (jsonStr == null || jsonStr.trim().isEmpty()) {
            return "JSON cannot be empty";
        }

        try {
            JsonReader reader = new JsonReader(new StringReader(jsonStr));
            reader.setLenient(false);
            while (reader.hasNext()) {
                reader.skipValue();
            }
            reader.close();
            return null;
        } catch (JsonSyntaxException e) {
            return "JSON syntax error: " + e.getMessage();
        } catch (JsonIOException e) {
            return "JSON IO error: " + e.getMessage();
        } catch (Exception e) {
            return "Invalid JSON: " + e.getMessage();
        }
    }
}
