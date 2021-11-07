package pl.smarthouse.modulemanager.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import pl.smarthouse.modulemanager.model.SettingsDao;
import pl.smarthouse.modulemanager.model.SettingsDto;
import pl.smarthouse.modulemanager.service.SettingsHandlerService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class ModuleManagerController {

  SettingsHandlerService settingsHandlerService;

  @PostMapping(value = "/settings")
  public Mono<SettingsDto> saveSettings(
      @RequestBody final SettingsDto settingsDto, final ServerHttpRequest request) {
    return settingsHandlerService.saveSettings(
        settingsDto, request.getRemoteAddress().getAddress().getHostAddress());
  }

  @GetMapping(value = "/all")
  public Flux<SettingsDao> getAllModules() {
    return settingsHandlerService.findAll();
  }

  @GetMapping(value = "/ip")
  public Mono<String> getIPbyMacAddress(@RequestParam final String macAddress) {
    return settingsHandlerService.getIPbyMacAddress(macAddress);
  }
}
