package br.com.fiap.globalsolution2025.repository;

import br.com.fiap.globalsolution2025.entity.PulseiraRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorRepository extends JpaRepository<PulseiraRequest, Long> {
    Optional<PulseiraRequest> findByDeviceToken(String deviceToken);
}
