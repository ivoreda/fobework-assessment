package com.muzan.musicbookingapp.service;

import com.muzan.musicbookingapp.dto.EventDto;
import com.muzan.musicbookingapp.exception.ResourceNotFoundException;
import com.muzan.musicbookingapp.model.*;
import com.muzan.musicbookingapp.repository.IArtistRepository;
import com.muzan.musicbookingapp.repository.IEventRepository;
import com.muzan.musicbookingapp.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {
    private final IEventRepository eventRepository;
    private final IArtistRepository artistRepository;
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;


    public EventDto.EventResponse getEvent(UUID id) {
        var eventEntity = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        var eventDto = modelMapper.map(eventEntity, EventDto.EventResponse.class);
        return eventDto;
    }

    public Page<EventDto.EventResponse> getEvents(Pageable pageable) {
        Page<Event> eventPage = eventRepository.findAll(pageable);
        return eventPage.map(eventEntity -> modelMapper.map(eventEntity, EventDto.EventResponse.class));
    }

    public Page<EventDto.EventResponse> getEventsByArtist(UUID artistId, Pageable pageable) {
        Page<Event> eventPage = eventRepository.findByCreatedByIdOrderByStartTimeDesc(artistId, pageable);
        return eventPage.map(eventEntity -> modelMapper.map(eventEntity, EventDto.EventResponse.class));
    }

    public EventDto.EventResponse createEvent(EventDto.CreateEventRequest createEventRequest) {

        Event eventEntity = modelMapper.map(createEventRequest, Event.class);

        User currentUser = userService.getCurrentUser();

        if (currentUser.getRole() != UserRole.ARTIST) {
            throw new AccessDeniedException("Only artists can create events");
        }

        Artist artist = artistRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist profile not found for current user"));

        eventEntity.setCreatedBy(artist);
        Event savedEvent = eventRepository.save(eventEntity);
        return modelMapper.map(savedEvent, EventDto.EventResponse.class);
    }

    public EventDto.EventResponse updateEvent(UUID id, EventDto.UpdateEventRequest updateEventRequest) {
        var eventEntity = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        if (eventEntity.getCreatedBy().getId() != userService.getCurrentUser().getId()) {
            throw new AccessDeniedException("Only the artist who created this event can update it");
        }

        if (updateEventRequest.getName() != null) {
            eventEntity.setName(updateEventRequest.getName());
        }

        if (updateEventRequest.getDescription() != null) {
            eventEntity.setDescription(updateEventRequest.getDescription());
        }

        if (updateEventRequest.getLocation() != null) {
            eventEntity.setLocation(updateEventRequest.getLocation());
        }

        if (updateEventRequest.getStartTime() != null) {
            eventEntity.setStartTime(updateEventRequest.getStartTime());
        }

        if (updateEventRequest.getEndTime() != null) {
            eventEntity.setEndTime(updateEventRequest.getEndTime());
        }

        if (updateEventRequest.getTicketPrice() != null) {
            eventEntity.setTicketPrice(updateEventRequest.getTicketPrice());
        }

        if (updateEventRequest.getTicketsAvailable() != null) {
            eventEntity.setTicketsAvailable(updateEventRequest.getTicketsAvailable());
        }

        if (updateEventRequest.getIsPublished() != null) {
            eventEntity.setIsPublished(updateEventRequest.getIsPublished());
        }

        Event savedEvent = eventRepository.save(eventEntity);
        return modelMapper.map(savedEvent, EventDto.EventResponse.class);
    }


}
