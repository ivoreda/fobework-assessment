package com.muzan.musicbookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateArtistDto {

    private String stageName;
    private String bio;
}
