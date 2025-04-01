package com.muzan.musicbookingapp.service;

import com.muzan.musicbookingapp.dto.PaymentDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class MockPaystackService {

    public Mono<PaymentDto.InitiatePaymentResponse> initiateTransaction(PaymentDto.InitiatePaymentRequest request) {
        PaymentDto.InitiatePaymentResponse response = new PaymentDto.InitiatePaymentResponse(
                true,
                "Mock payment initiated successfully",
                "https://mockpaystack.com/payment/" + UUID.randomUUID().toString(),
                request.getReference()
        );

        return Mono.just(response);
    }

    public Mono<PaymentDto.VerifyPaymentResponse> verifyTransaction(String reference) {
        PaymentDto.VerifyPaymentResponse response = new PaymentDto.VerifyPaymentResponse(
                true,
                "Mock verification successful",
                "success",
                reference,
                1000.00
        );

        return Mono.just(response);
    }
}