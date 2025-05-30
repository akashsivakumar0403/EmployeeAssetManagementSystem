package com.eams.service;
import com.eams.dto.MaintenanceEventRequest;
import com.eams.entity.MaintenanceEvent;
import com.eams.repository.MaintenanceEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceEventService {

    private final MaintenanceEventRepository repository;

    public MaintenanceEventService(MaintenanceEventRepository repository) {
        this.repository = repository;
    }

    public MaintenanceEvent logEvent(MaintenanceEventRequest request) {
        MaintenanceEvent event = new MaintenanceEvent();
        event.setEngineerName(request.getEngineerName());
        event.setNotes(request.getNotes());
        return repository.save(event);
    }

    public List<MaintenanceEvent> getAllEvents() {
        return repository.findAll();
    }

    public MaintenanceEvent getEventById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
