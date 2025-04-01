package com.muzan.musicbookingapp.service;

import com.muzan.musicbookingapp.dto.ArtistDto;
import com.muzan.musicbookingapp.dto.UpdateArtistDto;
import com.muzan.musicbookingapp.exception.ResourceNotFoundException;
import com.muzan.musicbookingapp.model.Artist;
import com.muzan.musicbookingapp.repository.IArtistRepository;
import com.muzan.musicbookingapp.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final IArtistRepository artistRepository;
    private final ModelMapper modelMapper;

    public ArtistDto createArtist(ArtistDto artistDto) {
        var artist = modelMapper.map(artistDto, Artist.class);
        Artist artistEntity = artistRepository.save(artist);
        return modelMapper.map(artistEntity, ArtistDto.class);
    }

    public ArtistDto updateArtist(UUID id, UpdateArtistDto updateArtistDto) {
        var artistEntity = artistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        artistEntity.setBio(updateArtistDto.getBio());
        artistRepository.save(artistEntity);
        return modelMapper.map(artistEntity, ArtistDto.class);
    }

    public ArtistDto getArtist(UUID id) {
        var artistEntity = artistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        return modelMapper.map(artistEntity, ArtistDto.class);
    }
}
