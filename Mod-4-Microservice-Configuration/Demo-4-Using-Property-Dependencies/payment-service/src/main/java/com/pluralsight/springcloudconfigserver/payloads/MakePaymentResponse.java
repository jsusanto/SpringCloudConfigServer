package com.pluralsight.springcloudconfigserver.payloads;

import java.math.BigDecimal;

public record MakePaymentResponse(BigDecimal total) {
}