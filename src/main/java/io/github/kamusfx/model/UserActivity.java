package io.github.kamusfx.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserActivity {
    private Long id;
    private String action;
    private String detail;
    private String timestamp;

    @Override
    public String toString() {
        return id + " | " + action + " | " + detail + " | " + timestamp;
    }
}
