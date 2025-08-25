package com.microdiab.Mpatient.configurations;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mes-configs")
@RefreshScope
public class ApplicationPropertiesConfiguration {

    private int limitDePatients;

    public int getLimitDePatients() {
        return limitDePatients;
    }

    public void setLimitDePatients(int limitDePatients) {
        this.limitDePatients = limitDePatients;
    }

}
