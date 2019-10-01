package br.com.ithappenssh.utils.exception;

public class ProcessamentoErrorException extends RuntimeException {

  public ProcessamentoErrorException() {
  }

  public ProcessamentoErrorException(String mensagem) {
    super(mensagem);
  }

  public ProcessamentoErrorException(String mensagem, Throwable causa) {
    super(mensagem, causa);
  }

  public ProcessamentoErrorException(Throwable throwable) {
    super(throwable);
  }

  public ProcessamentoErrorException(String s, Throwable throwable, boolean b, boolean b1) {
    super(s, throwable, b, b1);
  }

}