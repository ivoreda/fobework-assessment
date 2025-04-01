package com.muzan.musicbookingapp.controller;

import com.muzan.musicbookingapp.dto.ArtistDto;
import com.muzan.musicbookingapp.dto.LoginRequest;
import com.muzan.musicbookingapp.dto.UpdateArtistDto;
import com.muzan.musicbookingapp.service.ArtistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/artist")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistService artistService;

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDto> getArtist (@PathVariable UUID id) {
        return ResponseEntity.ok(artistService.getArtist(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistDto> update (@PathVariable UUID id, @RequestBody @Valid UpdateArtistDto updateArtistDto) {
        return ResponseEntity.ok(artistService.updateArtist(id, updateArtistDto));
    }
}
