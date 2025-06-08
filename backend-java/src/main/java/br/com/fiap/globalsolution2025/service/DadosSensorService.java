package br.com.fiap.globalsolution2025.service;

import br.com.fiap.globalsolution2025.dto.request.DadosPorSensorRequest;
import br.com.fiap.globalsolution2025.dto.request.DadosRequest;
import br.com.fiap.globalsolution2025.entity.DadosSensor;
import br.com.fiap.globalsolution2025.entity.PulseiraRequest;
import br.com.fiap.globalsolution2025.entity.Usuario;
import br.com.fiap.globalsolution2025.mapper.DadosMapper;
import br.com.fiap.globalsolution2025.repository.DadosSensorRepository;
import br.com.fiap.globalsolution2025.repository.SensorRepository;
import br.com.fiap.globalsolution2025.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DadosSensorService {

    private final DadosSensorRepository repository;
    private final DadosMapper mapper;
    private final UsuarioRepository usuarioRepository;
    private final SensorRepository sensorRepository;
    public DadosSensorService(DadosSensorRepository repository, DadosMapper mapper, UsuarioRepository usuarioRepository, SensorRepository sensorRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.usuarioRepository = usuarioRepository;
        this.sensorRepository = sensorRepository;
    }

    public DadosSensor getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dados não encontrados para o ID: " + id));
    }

    public List<DadosSensor> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Usuario usuario = getUsuarioPorEmail(email);

        List<DadosSensor> dados = repository.findByUsuario(usuario);

        return dados;
    }
    public Usuario getUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail_Email(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
    public DadosSensor saveDataComUsuario(DadosRequest request, Usuario usuario) {
        DadosSensor dados = mapper.toEntity(request);
        dados.setUsuario(usuario);
        return repository.save(dados);
    }

    public Usuario getUsuarioPorDeviceToken(String deviceToken) {
        Optional<PulseiraRequest> sensorOptional = sensorRepository.findByDeviceToken(deviceToken);
        return sensorOptional.get().getUsuario();
    }

    public DadosSensor saveDataComToken(DadosPorSensorRequest request) {
        Usuario usuario = getUsuarioPorDeviceToken(request.deviceToken());
        DadosSensor dados = mapper.toEntityPublic(request, usuario);
        return repository.save(dados);
    }









}
