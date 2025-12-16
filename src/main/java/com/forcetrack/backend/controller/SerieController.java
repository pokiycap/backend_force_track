package com.forcetrack.backend.controller;

import com.forcetrack.backend.entity.Serie;
import com.forcetrack.backend.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/serie")
public class SerieController {

    @Autowired
    private SerieRepository serieRepository;

    @GetMapping
    public List<Serie> getSeries(@RequestParam(required = false) Integer ejercicio_id) {
        if (ejercicio_id != null) {
            return serieRepository.findByEjercicioId(ejercicio_id);
        }
        return serieRepository.findAll();
    }

    // Compatible con cliente Android: GET /api/serie/ejercicio/{ejercicioId}
    @GetMapping("/ejercicio/{ejercicioId}")
    public List<Serie> getSeriesByEjercicioPath(@PathVariable Integer ejercicioId) {
        return serieRepository.findByEjercicioId(ejercicioId);
    }

    @PostMapping
    public Serie createSerie(@RequestBody Serie serie) {
        return serieRepository.save(serie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serie> getSerie(@PathVariable Integer id) {
        return serieRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Serie> updateSerie(@PathVariable Integer id, @RequestBody Serie serieDetails) {
        return serieRepository.findById(id)
                .map(serie -> {
                    if (serieDetails.getPeso() != null) serie.setPeso(serieDetails.getPeso());
                    if (serieDetails.getRepeticiones() != null) serie.setRepeticiones(serieDetails.getRepeticiones());
                    if (serieDetails.getRir() != null) serie.setRir(serieDetails.getRir());
                    if (serieDetails.getCompletada() != null) serie.setCompletada(serieDetails.getCompletada());
                    return ResponseEntity.ok(serieRepository.save(serie));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Cliente usa PUT para actualizar series
    @PutMapping("/{id}")
    public ResponseEntity<Serie> replaceSerie(@PathVariable Integer id, @RequestBody Serie serieDetails) {
        return serieRepository.findById(id)
                .map(serie -> {
                    serie.setPeso(serieDetails.getPeso());
                    serie.setRepeticiones(serieDetails.getRepeticiones());
                    serie.setRir(serieDetails.getRir());
                    serie.setCompletada(serieDetails.getCompletada());
                    return ResponseEntity.ok(serieRepository.save(serie));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSerie(@PathVariable Integer id) {
        if (serieRepository.existsById(id)) {
            serieRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
