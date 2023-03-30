package pl.smarthouse.modulemanager.model.dto;

import java.time.Instant;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
  private Instant updateTimestamp;
}
