package br.com.ithappenssh.model;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author luis.o.oliveira
 * @implNote Model Persistent, representação da tabela {CD18_CLIENTE} da base de dados!
 */
@Data
@Table(name = "CD18_CLIENTE")
public class Cliente {

  private Long CD18_ID;
  private Long CD18_ID_CIDADE;
  private Long CD18_ID_ESTADO;
  private Long CD18_ID_RAMO_ATIV;
  private Long CD18_ID_BAIRRO;
  private Long CD18_ID_PAIS;
  private Long CD18_CATEGORIA;

  private String CD18_RAZAO_SOCIAL;
  private String CD18_FANT;
  private String CD18_ENDE;
  private String CD18_BAIRRO;
  private String CD18_ID_UF;
  private String CD18_CEP;
  private String D18_DDD;
  private String CD18_FONE;
  private String CD18_EMPR;
  private String CD18_ENDE_COBR;
  private String CD18_CIDADE_COBR;
  private String CD18_UF_COBR;
  private String CD18_CEP_COBR;
  private String CD18_PESSOA;
  private String CD18_CIC_CGC;
  private String CD18_INSC_ESTADUAL;
  private String CD18_OBSE;
  private String CD18_NUMERO;
  private String CD18_COMPLEMENTO;
  private String CD18_NOME_PAI;
  private String CD18_NOME_MAE;
  private String CD18_ATIVO;
  private String CD18_EMAIL;
  private String D18_CONTRIBUINTE;
  private String CD18_TIPO_CLIENTE;
  private String CD18_SERASA_ESPECIAL;
  private String CD18_CEP_COMPLEMENTO;
  private String CD18_CEP_COBR_COMPLEMENTO;
  private String CD18_EMAIL_NFE;

  private Integer CD18_CREDITO;
  private Integer CD18_CREDITO_ATUAL;
  private Integer CD18_LATITUDE;
  private Integer CD18_LONGITUDE;
  private Integer CD18_SIMPLIFICADO;
  private Integer CD18_NAO_UNIFICA_BOLETO;
  private Integer CD18_INSENTO_TAXA_BOLETO;
  private Integer CD18_RG;

  private Date CD18_ROWVERSION;
  private Date CD18_DT_NASC;
  private Date CD18_DT_COMPRA;
  private Date CD18_DT_CADA;
  private Date CD18_DT_ATUALIZACAO;

}






