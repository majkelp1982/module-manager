package pl.smarthouse.modulemanager.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateTimeUtils {
  public static Instant toInstant(final LocalDateTime localDateTime) {
    return localDateTime.toInstant(ZoneOffset.UTC);
  }
}
