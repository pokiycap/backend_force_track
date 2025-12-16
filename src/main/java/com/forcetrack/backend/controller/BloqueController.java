package com.forcetrack.backend.controller;

import com.forcetrack.backend.entity.Bloque;
import com.forcetrack.backend.repository.BloqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bloque")
public class BloqueController {

    @Autowired
    private BloqueRepository bloqueRepository;

    @GetMapping
    public List<Bloque> getBloques(@RequestParam(required = false) Integer usuario_id) {
        if (usuario_id != null) {
            return bloqueRepository.findByUsuarioId(usuario_id);
        }
        return bloqueRepository.findAll();
    }

    @PostMapping
    public Bloque createBloque(@RequestBody Bloque bloque) {
        return bloqueRepository.save(bloque);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Bloque> getBloque(@PathVariable Integer id) {
        return bloqueRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint compatible con el cliente Android: GET /api/bloque/usuario/{usuarioId}
    @GetMapping("/usuario/{usuarioId}")
    public List<Bloque> getBloquesByUsuarioPath(@PathVariable Integer usuarioId) {
        return bloqueRepository.findByUsuarioId(usuarioId);
    }

    // Actualizar bloque (PUT) — cliente usa PUT, mantenemos lógica simple
    @PutMapping("/{id}")
    public ResponseEntity<Bloque> updateBloque(@PathVariable Integer id, @RequestBody Bloque bloqueDetails) {
        return bloqueRepository.findById(id)
                .map(b -> {
                    if (bloqueDetails.getNombre() != null) b.setNombre(bloqueDetails.getNombre());
                    if (bloqueDetails.getCategoria() != null) b.setCategoria(bloqueDetails.getCategoria());
                    return ResponseEntity.ok(bloqueRepository.save(b));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Borrar bloque (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBloqueById(@PathVariable Integer id) {
        if (bloqueRepository.existsById(id)) {
            bloqueRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
