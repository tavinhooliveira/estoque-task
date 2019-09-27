package br.com.ithappenssh.utils.exception;

import lombok.Data;

@Data
public class ClienteNaoEncontradoException extends RuntimeException {

  public ClienteNaoEncontradoException(String s) {
    super(s);
  }

  public ClienteNaoEncontradoException(String s, Throwable throwable) {
    super(s, throwable);
  }

}