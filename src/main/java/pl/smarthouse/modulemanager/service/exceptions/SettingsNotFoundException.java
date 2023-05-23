package pl.smarthouse.modulemanager.service.exceptions;

public class SettingsNotFoundException extends RuntimeException {
  public SettingsNotFoundException(final String message) {
    super(message);
  }
}
