package pl.smarthouse.modulemanager.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pl.smarthouse.modulemanager.model.dao.SettingsDao;
import pl.smarthouse.modulemanager.model.dto.ModuleSettingsDto;
import pl.smarthouse.modulemanager.repository.SettingsRepository;
import pl.smarthouse.modulemanager.service.exceptions.SettingsNotFoundException;
import pl.smarthouse.modulemanager.utils.ModelMapper;
import pl.smarthouse.sharedobjects.dto.ApplicationSettingsDto;
import pl.smarthouse.sharedobjects.dto.SettingsDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class SettingsService {
  private static final String LOG_SUCCESS_ON_ACTION = "Success action: {} for module: {}";
  private static final String LOG_ERROR_ON_ACTION = "Error action:{} for module: {}, reason:{}";
  private static final String LOG_ERROR_ON_FIND_ALL = "Error action:{}, reason:{}";
  private static final String ACTION_SAVE = "saveSettings";
  private static final String ACTION_FIND_ALL = "findAll";
  private static final String ACTION_GET_BY_MAC = "getIPbyMacAddress";

  SettingsRepository settingsRepository;

  public Mono<SettingsDto> saveSettings(
      final ModuleSettingsDto moduleSettingsDto, final String hostAddress) {
    return settingsRepository
        .findFirstByModuleMacAddress(moduleSettingsDto.getMacAddress())
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
        .flatMap(settingsDao -> settingsRepository.save(settingsDao))
        .map(ModelMapper::toSettingsDto)
        .doOnSuccess(ignore -> log.info(LOG_SUCCESS_ON_ACTION, ACTION_SAVE, moduleSettingsDto))
        .doOnError(
            throwable ->
                log.error(
                    LOG_ERROR_ON_ACTION, ACTION_SAVE, moduleSettingsDto, throwable.getMessage()));
  }

  public Mono<SettingsDto> saveApplicationSettings(
      final ApplicationSettingsDto applicationSettingsDto) {
    return settingsRepository
        .findFirstByType(applicationSettingsDto.getType())
        .map(SettingsDao::getId)
        .switchIfEmpty(Mono.defer(() -> Mono.just(ObjectId.get().toString())))
        .map(id -> ModelMapper.toSettingsDao(id, applicationSettingsDto))
        .flatMap(settingsDao -> settingsRepository.save(settingsDao))
        .map(ModelMapper::toSettingsDto)
        .doOnSuccess(
            savedSettingsDto ->
                log.info(LOG_SUCCESS_ON_ACTION, ACTION_SAVE, applicationSettingsDto))
        .doOnError(
            throwable ->
                log.error(
                    LOG_ERROR_ON_ACTION,
                    ACTION_SAVE,
                    applicationSettingsDto,
                    throwable.getMessage(),
                    throwable));
  }

  public Flux<SettingsDto> findAll() {
    return settingsRepository
        .findAll()
        .map(ModelMapper::toSettingsDto)
        .doOnError(
            throwable -> log.error(LOG_ERROR_ON_FIND_ALL, ACTION_FIND_ALL, throwable.getMessage()));
  }

  public Mono<SettingsDto> getByModuleMacAddress(final String macAddress) {
    return settingsRepository
        .findFirstByModuleMacAddress(macAddress)
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

  public Mono<SettingsDto> getByType(final String type) {
    return settingsRepository
        .findFirstByType(type)
        .switchIfEmpty(
            Mono.defer(
                () ->
                    Mono.error(
                        new SettingsNotFoundException(
                            String.format("Settings not found for type: %s", type)))))
        .doOnSuccess(settingsDao -> log.info(LOG_SUCCESS_ON_ACTION, ACTION_GET_BY_MAC, settingsDao))
        .doOnError(
            throwable ->
                log.error(
                    LOG_ERROR_ON_ACTION,
                    ACTION_GET_BY_MAC,
                    String.format("Type: %s", type),
                    throwable.getMessage()))
        .map(ModelMapper::toSettingsDto);
  }

  public Mono<SettingsDto> updateServiceAddress(
      final String moduleMacAddress, final String serviceAddress) {
    return settingsRepository
        .findFirstByModuleMacAddress(moduleMacAddress)
        .switchIfEmpty(
            Mono.defer(
                () ->
                    Mono.error(
                        new NotFoundException(
                            String.format(
                                "Settings not found for mac address: %s", moduleMacAddress)))))
        .map(
            settingsDao ->
                ModelMapper.enrichSettingsDaoWithServiceAddress(settingsDao, serviceAddress))
        .flatMap(settingsDao -> settingsRepository.save(settingsDao))
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
                    moduleMacAddress,
                    throwable));
  }
}
