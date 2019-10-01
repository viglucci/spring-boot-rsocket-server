package com.solidice.springbootrsocketserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication()
@ComponentScan(excludeFilters = {
    @ComponentScan.Filter(
        type = FilterType.REGEX,
        pattern = "com.solidice.springbootrsocketserver.rpc.proto.Blocking.*"
    )
})
public class SpringBootRSocketServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRSocketServerApplication.class, args);
    }
}
