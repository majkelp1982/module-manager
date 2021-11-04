package pl.smarthouse.loghandler.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.smarthouse.loghandler.model.ErrorDao;
import pl.smarthouse.loghandler.service.ErrorHandlerService;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class ErrorHandlerController {

	ErrorHandlerService errorHandlerService;

	@PostMapping(value = "/error")
	public Mono<ErrorDao> saveError(
			@RequestBody final ErrorDao errorDao) {
		return errorHandlerService.saveError(errorDao);
	}
}
