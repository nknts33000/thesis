package com.example.platform.searching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AdvertIndexingInitializer implements ApplicationRunner {

    private final AdvertService advertService;

    @Autowired
    public AdvertIndexingInitializer(AdvertService advertService) {
        this.advertService = advertService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Index all existing adverts on application startup
        advertService.indexAllAdverts();
    }
}
