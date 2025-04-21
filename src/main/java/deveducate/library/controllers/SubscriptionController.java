package deveducate.library.controllers;

import deveducate.library.models.SubscriptionEntity;
import deveducate.library.services.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "Subscription Controller")
@RestController("/subscription")
@RequiredArgsConstructor
public class SubscriptionController implements GlobalController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    @Operation(summary = "Get Subscription info", description = "Get detailed subscription info of Who, When and took What")
    public ResponseEntity<List<SubscriptionEntity>> getUser(@RequestParam String fullName,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting subscription for {}", fullName);
        PageRequest pageRequest = PageRequest.of(page, size);
        List<SubscriptionEntity> list = subscriptionService.getForName(fullName, pageRequest);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //todo rest of the crud operations if needed
}
