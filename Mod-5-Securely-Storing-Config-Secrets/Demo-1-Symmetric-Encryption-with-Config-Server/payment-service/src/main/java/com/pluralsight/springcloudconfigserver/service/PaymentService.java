package com.pluralsight.springcloudconfigserver.service;

import com.pluralsight.springcloudconfigserver.configurationproperties.FeatureTogglesConfigurationProperties;
import com.pluralsight.springcloudconfigserver.configurationproperties.RefreshableConfig;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static java.math.RoundingMode.HALF_UP;

@Service
public class PaymentService {
    private final RefreshableConfig refreshableConfig;
    private final FeatureTogglesConfigurationProperties features;

    public PaymentService(
            RefreshableConfig refreshableConfig,
            FeatureTogglesConfigurationProperties features) {
        this.refreshableConfig = refreshableConfig;
        this.features = features;
    }

    public BigDecimal makePayment(final UUID bookingId, final BigDecimal paymentAmount) {

        final BigDecimal taxToAdd = paymentAmount.divide(new BigDecimal(100), HALF_UP)
                .multiply(refreshableConfig.getSalesTaxPercentage());
        final BigDecimal paymentWithTax = paymentAmount.add(taxToAdd);

        return paymentWithTax;
    }
}
