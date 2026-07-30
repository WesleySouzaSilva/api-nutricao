package br.com.nutricao.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nutricao.domain.model.RegistroDiario;
import br.com.nutricao.infrastructure.persistence.RegistroDiarioRepository;

@Service
public class RegistroDiarioService {

    private final RegistroDiarioRepository registroDiarioRepository;

    public RegistroDiarioService(RegistroDiarioRepository registroDiarioRepository) {
        this.registroDiarioRepository = registroDiarioRepository;
    }

    @Transactional
    public RegistroDiario criar(RegistroDiario registro) {
        return registroDiarioRepository.save(registro);
    }

    public Optional<RegistroDiario> buscarPorId(Integer id) {
        return registroDiarioRepository.findById(id);
    }

    public Optional<RegistroDiario> buscarPorUsuarioEData(Integer usuarioId, LocalDate data) {
        return registroDiarioRepository.findByUsuarioIdAndData(usuarioId, data);
    }

    public List<RegistroDiario> buscarPorUsuarioOrdenado(Integer usuarioId) {
        return registroDiarioRepository.findByUsuarioIdOrderByDataDesc(usuarioId);
    }

    public List<RegistroDiario> buscarPorUsuarioEPeriodo(Integer usuarioId, LocalDate inicio, LocalDate fim) {
        return registroDiarioRepository.findByUsuarioIdAndDataBetweenOrderByDataDesc(usuarioId, inicio, fim);
    }

    public List<RegistroDiario> listar() {
        return registroDiarioRepository.findAll();
    }

    @Transactional
    public RegistroDiario atualizar(Integer id, RegistroDiario registro) {
        RegistroDiario existente = registroDiarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RegistroDiario nao encontrado: " + id));
        if (registro.getData() != null) existente.setData(registro.getData());
        if (registro.getCaloriasConsumidas() != null) existente.setCaloriasConsumidas(registro.getCaloriasConsumidas());
        if (registro.getProteinasConsumidas() != null) existente.setProteinasConsumidas(registro.getProteinasConsumidas());
        if (registro.getCarboidratosConsumidos() != null) existente.setCarboidratosConsumidos(registro.getCarboidratosConsumidos());
        if (registro.getGordurasConsumidas() != null) existente.setGordurasConsumidas(registro.getGordurasConsumidas());
        if (registro.getObservacoes() != null) existente.setObservacoes(registro.getObservacoes());
        if (registro.getUsuario() != null) existente.setUsuario(registro.getUsuario());
        return registroDiarioRepository.save(existente);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!registroDiarioRepository.existsById(id)) {
            throw new IllegalArgumentException("RegistroDiario nao encontrado: " + id);
        }
        registroDiarioRepository.deleteById(id);
    }
}
