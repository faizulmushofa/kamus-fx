package io.github.kamusfx.repository.Dictionary;

public class DictionaryQuery {
    public static final String FIND_ALL =
            "SELECT * FROM dictionaries";

    public static final String INSERT =
            "INSERT INTO dictionaries (indonesia, english) VALUES (?, ?)";

    public static final String DELETE_BY_ID =
            "DELETE FROM dictionaries WHERE id = ?";

    public static final String UPDATE =
            "UPDATE dictionaries SET indonesia = ?, english = ? WHERE id = ?";

    public static final String FIND_BY_INDONESIA =
            "SELECT * FROM dictionaries WHERE indonesia = ?";

    public static final String FIND_BY_ENGLISH =
            "SELECT * FROM dictionaries WHERE english = ?";
}
