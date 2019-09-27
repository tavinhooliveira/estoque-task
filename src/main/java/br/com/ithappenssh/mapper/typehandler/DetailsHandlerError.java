package br.com.ithappenssh.mapper.typehandler;

import lombok.Data;

@Data
public class DetailsHandlerError {
  private String detalhes;
  private Long status;
  private Long timestamp;
  private String routeTask;
}
