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
  private String id;
  private String type;
  private int typeHash;
  private boolean isTypeLocked;
  private String moduleMacAddress;
  private String serviceVersion;
  private String moduleFirmwareVersion;
  private String moduleIpAddress;
  private String serviceAddress;
  Instant moduleUpdateTimestamp;
  Instant serviceUpdateTimestamp;
  boolean connectionEstablish;
}
