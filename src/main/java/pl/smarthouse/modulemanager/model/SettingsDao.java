package pl.smarthouse.modulemanager.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "settings")
@Getter
@Setter
@Builder
public class SettingsDao {
  @Id private String id;
  private String moduleType;
  private int typeHash;
  @NonNull private boolean isTypeLocked;
  @NonNull private String macAddress;
  private String version;
  private String firmware;
  private String ipAddress;
  Instant updateTimestamp;
}
