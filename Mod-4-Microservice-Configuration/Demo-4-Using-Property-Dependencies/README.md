# spring-cloud-config-3-fundamentals
Module 4: Microservices Configuration

Demo: Using Property Dependencies

- Configuring a database connections for our microservices.
- Removing property duplication using property dependencies and inheritance.
- Verifying our configuring using Spring Actuator.

STEP 1. In the git repo configuration, application.yml
We want to set up a common postgresql url.

management.endpoints.web.exposure.include: '*'

spring:
  datasource:
    url: jdbc:postgresql://${spring.datasource.ext.host}:5432/${spring.datasource.ext.database-name}
    username: postgres
    password: password
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    
STEP 2. Run the SpringCloudConfigServerApplication

STEP 3. Run the SpringCloudConfigUserServiceApplication and set the environment variables as the following

-Dspring.profiles.active=staging,uk

2024-01-22 09:33:29.538  INFO 26508 --- [           main] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.PostgreSQLDialect
2024-01-22 09:33:29.885  INFO 26508 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
2024-01-22 09:33:29.897  INFO 26508 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2024-01-22 09:33:29.960  WARN 26508 --- [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2024-01-22 09:33:32.157  INFO 26508 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-22 09:33:32.198  INFO 26508 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 15 endpoint(s) beneath base path '/actuator'
2024-01-22 09:33:32.266  INFO 26508 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8081 (http) with context path ''
2024-01-22 09:33:33.484  INFO 26508 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-22 09:33:33.499  INFO 26508 --- [           main] .SpringCloudConfigUserServiceApplication : Started SpringCloudConfigUserServiceApplication in 15.577 seconds (JVM running for 16.042)


STEP 4. Run the SpringCloudConfigPaymentServiceApplication and set the environment variables as the follwing

-Dspring.profiles.active=development,uk

2024-01-22 09:36:20.358  INFO 6732 --- [           main] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.PostgreSQLDialect
2024-01-22 09:36:20.824  INFO 6732 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
2024-01-22 09:36:20.850  INFO 6732 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2024-01-22 09:36:20.974  WARN 6732 --- [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2024-01-22 09:36:23.661  INFO 6732 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-22 09:36:23.720  INFO 6732 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 15 endpoint(s) beneath base path '/actuator'
2024-01-22 09:36:23.839  INFO 6732 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2024-01-22 09:36:25.120  INFO 6732 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-22 09:36:25.148  INFO 6732 --- [           main] c.p.s.PaymentServiceApplication          : Started PaymentServiceApplication in 16.268 seconds (JVM running for 16.897)


STEP 5. We want to debug what's going on.

Open the POSTMAN and query: http://localhost:8080/actuator/env/spring.datasource.url

[GET], now we analyse what's going on:
- We can see the active profiles are correct.
- Configurations are set up properly.

{
    "property": {
        "source": "configserver:https://github.com/jsusanto/spring-cloud-config-server-repo.git/application.yml",
        "value": "jdbc:postgresql://development-host:5432/payments"
    },
    "activeProfiles": [
        "development",
        "uk"
    ],
  
        {
            "name": "configserver:https://github.com/jsusanto/spring-cloud-config-server-repo.git/application.yml",
            "property": {
                "value": "jdbc:postgresql://development-host:5432/payments",
                "origin": "Config Server https://github.com/jsusanto/spring-cloud-config-server-repo.git/application.yml:5:10"
            }
        },
      
    ]
}

STEP 6. 