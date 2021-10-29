package com.iscte.mei.ads.schedules.api.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("schedules")
public class ImportScheduleProperties {

    public int maxParallel = 3;

}
