package com.solidice.rsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication()
@ComponentScan(excludeFilters = {
    @ComponentScan.Filter(
        type = FilterType.REGEX,
        pattern = "com.solidice.rsocket.generated.proto.Blocking.*"
    )
})
public class SpringBootRSocketServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRSocketServerApplication.class, args);
    }
}
