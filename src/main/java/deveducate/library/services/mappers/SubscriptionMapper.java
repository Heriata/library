package deveducate.library.services.mappers;

import deveducate.library.dtos.SubscriptionDto;
import deveducate.library.models.SubscriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    @Mapping(target = "subscriptionId", ignore = true)
    @Mapping(target = "books", ignore = true)
    SubscriptionEntity toSubscriptionEntity(SubscriptionDto dto);
}
