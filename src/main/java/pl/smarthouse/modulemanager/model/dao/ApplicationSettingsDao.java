package pl.smarthouse.modulemanager.model.dao;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "settings")
@Data
@Builder
public class ApplicationSettingsDao {
  @Id private String id;
  private String type;
  private String serviceVersion;
  private String serviceAddress;
  Instant serviceUpdateTimestamp;
}
