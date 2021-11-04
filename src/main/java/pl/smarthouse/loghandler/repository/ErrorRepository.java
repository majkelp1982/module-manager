package pl.smarthouse.loghandler.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pl.smarthouse.loghandler.model.ErrorDao;

public interface ErrorRepository extends ReactiveMongoRepository<ErrorDao, String> {
}
