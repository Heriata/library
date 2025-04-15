package deveducate.library.repositories;

import deveducate.library.models.SubscriptionEntity;
import deveducate.library.repositories.projections.ReturnReminderProjection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    boolean existsByUsername(String username);

    List<SubscriptionEntity> findByUserFullName(String fullName, PageRequest pageRequest);

    SubscriptionEntity findByUsername(String username);

    @Query("select s from SubscriptionEntity s where s.username in :usernames")
    List<SubscriptionEntity> findAllByUsername(List<String> usernames);

    @Query(value = """
            select * from subscriptions
            join public.entries e on subscriptions.subscription_id = e.subscription_id
            join public.books b on b.book_id = e.book_id
            where e.taken_date > now() - interval '20 day';
            """, nativeQuery = true)
    List<ReturnReminderProjection> findAllWhereTakenDateIsOlder();
}
