package pl.smarthouse.modulemanager.utils;

import java.time.LocalDateTime;
import pl.smarthouse.modulemanager.model.dao.SettingsDao;
import pl.smarthouse.modulemanager.model.dto.ModuleSettingsDto;
import pl.smarthouse.sharedobjects.dto.SettingsDto;

public class ModelMapper {
  public static SettingsDao toSettingsDao(
      final String id, final ModuleSettingsDto moduleSettingsDto, final String hostAddress) {
    return SettingsDao.builder()
        .id(id)
        .type(moduleSettingsDto.getModuleType())
        .typeHash(moduleSettingsDto.getTypeHash())
        .isTypeLocked(moduleSettingsDto.isTypeLocked())
        .moduleMacAddress(moduleSettingsDto.getMacAddress())
        .serviceVersion(moduleSettingsDto.getVersion())
        .moduleFirmwareVersion(moduleSettingsDto.getFirmware())
        .moduleUpdateTimestamp(DateTimeUtils.toInstant(LocalDateTime.now()))
        .moduleIpAddress(hostAddress)
        .build();
  }

  public static SettingsDao updateSettingsDaoWithModuleSettings(
      final SettingsDao settingsDao,
      final ModuleSettingsDto moduleSettingsDto,
      final String hostAddress) {
    settingsDao.setType(moduleSettingsDto.getModuleType());
    settingsDao.setTypeHash(moduleSettingsDto.getTypeHash());
    settingsDao.setTypeLocked(moduleSettingsDto.isTypeLocked());
    settingsDao.setModuleMacAddress(moduleSettingsDto.getMacAddress());
    settingsDao.setServiceVersion(moduleSettingsDto.getVersion());
    settingsDao.setModuleFirmwareVersion(moduleSettingsDto.getFirmware());
    settingsDao.setModuleUpdateTimestamp(DateTimeUtils.toInstant(LocalDateTime.now()));
    settingsDao.setModuleIpAddress(hostAddress);
    return settingsDao;
  }

  public static SettingsDto toSettingsDto(final SettingsDao settingsDao) {
    return SettingsDto.builder()
        .id(settingsDao.getId())
        .type(settingsDao.getType())
        .typeHash(settingsDao.getTypeHash())
        .isTypeLocked(settingsDao.isTypeLocked())
        .moduleMacAddress(settingsDao.getModuleMacAddress())
        .serviceVersion(settingsDao.getServiceVersion())
        .moduleFirmwareVersion(settingsDao.getModuleFirmwareVersion())
        .moduleIpAddress(settingsDao.getModuleIpAddress())
        .serviceAddress(settingsDao.getServiceAddress())
        .moduleUpdateTimestamp(settingsDao.getModuleUpdateTimestamp())
        .serviceUpdateTimestamp(settingsDao.getServiceUpdateTimestamp())
        .connectionEstablish(settingsDao.isConnectionEstablish())
        .build();
  }

  public static SettingsDao enrichSettingsDaoWithServiceAddress(
      final SettingsDao settingsDao, final String serviceAddress) {
    settingsDao.setServiceAddress(serviceAddress);
    settingsDao.setServiceUpdateTimestamp(DateTimeUtils.toInstant(LocalDateTime.now()));
    return settingsDao;
  }
}
