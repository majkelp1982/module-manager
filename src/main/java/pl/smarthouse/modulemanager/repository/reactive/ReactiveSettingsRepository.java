package pl.smarthouse.modulemanager.repository.reactive;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.smarthouse.modulemanager.model.SettingsDao;
import pl.smarthouse.modulemanager.model.SettingsDto;
import pl.smarthouse.modulemanager.repository.SettingsRepository;
import pl.smarthouse.modulemanager.utils.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Repository
public class ReactiveSettingsRepository {

  SettingsRepository settingsRepository;

  public Mono<SettingsDto> save(final SettingsDao settingsDao) {
    return settingsRepository
        .findAllByMacAddress(settingsDao.getMacAddress())
        .doOnNext(
            settingsDao1 -> {
              System.out.println(settingsDao1.getId());
              settingsRepository.delete(settingsDao1).subscribe();
            })
        .then(settingsRepository.save(settingsDao))
        // fixme replace with log handler
        .doOnError(throwable -> throwable.printStackTrace())
        .map(element -> ModelMapper.toSettingsDto(element));
  }

  public Flux<SettingsDao> findAll() {
    return settingsRepository.findAll();
  }

  public Mono<String> findIPByMacAddress(final String macAddress) {
    return settingsRepository
        .findFirstByMacAddress(macAddress)
        .map(element -> element.getIpAddress());
  }
}
