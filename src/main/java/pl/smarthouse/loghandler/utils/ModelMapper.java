package pl.smarthouse.loghandler.utils;

import pl.smarthouse.loghandler.model.ErrorDao;
import pl.smarthouse.loghandler.model.ErrorDto;
import pl.smarthouse.loghandler.model.InfoDao;
import pl.smarthouse.loghandler.model.InfoDto;

public class ModelMapper {
  public static ErrorDao toErrorDao(final ErrorDto errorDto) {
    return ErrorDao.builder()
        .moduleName(errorDto.getModuleName())
        .errorCode(errorDto.getErrorCode())
        .httpCode(errorDto.getHttpCode())
        .message(errorDto.getMessage())
        .build();
  }

  public static ErrorDto toErrorDto(final ErrorDao errorDao) {
    return ErrorDto.builder()
        .moduleName(errorDao.getModuleName())
        .errorCode(errorDao.getErrorCode())
        .httpCode(errorDao.getHttpCode())
        .message(errorDao.getMessage())
        .build();
  }

  public static InfoDao toInfoDao(final InfoDto infoDto) {
    return InfoDao.builder()
        .moduleName(infoDto.getModuleName())
        .message(infoDto.getMessage())
        .build();
  }

  public static InfoDto toInfoDto(final InfoDao infoDao) {
    return InfoDto.builder()
        .moduleName(infoDao.getModuleName())
        .message(infoDao.getMessage())
        .build();
  }
}
