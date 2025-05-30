package com.eams.controller;

import com.eams.dto.MaintenanceEventRequest;
import com.eams.entity.MaintenanceEvent;
import com.eams.service.MaintenanceEventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance-events")
public class MaintenanceEventController {

    private final MaintenanceEventService service;

    public MaintenanceEventController(MaintenanceEventService service) {
        this.service = service;
    }

    // POST /api/maintenance-events
    @PostMapping
    public ResponseEntity<MaintenanceEvent> logEvent(@Valid @RequestBody MaintenanceEventRequest request) {
        MaintenanceEvent savedEvent = service.logEvent(request);
        return ResponseEntity.ok(savedEvent);
    }

    // GET /api/maintenance-events
    @GetMapping
    public ResponseEntity<List<MaintenanceEvent>> getAllEvents() {
        return ResponseEntity.ok(service.getAllEvents());
    }

    // GET /api/maintenance-events/{id}
    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceEvent> getEventById(@PathVariable Long id) {
        MaintenanceEvent event = service.getEventById(id);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }
}
