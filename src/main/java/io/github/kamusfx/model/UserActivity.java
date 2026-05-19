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
    private String action;
    private String detail;
    private long timestamp;
}
