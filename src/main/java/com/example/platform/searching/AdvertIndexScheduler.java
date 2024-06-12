package com.example.platform.searching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AdvertIndexScheduler {

    private final AdvertService advertService;

    @Autowired
    public AdvertIndexScheduler(AdvertService advertService) {
        this.advertService = advertService;
    }

    @Scheduled(fixedRate = 3600000) // Run every hour
    public void indexNewAdverts() {
        advertService.indexAllAdverts();
    }
}
