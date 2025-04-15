package deveducate.library.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SubscriptionDto {
    private String username;
    private String userFullName;
    private Boolean userActive;

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SubscriptionDto)) return false;
        SubscriptionDto dto = (SubscriptionDto) obj;
        return username.equals(dto.getUsername());
    }
}
