package br.com.ithappenssh.mapper.typehandler;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author luis.o.oliveira
 * @implNote Entity Transient
 */
@Data
public class DetailsHandlerError {
  private String detalhes;
  private Long status;
  private Long timestamp;
  private String routeTask;
}
