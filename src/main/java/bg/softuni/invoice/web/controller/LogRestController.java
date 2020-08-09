package bg.softuni.invoice.web.controller;

import bg.softuni.invoice.model.entity.Log;
import bg.softuni.invoice.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost")
@RestController
@RequestMapping("/api/log")
public class LogRestController {

    private final LogRepository logRepository;

    @Autowired
    public LogRestController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROOT')")
    public List<Log> getLogs() {
        return this.logRepository.findAll();
    }

    @GetMapping("/{logId}")
    @PreAuthorize("hasRole('ROOT')")
    public ResponseEntity<Log> getLog(@PathVariable String logId) {
        Optional<Log> optionalLog = this.logRepository.findById(logId);

        return optionalLog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
