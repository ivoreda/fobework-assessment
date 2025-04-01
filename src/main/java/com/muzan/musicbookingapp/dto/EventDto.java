package com.muzan.musicbookingapp.dto;

import com.muzan.musicbookingapp.model.EventType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

public class EventDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateEventRequest {
        @NotBlank(message = "Event name is required")
        private String name;

        @NotBlank(message = "Event description is required")
        private String description;

        @NotBlank(message = "Event location is required")
        private String location;

        @NotNull(message = "Event type is required")
        private EventType type;

        @NotNull(message = "Number of available tickets is required")
        @Min(value = 1, message = "At least one ticket must be available")
        private Integer ticketsAvailable;

        @NotNull(message = "Event start time is required")
        private LocalDateTime startTime;

        @NotNull(message = "Event end time is required")
        private LocalDateTime endTime;

        @NotNull(message = "Ticket price is required")
        @Min(value = 0, message = "Ticket price cannot be negative")
        private Double ticketPrice;

        private Boolean isPublished = false;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateEventRequest {
        private String name;
        private String description;
        private String location;
        private EventType type;
        private Integer ticketsAvailable;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Double ticketPrice;
        private Boolean isPublished;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventResponse {
        private UUID id;
        private String name;
        private String description;
        private String location;
        private String type;
        private ArtistDto createdBy;
        private Integer ticketsAvailable;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Double ticketPrice;
        private Boolean isPublished;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}