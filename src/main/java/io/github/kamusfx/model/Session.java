package io.github.kamusfx.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public abstract class Session {

    protected static User user;

    protected static List<UserActivity> activities = new ArrayList<>();

}
