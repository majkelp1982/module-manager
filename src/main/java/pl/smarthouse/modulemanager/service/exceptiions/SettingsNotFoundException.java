package pl.smarthouse.modulemanager.service.exceptiions;

public class SettingsNotFoundException extends RuntimeException {
  public SettingsNotFoundException(final String message) {
    super(message);
  }
}
