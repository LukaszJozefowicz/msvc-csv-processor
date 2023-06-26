package com.ljozefowicz.csvprocessor.controller;

import com.ljozefowicz.csvprocessor.model.Dataset;
import com.ljozefowicz.csvprocessor.service.CsvProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class CsvProcessorController {

    private final CsvProcessorService csvProcessorService;

    @PostMapping("/process/csv")
    public Dataset processFile(@RequestBody MultipartFile file) throws IOException {
        return csvProcessorService.processFile(file);
    }

}
