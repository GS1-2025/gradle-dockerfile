package br.com.fiap.globalsolution2025.repository;

import br.com.fiap.globalsolution2025.entity.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
}
