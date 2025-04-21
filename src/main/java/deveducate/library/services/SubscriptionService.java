package deveducate.library.services;

import deveducate.library.exceptions.EntityNotFoundException;
import deveducate.library.models.SubscriptionEntity;
import deveducate.library.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Transactional(readOnly = true)
    public List<SubscriptionEntity> getForName(String fullName, PageRequest pageRequest) {
        if (Strings.isBlank(fullName)) {
            throw new IllegalArgumentException("fullName cannot be null");
        }

        List<SubscriptionEntity> byUserFullName = subscriptionRepository.findByUserFullName(fullName, pageRequest);

        if(byUserFullName.isEmpty()) {
            throw new EntityNotFoundException(String.format("User(s) with name '%s' not found", fullName));
        }

        return byUserFullName;
    }

}
