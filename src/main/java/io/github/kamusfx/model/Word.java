package io.github.kamusfx.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Word {
    private Long id;
    private String indonesia;
    private String english;
}
