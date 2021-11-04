package pl.smarthouse.loghandler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorDao {
  @Id private String id;
  private String moduleName;
  private int httpCode;
  private String message;
}
