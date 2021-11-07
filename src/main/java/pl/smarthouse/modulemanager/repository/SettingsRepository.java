package pl.smarthouse.modulemanager.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pl.smarthouse.modulemanager.model.SettingsDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SettingsRepository extends ReactiveMongoRepository<SettingsDao, String> {

  Mono<SettingsDao> findFirstByMacAddress(String macAddress);

  Flux<SettingsDao> findAllByMacAddress(String macAddress);
}
