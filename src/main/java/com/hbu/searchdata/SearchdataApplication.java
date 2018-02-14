package com.hbu.searchdata;

import com.hbu.searchdata.service.SearchService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class SearchdataApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchdataApplication.class, args);
    }
}
