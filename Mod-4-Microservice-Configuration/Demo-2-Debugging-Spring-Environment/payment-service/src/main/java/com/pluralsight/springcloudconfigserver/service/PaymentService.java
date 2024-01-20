package com.pluralsight.springcloudconfigserver.service;

import com.pluralsight.springcloudconfigserver.configurationproperties.FeatureTogglesConfigurationProperties;
import com.pluralsight.springcloudconfigserver.exceptions.CreditCheckException;
import com.pluralsight.springcloudconfigserver.exceptions.FraudException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import static java.math.RoundingMode.HALF_UP;

@Service
public class PaymentService {
    private final BigDecimal salesTaxPercentage;
    private final FeatureTogglesConfigurationProperties features;

    public PaymentService(
            @Value("${sales-tax-percentage}") final BigDecimal salesTaxPercentage,
            FeatureTogglesConfigurationProperties features) {
        this.salesTaxPercentage = salesTaxPercentage;
        this.features = features;
    }

    public BigDecimal makePayment(final UUID bookingId, final BigDecimal paymentAmount) {

        final BigDecimal taxToAdd = paymentAmount.divide(new BigDecimal(100), HALF_UP)
                .multiply(salesTaxPercentage);
        final BigDecimal paymentWithTax = paymentAmount.add(taxToAdd);

        if (features.isFraudDetection()) {
            detectFraud();
        }

        if (features.isCreditCheck()) {
            creditCheck();
        }

        return paymentWithTax;
    }

    private void detectFraud() {
        if (new Random().nextBoolean()) {
            throw new FraudException();
        }
    }

    private void creditCheck() {
        if (new Random().nextBoolean()) {
            throw new CreditCheckException();
        }
    }
}
