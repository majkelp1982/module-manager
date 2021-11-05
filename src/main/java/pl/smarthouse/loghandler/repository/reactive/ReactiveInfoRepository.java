package pl.smarthouse.loghandler.repository.reactive;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.smarthouse.loghandler.model.InfoDao;
import pl.smarthouse.loghandler.model.InfoDto;
import pl.smarthouse.loghandler.repository.InfoRepository;
import pl.smarthouse.loghandler.utils.ModelMapper;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Repository
public class ReactiveInfoRepository {

  InfoRepository infoRepository;

  public Mono<InfoDto> save(final InfoDao infoDao) {
    return infoRepository.save(infoDao).map(element -> ModelMapper.toInfoDto(element));
  }
}
