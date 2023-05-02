package pl.smarthouse.modulemanager.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pl.smarthouse.modulemanager.model.dto.ModuleSettingsDto;
import pl.smarthouse.modulemanager.model.dto.SettingsDto;
import pl.smarthouse.modulemanager.repository.reactive.ReactiveSettingsRepository;
import pl.smarthouse.modulemanager.service.exceptiions.SettingsNotFoundException;
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

  public Mono<SettingsDto> saveSettings(
      final ModuleSettingsDto moduleSettingsDto, final String hostAddress) {
    return reactiveSettingsRepository
        .findByModuleMacAddress(moduleSettingsDto.getMacAddress())
        .map(
            settingsDao ->
                ModelMapper.updateSettingsDaoWithModuleSettings(
                    settingsDao, moduleSettingsDto, hostAddress))
        .switchIfEmpty(
            Mono.defer(
                () ->
                    Mono.just(
                        ModelMapper.toSettingsDao(
                            ObjectId.get().toString(), moduleSettingsDto, hostAddress))))
        .flatMap(settingsDao -> reactiveSettingsRepository.save(settingsDao))
        .map(ModelMapper::toSettingsDto)
        .doOnSuccess(ignore -> log.info(LOG_SUCCESS_ON_ACTION, ACTION_SAVE, moduleSettingsDto))
        .doOnError(
            throwable ->
                log.error(
                    LOG_ERROR_ON_ACTION, ACTION_SAVE, moduleSettingsDto, throwable.getMessage()));
  }

  public Flux<SettingsDto> findAll() {
    return reactiveSettingsRepository
        .findAll()
        .map(ModelMapper::toSettingsDto)
        .doOnError(
            throwable -> log.error(LOG_ERROR_ON_FIND_ALL, ACTION_FIND_ALL, throwable.getMessage()));
  }

  public Mono<SettingsDto> getByModuleMacAddress(final String macAddress) {
    return reactiveSettingsRepository
        .findByModuleMacAddress(macAddress)
        .switchIfEmpty(
            Mono.defer(
                () ->
                    Mono.error(
                        new SettingsNotFoundException(
                            String.format("Settings not found for mac address: %s", macAddress)))))
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

  public Mono<SettingsDto> updateServiceAddress(
      final String moduleMacAddress, final String serviceAddress) {
    return reactiveSettingsRepository
        .findByModuleMacAddress(moduleMacAddress)
        .switchIfEmpty(
            Mono.defer(
                () ->
                    Mono.error(
                        new NotFoundException(
                            String.format(
                                "Settings not found for mac address: %s", moduleMacAddress)))))
        .map(
            settingsDao -> {
              settingsDao.setServiceAddress(serviceAddress);
              return settingsDao;
            })
        .flatMap(settingsDao -> reactiveSettingsRepository.save(settingsDao))
        .map(ModelMapper::toSettingsDto)
        .doOnSuccess(
            ignore ->
                log.info(
                    LOG_SUCCESS_ON_ACTION,
                    String.format("updateServiceAddress with service address: %s", serviceAddress),
                    moduleMacAddress))
        .doOnError(
            throwable ->
                log.error(
                    LOG_ERROR_ON_ACTION,
                    String.format("updateServiceAddress with service address: %s", serviceAddress),
                    moduleMacAddress));
  }
}
