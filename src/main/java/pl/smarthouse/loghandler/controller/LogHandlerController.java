package pl.smarthouse.loghandler.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.smarthouse.loghandler.model.ErrorDto;
import pl.smarthouse.loghandler.model.InfoDto;
import pl.smarthouse.loghandler.service.LogHandlerService;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class LogHandlerController {

  LogHandlerService logHandlerService;

  @PostMapping(value = "/error")
  public Mono<ErrorDto> saveError(@RequestBody final ErrorDto errorDto) {
    final Mono<ErrorDto> response = logHandlerService.saveError(errorDto);
    return response;
  }

  @PostMapping(value = "/info")
  public Mono<InfoDto> saveInfo(@RequestBody final InfoDto infoDto) {
    final Mono<InfoDto> response = logHandlerService.saveInfo(infoDto);
    return response;
  }
}
