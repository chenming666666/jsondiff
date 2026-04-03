package com.jsondiff.controller;

import com.jsondiff.model.JsonFormatRequest;
import com.jsondiff.model.JsonFormatResponse;
import com.jsondiff.service.JsonParseService;
import com.jsondiff.service.JsonValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JsonDiffController {

    private final JsonValidationService validationService;
    private final JsonParseService parseService;

    @Autowired
    public JsonDiffController(JsonValidationService validationService, JsonParseService parseService) {
        this.validationService = validationService;
        this.parseService = parseService;
    }

    @PostMapping("/format")
    public JsonFormatResponse formatJson(@RequestBody JsonFormatRequest request) {
        String error1 = validationService.validateJson(request.getJson1());
        if (error1 != null) {
            return JsonFormatResponse.error("Left JSON: " + error1);
        }

        String error2 = validationService.validateJson(request.getJson2());
        if (error2 != null) {
            return JsonFormatResponse.error("Right JSON: " + error2);
        }

        String formatted1 = parseService.formatJson(request.getJson1());
        String formatted2 = parseService.formatJson(request.getJson2());

        return JsonFormatResponse.success(formatted1, formatted2);
    }
}
