package com.iscte.mei.ads.schedules.api.services;

import com.iscte.mei.ads.schedules.api.repositories.SchedulesRepository;
import org.springframework.stereotype.Service;

@Service
public class SchedulesService {

    private final SchedulesRepository repository;

    public SchedulesService(SchedulesRepository repository) {
        this.repository = repository;
    }
}
