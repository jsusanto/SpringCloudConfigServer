Introduction to Spring Cloud Config Service
===========================================
SETTING UP THE CONFIG SERVER
===========================================

Prerequisites:
- Java 17
- Install Gradle

STEP 1. Go to https://start.spring.io/

At the time of creating this project.
Project: Gradle - Groovy
Language: Java
Spring Boot: 3.1.8
Group: com.pluralsight
Artifact: spring-cloud-config-server
Name: spring-cloud-config-server
Packaging: Jar
Java: 17

Add dependency: Config Server - Spring Cloud Config

STEP 2.  Enable the config server by adding the annotation from SpringCloudConfigServerApplication.java

@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConfigServerApplication.class, args);
	}

}

STEP 3. Try to run the main program and identify what's happening.

ERROR:
Description:

Invalid config server configuration.

Action:

Why? Because we haven't set up the configuration yet.

STEP 4. Remove the current default application.properties and create
application.yml

Add the following configuration:

server.port: 8888
spring.cloud.config.server.git.uri: https://github.com/jsusanto/spring-cloud-config-server-repo.git

STEP 5. Run the main program and identify the log.

It's running successfully.

2024-01-20T01:18:18.346+11:00  INFO 7180 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8888 (http)
2024-01-20T01:18:18.363+11:00  INFO 7180 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2024-01-20T01:18:18.363+11:00  INFO 7180 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.18]
2024-01-20T01:18:18.612+11:00  INFO 7180 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2024-01-20T01:18:18.615+11:00  INFO 7180 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 2962 ms
2024-01-20T01:18:20.860+11:00  INFO 7180 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-20T01:18:21.624+11:00  INFO 7180 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8888 (http) with context path ''
2024-01-20T01:18:22.876+11:00  INFO 7180 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-20T01:18:22.904+11:00  INFO 7180 --- [           main] c.p.s.SpringCloudConfigServerApplication : Started SpringCloudConfigServerApplication in 9.197 seconds (process running for 9.698)


STEP 6. Use POSTMAN CLIENT API to check the current service.

GET: http://localhost:8888/payment-service.yml
expected output
    salesTaxPercentage: 17

GET: http://localhost:8888/user-service.yml
expected output
    countryDenyList:
    - evilSanctionedCountry