package pl.smarthouse.modulemanager.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pl.smarthouse.modulemanager.model.dao.SettingsDao;
import reactor.core.publisher.Mono;

public interface SettingsRepository extends ReactiveMongoRepository<SettingsDao, String> {

  Mono<SettingsDao> findFirstByModuleMacAddress(String macAddress);

  Mono<SettingsDao> findFirstByType(String type);
}
