package pl.smarthouse.modulemanager.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import pl.smarthouse.modulemanager.model.dto.SettingsDto;
import pl.smarthouse.modulemanager.service.SettingsHandlerService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class ModuleManagerController {

  SettingsHandlerService settingsHandlerService;

  @PostMapping(value = "/settings")
  public Mono<SettingsDto> updateSettings(
      @RequestBody final SettingsDto settingsDto, final ServerHttpRequest request) {
    return settingsHandlerService.updateSettings(
        settingsDto, request.getRemoteAddress().getAddress().getHostAddress());
  }

  @GetMapping(value = "/all")
  public Flux<SettingsDto> getAllModules() {
    return settingsHandlerService.findAll();
  }

  @GetMapping(value = "/settings")
  public Mono<SettingsDto> getByMacAddress(@RequestParam final String macAddress) {
    return settingsHandlerService.getByMacAddress(macAddress);
  }
}
