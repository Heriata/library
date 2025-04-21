package deveducate.library.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscriptions", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class SubscriptionEntity {
    @Id
    @Column(name = "subscription_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscription_seq")
    @SequenceGenerator(name = "subscription_seq", sequenceName = "subscriptions_subscription_id_seq", allocationSize = 1)
    private Long subscriptionId;

    @Column(name = "username")
    private String username;
    @Column(name = "user_full_name")
    private String userFullName;
    @Column(name = "user_active")
    private boolean userActive;
    @Column(name = "user_email")
    private String userEmail;

    @ToString.Exclude
    @OneToMany(mappedBy = "subscription", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BookEntryEntity> books;
}
