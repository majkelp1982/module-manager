package pl.smarthouse.modulemanager.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.smarthouse.modulemanager.model.dto.SettingsDto;
import pl.smarthouse.modulemanager.repository.reactive.ReactiveSettingsRepository;
import pl.smarthouse.modulemanager.utils.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class SettingsHandlerService {
  private static final String LOG_SUCCESS_ON_ACTION = "Success action: {} for module: {}";
  private static final String LOG_ERROR_ON_ACTION = "Error action:{} for module: {}, reason:{}";
  private static final String LOG_ERROR_ON_FIND_ALL = "Error action:{}, reason:{}";
  private static final String ACTION_UPDATE = "updateSettings";
  private static final String ACTION_FIND_ALL = "findAll";
  private static final String ACTION_GET_BY_MAC = "getIPbyMacAddress";

  ReactiveSettingsRepository reactiveSettingsRepository;

  public Mono<SettingsDto> updateSettings(
      final SettingsDto settingsDto, final @NonNull String hostAddress) {
    return Mono.just(ModelMapper.toSettingsDao(settingsDto, hostAddress))
        .flatMap(settingsDao -> reactiveSettingsRepository.updateSettings(settingsDao))
        .doOnNext(settingsDao -> log.info("Object returned: {}", settingsDao))
        .flatMap(ignore -> reactiveSettingsRepository.findByMacAddress(settingsDto.getMacAddress()))
        .map(ModelMapper::toSettingsDto)
        .doOnSuccess(ignore -> log.info(LOG_SUCCESS_ON_ACTION, ACTION_UPDATE, settingsDto))
        .doOnError(
            throwable ->
                log.error(LOG_ERROR_ON_ACTION, ACTION_UPDATE, settingsDto, throwable.getMessage()));
  }

  public Flux<SettingsDto> findAll() {
    return reactiveSettingsRepository
        .findAll()
        .map(ModelMapper::toSettingsDto)
        .doOnError(
            throwable -> log.error(LOG_ERROR_ON_FIND_ALL, ACTION_FIND_ALL, throwable.getMessage()));
  }

  public Mono<SettingsDto> getByMacAddress(final String macAddress) {
    return reactiveSettingsRepository
        .findByMacAddress(macAddress)
        .map(ModelMapper::toSettingsDto)
        .doOnSuccess(settingsDto -> log.info(LOG_SUCCESS_ON_ACTION, ACTION_GET_BY_MAC, settingsDto))
        .doOnError(
            throwable ->
                log.error(
                    LOG_ERROR_ON_ACTION,
                    ACTION_GET_BY_MAC,
                    String.format("MAC address: %s", macAddress),
                    throwable.getMessage()));
  }
}
