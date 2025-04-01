package com.muzan.musicbookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

public class PaymentDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InitiatePaymentRequest {
        private String email;
        private Double amount;
        private String callbackUrl;
        private String reference;
        private Map<String, Object> metadata;
    }

    @Data
    @AllArgsConstructor
    public static class InitiatePaymentResponse {
        private boolean status;
        private String message;
        private String authorizationUrl;
        private String reference;
    }

    @Data
    @AllArgsConstructor
    public static class VerifyPaymentResponse {
        private boolean status;
        private String message;
        private String paymentStatus;
        private String reference;
        private Double amount;
    }
}