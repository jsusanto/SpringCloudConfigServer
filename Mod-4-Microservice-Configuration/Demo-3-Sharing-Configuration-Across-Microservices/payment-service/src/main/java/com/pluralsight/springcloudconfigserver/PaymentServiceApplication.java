package com.pluralsight.springcloudconfigserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PaymentServiceApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(PaymentServiceApplication.class, args);
	}

}
