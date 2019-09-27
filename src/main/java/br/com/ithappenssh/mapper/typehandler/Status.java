package br.com.ithappenssh.mapper.typehandler;

public enum Status {
  S("PROCESSADO_SUCESSO"),
  E("PROCESSADO_ERRO");
  private String descricao;

  Status(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }
}
