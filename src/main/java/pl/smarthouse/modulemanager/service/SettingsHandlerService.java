package pl.smarthouse.modulemanager.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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
  private static final String ACTION_SAVE = "saveSettings";
  private static final String ACTION_FIND_ALL = "findAll";
  private static final String ACTION_GET_BY_MAC = "getIPbyMacAddress";

  ReactiveSettingsRepository reactiveSettingsRepository;

  public Mono<SettingsDto> saveSettings(final SettingsDto settingsDto, final String hostAddress) {
    return reactiveSettingsRepository
        .findByMacAddress(settingsDto.getMacAddress())
        .map(settingsDao -> settingsDao.getId())
        .switchIfEmpty(Mono.defer(() -> Mono.just(ObjectId.get().toString())))
        .map(id -> ModelMapper.toSettingsDao(id, settingsDto, hostAddress))
        .flatMap(settingsDao -> reactiveSettingsRepository.save(settingsDao))
        .map(ModelMapper::toSettingsDto)
        .doOnSuccess(ignore -> log.info(LOG_SUCCESS_ON_ACTION, ACTION_SAVE, settingsDto))
        .doOnError(
            throwable ->
                log.error(LOG_ERROR_ON_ACTION, ACTION_SAVE, settingsDto, throwable.getMessage()));
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
        .doOnSuccess(settingsDao -> log.info(LOG_SUCCESS_ON_ACTION, ACTION_GET_BY_MAC, settingsDao))
        .doOnError(
            throwable ->
                log.error(
                    LOG_ERROR_ON_ACTION,
                    ACTION_GET_BY_MAC,
                    String.format("MAC address: %s", macAddress),
                    throwable.getMessage()))
        .map(ModelMapper::toSettingsDto);
  }
}
