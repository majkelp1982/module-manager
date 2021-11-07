package pl.smarthouse.modulemanager.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.smarthouse.modulemanager.model.SettingsDao;
import pl.smarthouse.modulemanager.model.SettingsDto;
import pl.smarthouse.modulemanager.repository.reactive.ReactiveSettingsRepository;
import pl.smarthouse.modulemanager.utils.DateTimeUtils;
import pl.smarthouse.modulemanager.utils.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SettingsHandlerService {

  ReactiveSettingsRepository reactiveSettingsRepository;

  public Mono<SettingsDto> saveSettings(final SettingsDto settingsDto, final String hostAddress) {
    final SettingsDao settingsDao = ModelMapper.toSettingsDao(settingsDto);
    settingsDao.setUpdateTimestamp(DateTimeUtils.toInstant(LocalDateTime.now()));
    settingsDao.setIpAddress(hostAddress);
    return reactiveSettingsRepository.save(settingsDao);
  }

  public Flux<SettingsDao> findAll() {
    return reactiveSettingsRepository.findAll();
  }

  public Mono<String> getIPbyMacAddress(final String macAddress) {
    return reactiveSettingsRepository.findIPByMacAddress(macAddress);
  }
}
