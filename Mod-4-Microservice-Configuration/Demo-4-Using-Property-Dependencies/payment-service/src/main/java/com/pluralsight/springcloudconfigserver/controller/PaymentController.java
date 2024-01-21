package com.pluralsight.springcloudconfigserver.controller;

import com.pluralsight.springcloudconfigserver.exceptions.CreditCheckException;
import com.pluralsight.springcloudconfigserver.exceptions.FraudException;
import com.pluralsight.springcloudconfigserver.payloads.FailureResponse;
import com.pluralsight.springcloudconfigserver.payloads.MakePaymentRequest;
import com.pluralsight.springcloudconfigserver.payloads.MakePaymentResponse;
import com.pluralsight.springcloudconfigserver.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping("/payments")
    public @ResponseBody MakePaymentResponse makePayment(@RequestBody final MakePaymentRequest makePaymentRequest) {
        final BigDecimal totalPayment = paymentService.makePayment(makePaymentRequest.getBookingId(), makePaymentRequest.getPaymentAmount());
        return new MakePaymentResponse(totalPayment);
    }

    @ExceptionHandler(FraudException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public FailureResponse handleFraudException() {
        return new FailureResponse("Fraud detected");
    }

    @ExceptionHandler(CreditCheckException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public FailureResponse handleCreditCheckException() {
        return new FailureResponse("Credit check failed");
    }
}
