package br.com.ithappenssh.model;

import br.com.ithappenssh.mapper.typehandler.Status;
import lombok.Data;

import java.util.Date;

@Data
public class SolicitacaoPedido {
  private Long id;
  private Long pedido;
  private Date dT_Processamento;
  private Status status;
  private String descricao;
}
