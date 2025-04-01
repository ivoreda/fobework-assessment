package com.muzan.musicbookingapp.repository;

import com.muzan.musicbookingapp.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface IEventRepository extends JpaRepository<Event, UUID> {
    Page<Event> findByCreatedByIdOrderByStartTimeDesc(UUID artistId, Pageable pageable);
}
