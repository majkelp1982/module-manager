package pl.smarthouse.modulemanager.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import pl.smarthouse.modulemanager.model.dto.ModuleSettingsDto;
import pl.smarthouse.modulemanager.service.SettingsService;
import pl.smarthouse.modulemanager.service.exceptions.SettingsNotFoundException;
import pl.smarthouse.sharedobjects.dto.SettingsDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class ModuleManagerController {

  SettingsService settingsService;

  @ExceptionHandler(SettingsNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Mono<String> handleNotFoundException(final Exception exception) {
    return Mono.just(exception.getMessage());
  }

  @PostMapping(value = "/settings")
  public Mono<SettingsDto> saveModuleSettings(
      @RequestBody final ModuleSettingsDto moduleSettingsDto, final ServerHttpRequest request) {
    return settingsService.saveSettings(
        moduleSettingsDto, request.getRemoteAddress().getAddress().getHostAddress());
  }

  @PutMapping(value = "/updateServiceAddress")
  public Mono<SettingsDto> updateServiceAddress(
      @RequestParam final String moduleMacAddress, @RequestParam final String serviceAddress) {
    return settingsService.updateServiceAddress(moduleMacAddress, serviceAddress);
  }

  @GetMapping(value = "/all")
  public Flux<SettingsDto> getAllSettings() {
    return settingsService.findAll();
  }

  @GetMapping(
      value = "/settings",
      params = {"moduleMacAddress"})
  public Mono<SettingsDto> getByModuleMacAddress(
      @RequestParam(required = false) final String moduleMacAddress) {
    return settingsService.getByModuleMacAddress(moduleMacAddress);
  }

  @GetMapping(
      value = "/settings",
      params = {"type"})
  public Mono<SettingsDto> getByType(@RequestParam(required = false) final String type) {
    return settingsService.getByType(type.toUpperCase());
  }
}
