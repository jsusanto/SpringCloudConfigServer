package com.pluralsight.springcloudconfigserver.payloads;

import java.math.BigDecimal;
import java.util.UUID;

public class MakePaymentRequest {
    private UUID bookingId;
    private BigDecimal paymentAmount;

    public UUID getBookingId() {
        return bookingId;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }
}