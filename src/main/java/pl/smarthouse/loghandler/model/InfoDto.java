package pl.smarthouse.loghandler.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class InfoDto {
  private String id;
  private String moduleName;
  private String message;
}
