package deveducate.library.controllers;


import deveducate.library.services.ExcelDataHandlerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Tag(name = "Excel Data Upload Controller")
@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class ExcelDataUploadController implements GlobalController {

    private final ExcelDataHandlerService excelDataHandlerService;

    @Operation(summary = "Send JSON file")
    @PostMapping(value= "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<ResponseEntity<Void>> asyncUpload(@RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.OK));
        excelDataHandlerService.sendDataToKafka(file);
        return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.OK));
    }

    @Operation(summary = "Send String")
    @PostMapping
    public CompletableFuture<ResponseEntity<Void>> asyncUpload(@RequestBody String file) {
        if (file.isEmpty()) return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.OK));
        excelDataHandlerService.sendDataToKafka(file);


        return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.OK));
    }
}
