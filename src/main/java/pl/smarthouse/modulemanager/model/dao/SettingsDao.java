package pl.smarthouse.modulemanager.model.dao;

import java.time.Instant;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
  @NonNull private String firmware;
  @NotNull private String ipAddress;
  @NonNull Instant updateTimestamp;
}
