package pl.smarthouse.loghandler.repository.reactive;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.smarthouse.loghandler.model.ErrorDao;
import pl.smarthouse.loghandler.repository.ErrorRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class ReactiveErrorRepository {

	ErrorRepository errorRepository;

	public Mono<ErrorDao> save (final ErrorDao errorDao) {
		return Mono.fromCallable(() -> errorRepository.save(errorDao))
				.subscribeOn(Schedulers.boundedElastic())
				.cast(ErrorDao.class)
				.retryWhen(Retry.fixedDelay(10, Duration.ofMillis(1000)));
	}
}
