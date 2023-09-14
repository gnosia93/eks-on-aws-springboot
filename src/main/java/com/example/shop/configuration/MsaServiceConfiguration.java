package com.example.shop.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix="msa.service.endpoint")
public class MsaServiceConfiguration {
        private String prod;
        private String point;
        private String benefit;
}

