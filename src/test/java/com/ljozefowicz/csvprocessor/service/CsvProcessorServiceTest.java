package com.ljozefowicz.csvprocessor.service;

import com.ljozefowicz.csvprocessor.model.DataEntry;
import com.ljozefowicz.csvprocessor.model.Dataset;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CsvProcessorServiceTest {
    private final CsvProcessorService serviceToTest = new CsvProcessorService();
    private static final String MY_ACCOUNT = "My account";
    private final Dataset expected = new Dataset(
            "default",
            List.of(DataEntry.builder()
                            .transactionDate(LocalDate.parse("2023-06-06"))
                            .amount(new BigDecimal("6276.97"))
                            .currency("PLN")
                            .transactionType("Przelew na rachunek")
                            .recipient(MY_ACCOUNT)
                            .sender("Nazwa nadawcy: GLOBALLOGIC POLAND SPÓŁKA Z OGRANICZONĄ ODPOWIEDZIALNOŚCIĄ")
                            .transactionDescription("Tytuł: Wynagrodzenie za 05/2023")
                            .build(),
                    DataEntry.builder()
                            .transactionDate(LocalDate.parse("2023-05-25"))
                            .amount(new BigDecimal("1000.00"))
                            .currency("PLN")
                            .transactionType("Przelew na rachunek")
                            .recipient(MY_ACCOUNT)
                            .sender("Nazwa nadawcy: Łukasz Józefowicz")
                            .transactionDescription("Tytuł: Przelew środków")
                            .build()),
            List.of(DataEntry.builder()
                            .transactionDate(LocalDate.parse("2023-06-12"))
                            .amount(new BigDecimal("105.26"))
                            .currency("PLN")
                            .transactionType("Płatność kartą")
                            .recipient("Lokalizacja: Kraj: POLSKA Miasto: WROCLAW Adres: SKLEP LIDL 1685")
                            .sender(MY_ACCOUNT)
                            .transactionDescription("Tytuł: 000483849 74838490163266497583241")
                            .build(),
                    DataEntry.builder()
                            .transactionDate(LocalDate.parse("2023-06-13"))
                            .amount(new BigDecimal("50.00"))
                            .currency("PLN")
                            .transactionType("Przelew na telefon wychodzący zew.")
                            .recipient("Nazwa odbiorcy: KAMILŚ")
                            .sender(MY_ACCOUNT)
                            .transactionDescription("Rachunek odbiorcy: 10 3333 3333 3333 3333 3333 3333")
                            .build())
    );

    @Test
    void test() throws IOException {
        InputStream inputStream = CsvProcessorServiceTest.class.getResourceAsStream("/csv/pko_sample.csv");
        Dataset result;
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            MultipartFile file = new MockMultipartFile("pko_sample.csv", "pko_sample.csv", null, content);
            result = serviceToTest.processFile(file);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getIncome(), result.getIncome());
        assertEquals(expected.getExpense(), result.getExpense());

    }

}
