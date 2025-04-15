package deveducate.library.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SubscriptionBookDataContainerDto {

    private SubscriptionDto subscriptionDto;
    private BookEntryDto bookEntryDto;
}
