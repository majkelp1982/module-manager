package pl.smarthouse.loghandler.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.smarthouse.loghandler.model.ErrorDao;
import pl.smarthouse.loghandler.repository.reactive.ReactiveErrorRepository;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ErrorHandlerService {

	ReactiveErrorRepository reactiveErrorRepository;

	public Mono<ErrorDao> saveError(final ErrorDao errorDao) {
		return reactiveErrorRepository.save(errorDao);
	}
}
