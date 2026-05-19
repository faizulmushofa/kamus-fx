package io.github.kamusfx.model;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class Session {

    private User user;
    private final List<UserActivity> activities;

    public void log(String action, String detail) {
        activities.add(new UserActivity(action, detail, System.currentTimeMillis()));
    }


}
