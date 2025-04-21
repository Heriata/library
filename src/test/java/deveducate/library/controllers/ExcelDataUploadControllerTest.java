package deveducate.library.controllers;


import deveducate.library.config.TestContainerConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.FileInputStream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@DirtiesContext
@RequiredArgsConstructor
public class ExcelDataUploadControllerTest extends TestContainerConfig {

    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @DisplayName("Should return Ok when JSON string provided")
    @ParameterizedTest
    @MethodSource("deveducate.library.utils.ArgumentProvider#provideArguments")
    public void testExcelDataUploadString(String data) {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/upload")
                        .content(data)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("Should return Ok when JSON file provided")
    public void testExcelDataUploadFile() {
        MockMultipartFile multipartFile;

        try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/mock_data/data_test.json")) {
            multipartFile = new MockMultipartFile("mock_data", "data_test.json", "application/json", fileInputStream);
        }

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/upload/file")
                        .file("file", multipartFile.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }
}
