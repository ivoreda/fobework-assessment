package com.muzan.musicbookingapp.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    private String name;
    private String description;
    private String location;
    private String type;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private Artist createdBy;

    private Integer ticketsAvailable;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Double ticketPrice;

    @Column(columnDefinition = "boolean default false")
    private Boolean isPublished;

    private String imageUrl;

    @Column(columnDefinition = "integer default 0")
    private Integer ticketsSold;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
