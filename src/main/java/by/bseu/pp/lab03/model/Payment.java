package by.bseu.pp.lab03.model;

import java.time.LocalDate;

/**
 * Represents a payment connected with an order.
 */
public class Payment {
    private final String paymentId;
    private final double amount;
    private final LocalDate date;
    private PaymentStatus status;

    public Payment(String paymentId, double amount, LocalDate date) {
        if (paymentId == null || paymentId.isBlank()) {
            throw new IllegalArgumentException("Payment ID cannot be null or blank");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        this.paymentId = paymentId;
        this.amount = amount;
        this.date = date;
        this.status = PaymentStatus.CREATED;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void markSuccessful() {
        if (status != PaymentStatus.CREATED) {
            throw new IllegalStateException("Payment has already been processed");
        }

        status = PaymentStatus.SUCCESSFUL;
    }

    public void markFailed() {
        if (status != PaymentStatus.CREATED) {
            throw new IllegalStateException("Payment has already been processed");
        }

        status = PaymentStatus.FAILED;
    }

    public boolean isSuccessful() {
        return status == PaymentStatus.SUCCESSFUL;
    }
}