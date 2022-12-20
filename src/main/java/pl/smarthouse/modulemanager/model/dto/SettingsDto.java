package pl.smarthouse.modulemanager.model.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SettingsDto {
  private String moduleType;
  private int typeHash;
  private boolean isTypeLocked;
  private String macAddress;
  private String version;
  private String firmware;
  private String ipAddress;
  Instant updateTimestamp;
}
