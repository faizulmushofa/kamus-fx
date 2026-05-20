package io.github.kamusfx.service.implementation;

import io.github.kamusfx.MiniContainer.Auto;
import io.github.kamusfx.model.Session;
import io.github.kamusfx.model.User;
import io.github.kamusfx.model.UserActivity;

import java.time.LocalDateTime;

@Auto
public class SessionService extends Session {

    public static void log(String action, String detail) {
        UserActivity userActivity = UserActivity.builder()
                .id(user.getId())
                .action(action)
                .detail(detail)
                .timestamp(String.valueOf(LocalDateTime.now())).build();

        activities.add(userActivity);

        System.out.println("[ LOGGING ] : " + userActivity.toString());
    }

    public void init(User user) {
        Session.user =  user;
    }

}
