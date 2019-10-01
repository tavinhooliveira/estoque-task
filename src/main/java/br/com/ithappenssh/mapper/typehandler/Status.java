package br.com.ithappenssh.mapper.typehandler;

/**
 * @author luis.o.oliveira
 * @implNote Enum
 */
public enum Status {
  SUCCESS("1"),
  ERROR("0");
  private String descricao;

  Status(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }
}
