package br.com.fiap.globalsolution2025.repository;

import br.com.fiap.globalsolution2025.entity.DadosSensor;
import br.com.fiap.globalsolution2025.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DadosSensorRepository  extends JpaRepository<DadosSensor, Long> {
        List<DadosSensor> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);

        List<DadosSensor> findTop10ByOrderByDataHoraDesc();

        @Query("SELECT AVG(d.temperatura) FROM DadosSensor d WHERE d.dataHora BETWEEN :inicio AND :fim")
        Double mediaTemperatura(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

        @Query("SELECT AVG(d.umidade) FROM DadosSensor d WHERE d.dataHora BETWEEN :inicio AND :fim")
        Double mediaUmidade(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

        List<DadosSensor> findByUsuario(Usuario usuario);
}
