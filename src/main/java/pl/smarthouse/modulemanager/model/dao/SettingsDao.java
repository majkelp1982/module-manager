package pl.smarthouse.modulemanager.model.dao;

import java.time.Instant;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "settings")
@Data
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
