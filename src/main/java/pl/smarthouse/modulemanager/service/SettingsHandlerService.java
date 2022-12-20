package pl.smarthouse.modulemanager.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.smarthouse.modulemanager.model.dao.SettingsDao;
import pl.smarthouse.modulemanager.model.dto.SettingsDto;
import pl.smarthouse.modulemanager.repository.reactive.ReactiveSettingsRepository;
import pl.smarthouse.modulemanager.utils.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class SettingsHandlerService {
  private static final String LOG_SUCCESS_ON_ACTION =
      "Success action: {} for moduleType: {}, MAC-address: {}";
  private static final String LOG_ERROR_ON_ACTION =
      "Error action:{} for moduleType: {}, MAC-address: {}, reason:{}";
  private static final String LOG_ERROR_ON_FIND_ALL = "Error action:{}, reason:{}";
  private static final String ACTION_SAVE = "saveSettings";
  private static final String ACTION_FIND_ALL = "findAll";
  private static final String ACTION_GET_BY_MAC = "getIPbyMacAddress";

  ReactiveSettingsRepository reactiveSettingsRepository;

  public Mono<SettingsDto> saveSettings(final SettingsDto settingsDto, final String hostAddress) {
    return reactiveSettingsRepository
        .deleteByMacAddress(settingsDto.getMacAddress())
        .then(Mono.just(settingsDto))
        .map(settings -> ModelMapper.toSettingsDao(settings, hostAddress))
        .flatMap(settingsDao -> reactiveSettingsRepository.save(settingsDao))
        .map(ModelMapper::toSettingsDto)
        .doOnSuccess(
            ignore ->
                log.info(
                    LOG_SUCCESS_ON_ACTION,
                    ACTION_SAVE,
                    settingsDto.getModuleType(),
                    settingsDto.getMacAddress()))
        .doOnError(
            throwable ->
                log.error(
                    LOG_ERROR_ON_ACTION,
                    ACTION_SAVE,
                    settingsDto.getModuleType(),
                    settingsDto.getModuleType(),
                    throwable.getMessage()));
  }

  public Flux<SettingsDto> findAll() {
    return reactiveSettingsRepository
        .findAll()
        .map(ModelMapper::toSettingsDto)
        .doOnError(
            throwable -> log.error(LOG_ERROR_ON_FIND_ALL, ACTION_FIND_ALL, throwable.getMessage()));
  }

  public Mono<String> getIPbyMacAddress(final String macAddress) {
    return reactiveSettingsRepository
        .findIPByMacAddress(macAddress)
        .doOnSuccess(
            settingsDao ->
                log.info(
                    LOG_SUCCESS_ON_ACTION,
                    ACTION_GET_BY_MAC,
                    settingsDao.getModuleType(),
                    settingsDao.getMacAddress()))
        .doOnError(
            throwable ->
                log.error(
                    LOG_ERROR_ON_ACTION,
                    ACTION_GET_BY_MAC,
                    null,
                    macAddress,
                    throwable.getMessage()))
        .map(SettingsDao::getIpAddress);
  }
}
