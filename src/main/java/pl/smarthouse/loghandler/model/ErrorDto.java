package pl.smarthouse.loghandler.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ErrorDto {
  private String id;
  private String moduleName;
  private int httpCode;
  private String message;
  private int errorCode;
}
