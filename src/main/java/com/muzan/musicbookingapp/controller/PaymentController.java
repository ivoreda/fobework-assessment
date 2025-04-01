package com.muzan.musicbookingapp.controller;

import com.muzan.musicbookingapp.dto.PaymentDto;
import com.muzan.musicbookingapp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/events/{eventId}")
    public Mono<ResponseEntity<PaymentDto.InitiatePaymentResponse>> initiateEventPayment(
            @PathVariable UUID eventId) {

        return paymentService.initiateEventPayment(eventId)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/verify/{reference}")
    public Mono<ResponseEntity<PaymentDto.VerifyPaymentResponse>> verifyPayment(
            @PathVariable String reference) {

        return paymentService.verifyPayment(reference)
                .map(ResponseEntity::ok);
    }
}