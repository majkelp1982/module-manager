package pl.smarthouse.modulemanager.repository.reactive;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.smarthouse.modulemanager.model.dao.SettingsDao;
import pl.smarthouse.modulemanager.repository.SettingsRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@AllArgsConstructor
@Repository
public class ReactiveSettingsRepository {

  SettingsRepository settingsRepository;

  public Mono<SettingsDao> save(final SettingsDao settingsDao) {
    return settingsRepository.save(settingsDao).subscribeOn(Schedulers.boundedElastic());
  }

  public Mono<Void> deleteByMacAddress(final String macAddress) {
    return settingsRepository
        .deleteByMacAddress(macAddress)
        .subscribeOn(Schedulers.boundedElastic());
  }

  public Flux<SettingsDao> findAll() {
    return settingsRepository.findAll().subscribeOn(Schedulers.boundedElastic());
  }

  public Mono<SettingsDao> findByMacAddress(final String macAddress) {
    return settingsRepository.findFirstByMacAddress(macAddress);
  }
}
