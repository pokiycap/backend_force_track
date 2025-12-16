package com.forcetrack.backend.controller;

import com.forcetrack.backend.entity.Dia;
import com.forcetrack.backend.repository.DiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dia")
public class DiaController {

    @Autowired
    private DiaRepository diaRepository;

    @GetMapping
    public List<Dia> getDias(@RequestParam(required = false) Integer bloque_id) {
        if (bloque_id != null) {
            return diaRepository.findByBloqueId(bloque_id);
        }
        return diaRepository.findAll();
    }

    // Endpoint compatible con cliente Android: GET /api/dia/bloque/{bloqueId}
    @GetMapping("/bloque/{bloqueId}")
    public List<Dia> getDiasByBloquePath(@PathVariable Integer bloqueId) {
        return diaRepository.findByBloqueId(bloqueId);
    }

    @PostMapping
    public Dia createDia(@RequestBody Dia dia) {
        return diaRepository.save(dia);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dia> getDia(@PathVariable Integer id) {
        return diaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Dia> updateDia(@PathVariable Integer id, @RequestBody Dia diaDetails) {
        return diaRepository.findById(id)
                .map(dia -> {
                    if (diaDetails.getNombre() != null) dia.setNombre(diaDetails.getNombre());
                    if (diaDetails.getNotas() != null) dia.setNotas(diaDetails.getNotas());
                    return ResponseEntity.ok(diaRepository.save(dia));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Cliente Android utiliza PUT para actualizar día: añadimos compatibilidad
    @PutMapping("/{id}")
    public ResponseEntity<Dia> replaceDia(@PathVariable Integer id, @RequestBody Dia diaDetails) {
        return diaRepository.findById(id)
                .map(dia -> {
                    dia.setNombre(diaDetails.getNombre());
                    dia.setNotas(diaDetails.getNotas());
                    dia.setFecha(diaDetails.getFecha());
                    dia.setNumeroSemana(diaDetails.getNumeroSemana());
                    return ResponseEntity.ok(diaRepository.save(dia));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDia(@PathVariable Integer id) {
        if (diaRepository.existsById(id)) {
            diaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
