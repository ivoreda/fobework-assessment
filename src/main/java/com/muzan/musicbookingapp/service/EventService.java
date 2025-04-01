package com.muzan.musicbookingapp.service;

import com.muzan.musicbookingapp.dto.EventDto;
import com.muzan.musicbookingapp.exception.ResourceNotFoundException;
import com.muzan.musicbookingapp.repository.IArtistRepository;
import com.muzan.musicbookingapp.repository.IEventRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {
    private final IEventRepository eventRepository;
    private final IArtistRepository artistRepository;
    private final ModelMapper modelMapper;


    public EventDto.EventResponse getEvent(UUID id) {
        var eventEntity = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        var eventDto = modelMapper.map(eventEntity, EventDto.EventResponse.class);
        return eventDto;
    }

    // finish this
    public List<EventDto.EventResponse> getEvents() {
        List<EventDto.EventResponse> eventDtoList = new ArrayList<>();
        eventRepository.findAll().forEach(eventEntity -> {
            var eventDto = modelMapper.map(eventEntity, EventDto.EventResponse.class);
        });
        return eventDtoList;
    }


}
