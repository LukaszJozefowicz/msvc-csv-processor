package com.ljozefowicz.csvprocessor.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.ljozefowicz.csvprocessor.model.DataEntry;
import com.ljozefowicz.csvprocessor.model.Dataset;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvProcessorService {

    private static final String MY_ACCOUNT = "My account";
    public Dataset processFile(MultipartFile file) throws IOException {

        List<DataEntry> income = new ArrayList<>();
        List<DataEntry> expense = new ArrayList<>();

        CsvMapper mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);

        InputStream inputStream = file.getInputStream();

        MappingIterator<String[]> iterator = mapper
                .readerFor(String[].class)
                .readValues(inputStream);

        if(iterator.hasNext()) {
            iterator.next();        //skip the header line
        }

        while (iterator.hasNext()) {
            String[] row = iterator.next();

            boolean isExpense = new BigDecimal(row[3]).compareTo(BigDecimal.ZERO) < 0;

            DataEntry dataEntry = DataEntry.builder()
                    .transactionDate(LocalDate.parse(row[1]))
                    .amount(new BigDecimal(row[3]).abs())
                    .currency(row[4])
                    .transactionType(row[2])
                    .recipient(isExpense ? row[7] : MY_ACCOUNT)
                    .sender(isExpense ? MY_ACCOUNT : row[7])
                    .transactionDescription(isExpense ? row[6] : row[9])
                    .build();

            if (isExpense) {
                expense.add(dataEntry);
            } else {
                income.add((dataEntry));
            }

        }

        return Dataset.builder()
                .name("default")
                .income(income)
                .expense(expense)
                .build();
    }
}
