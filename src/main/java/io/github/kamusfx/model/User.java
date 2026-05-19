package io.github.kamusfx.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
   private Long id;
   private String username;
   private String password;
   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;
}
