package com.muzan.musicbookingapp.controller;

import com.muzan.musicbookingapp.dto.EventDto;
import com.muzan.musicbookingapp.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventDto.EventResponse> createEvent(@RequestBody @Valid EventDto.CreateEventRequest createEventRequest) {
        return ResponseEntity.ok(eventService.createEvent(createEventRequest));
    }


    @GetMapping
    public ResponseEntity<Page<EventDto.EventResponse>> getEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startTime") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<EventDto.EventResponse> events = eventService.getEvents(pageable);

        return ResponseEntity.ok(events);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EventDto.EventResponse> getEventById(@PathVariable UUID id) {
        return ResponseEntity.ok(eventService.getEvent(id));
    }


    @GetMapping("/artist/{artistId}")
    public ResponseEntity<Page<EventDto.EventResponse>> getEventsByArtist(
            @PathVariable UUID artistId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startTime") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<EventDto.EventResponse> events = eventService.getEventsByArtist(artistId, pageable);

        return ResponseEntity.ok(events);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto.EventResponse> updateEvent(@PathVariable UUID id, @RequestBody @Valid EventDto.UpdateEventRequest updateEventRequest) {
        return ResponseEntity.ok(eventService.updateEvent(id, updateEventRequest));
    }

}
