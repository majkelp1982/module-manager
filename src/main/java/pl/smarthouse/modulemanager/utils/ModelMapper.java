package pl.smarthouse.modulemanager.utils;

import pl.smarthouse.modulemanager.model.dao.SettingsDao;
import pl.smarthouse.modulemanager.model.dto.SettingsDto;

import java.time.LocalDateTime;

public class ModelMapper {
  public static SettingsDao toSettingsDao(final SettingsDto settingsDto, final String hostAddress) {
    return SettingsDao.builder()
        .moduleType(settingsDto.getModuleType())
        .typeHash(settingsDto.getTypeHash())
        .isTypeLocked(settingsDto.isTypeLocked())
        .macAddress(settingsDto.getMacAddress())
        .version(settingsDto.getVersion())
        .firmware(settingsDto.getFirmware())
        .updateTimestamp(DateTimeUtils.toInstant(LocalDateTime.now()))
        .ipAddress(hostAddress)
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
        .ipAddress(settingsDao.getIpAddress())
        .updateTimestamp(settingsDao.getUpdateTimestamp())
        .build();
  }
}
