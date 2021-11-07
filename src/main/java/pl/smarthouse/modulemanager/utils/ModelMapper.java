package pl.smarthouse.modulemanager.utils;

import pl.smarthouse.modulemanager.model.SettingsDao;
import pl.smarthouse.modulemanager.model.SettingsDto;

public class ModelMapper {
  public static SettingsDao toSettingsDao(final SettingsDto settingsDto) {
    return SettingsDao.builder()
        .moduleType(settingsDto.getModuleType())
        .typeHash(settingsDto.getTypeHash())
        .isTypeLocked(settingsDto.isTypeLocked())
        .macAddress(settingsDto.getMacAddress())
        .version(settingsDto.getVersion())
        .firmware(settingsDto.getFirmware())
        .build();
  }

  public static SettingsDto toSettingsDto(final SettingsDao settingsDao) {
    return SettingsDto.builder()
        .moduleType(settingsDao.getModuleType())
        .typeHash(settingsDao.getTypeHash())
        .isTypeLocked(settingsDao.isTypeLocked())
        .macAddress(settingsDao.getMacAddress())
        .version(settingsDao.getVersion())
        .firmware(settingsDao.getFirmware())
        .build();
  }
}
