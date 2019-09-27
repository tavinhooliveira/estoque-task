package br.com.ithappenssh.utils.exception;

public class ControllerExceptionHandler extends Exception {

  public ControllerExceptionHandler() {
  }

  public ControllerExceptionHandler(String mensagem) {
    super(mensagem);
  }

  public ControllerExceptionHandler(String mensagem, Throwable causa) {
    super(mensagem, causa);
  }

  public ControllerExceptionHandler(Throwable throwable) {
    super(throwable);
  }

  public ControllerExceptionHandler(String s, Throwable throwable, boolean b, boolean b1) {
    super(s, throwable, b, b1);
  }
}