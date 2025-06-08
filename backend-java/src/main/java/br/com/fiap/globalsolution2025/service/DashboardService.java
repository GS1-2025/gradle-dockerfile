package br.com.fiap.globalsolution2025.service;

import br.com.fiap.globalsolution2025.entity.DadosSensor;
import br.com.fiap.globalsolution2025.repository.DadosSensorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class DashboardService {

    private final DadosSensorRepository repository;

    public DashboardService(DadosSensorRepository repository) {
        this.repository = repository;
    }

    public List<DadosSensor> getLatestReadings() {
        return repository.findTop10ByOrderByDataHoraDesc();
    }

    public Double getAverageTemperatureToday() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1).minusSeconds(1);
        return repository.mediaTemperatura(start, end);
    }

    public Double getAverageHumidityToday() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1).minusSeconds(1);
        return repository.mediaUmidade(start, end);
    }

    public List<DadosSensor> getReadingsBetween(LocalDateTime start, LocalDateTime end) {
        return repository.findByDataHoraBetween(start, end);
    }
}
