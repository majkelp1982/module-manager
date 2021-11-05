package pl.smarthouse.loghandler.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.smarthouse.loghandler.model.ErrorDao;
import pl.smarthouse.loghandler.model.ErrorDto;
import pl.smarthouse.loghandler.model.InfoDao;
import pl.smarthouse.loghandler.model.InfoDto;
import pl.smarthouse.loghandler.repository.reactive.ReactiveErrorRepository;
import pl.smarthouse.loghandler.repository.reactive.ReactiveInfoRepository;
import pl.smarthouse.loghandler.utils.ModelMapper;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class LogHandlerService {

  ReactiveErrorRepository reactiveErrorRepository;
  ReactiveInfoRepository reactiveInfoRepository;

  public Mono<ErrorDto> saveError(final ErrorDto errorDto) {
    final ErrorDao errorDao = ModelMapper.toErrorDao(errorDto);
    errorDao.setIncomingTime(LocalDateTime.now());
    errorDao.setOutgoingTime(null);
    return reactiveErrorRepository.save(errorDao);
  }

  public Mono<InfoDto> saveInfo(final InfoDto infoDto) {
    final InfoDao infoDao = ModelMapper.toInfoDao(infoDto);
    return reactiveInfoRepository.save(infoDao);
  }
}
