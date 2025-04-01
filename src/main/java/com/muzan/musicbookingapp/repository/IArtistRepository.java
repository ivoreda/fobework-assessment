package com.muzan.musicbookingapp.repository;

import com.muzan.musicbookingapp.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IArtistRepository extends JpaRepository<Artist, UUID> {
    Optional<Artist> findByUserId(UUID id);
}
