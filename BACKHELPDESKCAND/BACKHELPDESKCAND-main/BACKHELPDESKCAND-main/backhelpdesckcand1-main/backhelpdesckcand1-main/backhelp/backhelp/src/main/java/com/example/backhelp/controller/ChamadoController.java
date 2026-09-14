package com.example.backhelp.controller;

import com.example.backhelp.dto.ChamadoRequestDTO;
import com.example.backhelp.dto.ChamadoResponseDTO;
import com.example.backhelp.dto.DashboardDTO;
import com.example.backhelp.model.Perfil;
import com.example.backhelp.model.StatusChamado;
import com.example.backhelp.model.Urgencia;
import com.example.backhelp.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/chamados")
public class ChamadoController {

    private final ChamadoService chamadoService;

    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @PostMapping
    public ResponseEntity<ChamadoResponseDTO> criarChamado(@Valid @RequestBody ChamadoRequestDTO dto) {
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(chamadoService.criarChamado(dto, emailUsuario));
    }

    @PostMapping(value = "/{id}/anexo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ChamadoResponseDTO> uploadAnexo(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(chamadoService.salvarAnexo(id, file));
    }

    @GetMapping("/{id}/anexo")
    public ResponseEntity<Resource> buscarAnexo(@PathVariable Long id) {
        return chamadoService.carregarAnexo(id);
    }

    @GetMapping
    public ResponseEntity<List<ChamadoResponseDTO>> listarTodos(
            @RequestParam(required = false) StatusChamado status,
            @RequestParam(required = false) Perfil nivelAtendimento,
            @RequestParam(required = false) Urgencia urgencia) {
        return ResponseEntity.ok(chamadoService.listarComFiltros(status, nivelAtendimento, urgencia));
    }

    @PutMapping("/{id}/escalonar")
    @PreAuthorize("hasAnyRole('ATENDENTE_N1', 'ATENDENTE_N2', 'ATENDENTE_N3')")
    public ResponseEntity<ChamadoResponseDTO> escalonarChamado(
            @PathVariable Long id,
            @RequestParam Perfil novoNivel) {
        return ResponseEntity.ok(chamadoService.escalonarChamado(id, novoNivel));
    }

    @PutMapping("/{id}/atender")
    @PreAuthorize("hasAnyRole('ATENDENTE_N1', 'ATENDENTE_N2', 'ATENDENTE_N3')")
    public ResponseEntity<ChamadoResponseDTO> atenderEConverter(
            @PathVariable Long id,
            @RequestParam Long atendenteId,
            @RequestParam StatusChamado status,
            @RequestBody(required = false) String solucao) {
        return ResponseEntity.ok(chamadoService.atenderEConverter(id, atendenteId, status, solucao));
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('ATENDENTE_N1', 'ATENDENTE_N2', 'ATENDENTE_N3', 'SETOR_ADMINISTRATIVO')")
    public ResponseEntity<DashboardDTO> obterDashboard() {
        return ResponseEntity.ok(chamadoService.obterMetricsDashboard());
    }
}