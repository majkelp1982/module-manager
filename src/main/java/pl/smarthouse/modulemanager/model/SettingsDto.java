package pl.smarthouse.modulemanager.model;

import lombok.*;

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
}
