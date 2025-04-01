package com.muzan.musicbookingapp.service;

import com.muzan.musicbookingapp.dto.PaymentDto;
import com.muzan.musicbookingapp.model.Event;
import com.muzan.musicbookingapp.model.Payment;
import com.muzan.musicbookingapp.model.PaymentStatus;
import com.muzan.musicbookingapp.model.User;
import com.muzan.musicbookingapp.repository.IEventRepository;
import com.muzan.musicbookingapp.repository.IPaymentRepository;
import com.muzan.musicbookingapp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class PaymentService {

    private final MockPaystackService mockPaystackService;
    private final IPaymentRepository paymentRepository;
    private final IEventRepository eventRepository;
    private final UserService userService;

    @Value("${app.payment.callback-url}")
    private String callbackUrl;

    public Mono<PaymentDto.InitiatePaymentResponse> initiateEventPayment(UUID eventId) {
        User currentUser = userService.getCurrentUser();

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        String reference = "EVT_" + UUID.randomUUID().toString();

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("eventId", eventId.toString());
        metadata.put("userId", currentUser.getId().toString());

        Payment payment = Payment.builder()
                .amount(event.getTicketPrice())
                .reference(reference)
                .status(PaymentStatus.PENDING)
                .user(currentUser)
                .event(event)
                .createdAt(LocalDateTime.now())
                .build();
        paymentRepository.save(payment);

        PaymentDto.InitiatePaymentRequest request = PaymentDto.InitiatePaymentRequest.builder()
                .email(currentUser.getEmail())
                .amount(event.getTicketPrice())
                .reference(reference)
                .callbackUrl(callbackUrl)
                .metadata(metadata)
                .build();

        return mockPaystackService.initiateTransaction(request);
    }

    public Mono<PaymentDto.VerifyPaymentResponse> verifyPayment(String reference) {
        return mockPaystackService.verifyTransaction(reference)
                .doOnSuccess(response -> {
                    Payment payment = paymentRepository.findByReference(reference)
                            .orElseThrow(() -> new RuntimeException("Payment not found"));

                    payment.setStatus(PaymentStatus.APPROVED);
                    payment.setPaidAt(LocalDateTime.now());
                    paymentRepository.save(payment);

                    // Reduce available tickets
                    Event event = payment.getEvent();
                    event.setTicketsAvailable(event.getTicketsAvailable() - 1);
                    eventRepository.save(event);
                });
    }
}