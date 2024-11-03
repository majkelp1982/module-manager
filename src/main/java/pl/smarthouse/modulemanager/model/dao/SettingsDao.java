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
  private String type;
  private int typeHash;
  @NonNull private boolean isTypeLocked;
  @NonNull private String moduleMacAddress;
  private String serviceVersion;
  @NonNull private String moduleFirmwareVersion;
  @NotNull private String moduleIpAddress;
  private String serviceAddress;
  @NonNull Instant moduleUpdateTimestamp;
  Instant serviceUpdateTimestamp;
  boolean connectionEstablish;
  long uptimeInMinutes;
}
