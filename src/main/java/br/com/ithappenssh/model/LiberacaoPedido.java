package br.com.ithappenssh.model;

import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author luis.o.oliveira
 * @implNote Model Persistent, representação da tabela {ES19_LIBERACAO_PEDIDO} da base de dados!
 */
@Data
@Table(name = "ES19_LIBERACAO_PEDIDO")
public class LiberacaoPedido {
  /**
   * tabela: ES19_LIBERACAO_PEDIDO
   */
  private Long ES19_ID;
  private Long ES19_ID_USUARIO;
  private Long ES19_ID_PEDIDO;
  private String ES19_TITULO_VENCIDO;
  private String S19_CHEQUE_DEVOLVIDO;
  private String ES19_CHEQUE_REAPRESENTADO;
  private String ES19_EXCEDEU_LIMITE;

  @Temporal(TemporalType.TIMESTAMP)
  private Date ES19_DT;

  private int ES19_LIBERACAO_AUTOMATICA;
  private int ES19_RESTRICAO_SEFAZ;
}