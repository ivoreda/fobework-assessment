package com.muzan.musicbookingapp.repository;

import com.muzan.musicbookingapp.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IPaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByReference(String reference);
}