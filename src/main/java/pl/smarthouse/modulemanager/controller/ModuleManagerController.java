package pl.smarthouse.modulemanager.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import pl.smarthouse.modulemanager.model.dto.ModuleSettingsDto;
import pl.smarthouse.modulemanager.model.dto.SettingsDto;
import pl.smarthouse.modulemanager.service.SettingsHandlerService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class ModuleManagerController {

  SettingsHandlerService settingsHandlerService;

  @PostMapping(value = "/settings")
  public Mono<SettingsDto> saveModuleSettings(
      @RequestBody final ModuleSettingsDto moduleSettingsDto, final ServerHttpRequest request) {
    return settingsHandlerService.saveSettings(
        moduleSettingsDto, request.getRemoteAddress().getAddress().getHostAddress());
  }

  @PutMapping(value = "/updateServiceAddress")
  public Mono<SettingsDto> updateServiceAddress(
      @RequestParam final String moduleMacAddress, @RequestParam final String serviceAddress) {
    return settingsHandlerService.updateServiceAddress(moduleMacAddress, serviceAddress);
  }

  @GetMapping(value = "/all")
  public Flux<SettingsDto> getAllSettings() {
    return settingsHandlerService.findAll();
  }

  @GetMapping(value = "/settings")
  public Mono<SettingsDto> getByModuleMacAddress(@RequestParam final String moduleMacAddress) {
    return settingsHandlerService.getByModuleMacAddress(moduleMacAddress);
  }
}
