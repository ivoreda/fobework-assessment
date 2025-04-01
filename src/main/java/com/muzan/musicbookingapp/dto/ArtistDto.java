package com.muzan.musicbookingapp.dto;

import com.muzan.musicbookingapp.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistDto {

        @Null
        private UUID id;

        @NotNull
        private User user;

        private String stageName;
        private String bio;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

}
