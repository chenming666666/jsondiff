package com.jsondiff.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

@Service
public class JsonParseService {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String formatJson(String jsonStr) {
        JsonElement jsonElement = JsonParser.parseString(jsonStr);
        return gson.toJson(jsonElement);
    }
}
