package br.com.ithappenssh.model;

import lombok.Data;

import java.util.Date;

@Data
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
  private Date ES19_DT;
  private int ES19_LIBERACAO_AUTOMATICA;
  private int ES19_RESTRICAO_SEFAZ;
}