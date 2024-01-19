package com.pluralsight.springcloudconfigserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

//STEP 2
@SpringBootApplication
@ConfigurationPropertiesScan  //This tells spring to scan classes annotated with configuration properties and binding
public class PaymentServiceApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(PaymentServiceApplication.class, args);
	}

}
