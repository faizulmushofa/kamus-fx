package io.github.kamusfx.repository.User;

public class UserQuery {
    public static final String FIND_ALL =
            "SELECT * FROM users";

    public static final String FIND_BY_ID =
            "SELECT * FROM users WHERE id = ?";

    public static final String FIND_BY_USERNAME =
            "SELECT * FROM users WHERE username = ?";

    public static final String INSERT =
            "INSERT INTO users (username, password) VALUES (?, ?)";

    public static final String UPDATE =
            "UPDATE users SET username = ?, password = ?, updatedAt = CURRENT_TIMESTAMP WHERE id = ?";

    public static final String DELETE =
            "DELETE FROM users WHERE id = ?";

    public static final String LOGIN =
            "SELECT * FROM users WHERE username = ? AND password = ?";
}
