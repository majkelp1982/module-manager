package pl.smarthouse.modulemanager.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeUtils {
  public static Instant toInstant(final LocalDateTime localDateTime) {
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
  }
}
