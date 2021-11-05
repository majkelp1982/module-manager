package pl.smarthouse.loghandler.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InfoDao {
  @Id private String id;
  private String moduleName;
  private String message;
}
