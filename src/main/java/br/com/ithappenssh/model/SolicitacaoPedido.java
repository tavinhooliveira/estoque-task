package br.com.ithappenssh.model;

import br.com.ithappenssh.mapper.typehandler.Status;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author luis.o.oliveira
 * @implNote Model Persistent, representação da tabela {ES19B_SOLICITACAO_PEDIDO} da base de dados!
 */
@Data
@Table(name = "ES19B_SOLICITACAO_PEDIDO")
public class SolicitacaoPedido {

  @Column(name = "ES19B_ID")
  private Long id;

  @Column(name = "ES19B_ID_PEDIDO")
  private Long pedido;

  @Column(name = "ES19B_ID_CLIENTE")
  private Long cliente;

  @Column(name = "ES19B_DT_PROCESSAMENTO")
  private Date dTProcessamento;

  @Column(name = "ES19B_STATUS")
  private String status;

  @Column(name = "ES19B_DESCRICAO")
  private String descricao;
}