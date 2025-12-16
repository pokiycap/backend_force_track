package com.forcetrack.backend.controller;

import com.forcetrack.backend.entity.Ejercicio;
import com.forcetrack.backend.repository.EjercicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejercicio")
public class EjercicioController {

    @Autowired
    private EjercicioRepository ejercicioRepository;

    @GetMapping
    public List<Ejercicio> getEjercicios(@RequestParam(required = false) Integer dia_id) {
        if (dia_id != null) {
            return ejercicioRepository.findByDiaId(dia_id);
        }
        return ejercicioRepository.findAll();
    }

    // Compatible con cliente Android: GET /api/ejercicio/dia/{diaId}
    @GetMapping("/dia/{diaId}")
    public List<Ejercicio> getEjerciciosByDiaPath(@PathVariable Integer diaId) {
        return ejercicioRepository.findByDiaId(diaId);
    }

    @PostMapping
    public Ejercicio createEjercicio(@RequestBody Ejercicio ejercicio) {
        return ejercicioRepository.save(ejercicio);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ejercicio> getEjercicio(@PathVariable Integer id) {
        return ejercicioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ejercicio> updateEjercicio(@PathVariable Integer id, @RequestBody Ejercicio ejercicioDetails) {
        return ejercicioRepository.findById(id)
                .map(ejercicio -> {
                    if (ejercicioDetails.getNombre() != null) ejercicio.setNombre(ejercicioDetails.getNombre());
                    if (ejercicioDetails.getDescansoSegundos() != null) ejercicio.setDescansoSegundos(ejercicioDetails.getDescansoSegundos());
                    return ResponseEntity.ok(ejercicioRepository.save(ejercicio));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEjercicio(@PathVariable Integer id) {
        if (ejercicioRepository.existsById(id)) {
            ejercicioRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
