package deveducate.library.services;

import deveducate.library.models.SubscriptionEntity;
import deveducate.library.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        //todo null checks

        return subscriptionRepository.findByUserFullName(fullName, pageRequest);
    }

}
