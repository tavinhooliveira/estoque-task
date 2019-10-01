package br.com.ithappenssh.utils.handler;

import br.com.ithappenssh.mapper.typehandler.DetailsHandlerError;
import br.com.ithappenssh.utils.exception.ClienteNaoEncontradoException;
import br.com.ithappenssh.utils.exception.ProcessamentoErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(ClienteNaoEncontradoException.class)
  public ResponseEntity<DetailsHandlerError> handleClienteNaoEncontradoException
      (ClienteNaoEncontradoException e, HttpServletRequest request) {

    DetailsHandlerError erro = new DetailsHandlerError();
    erro.setStatus(404l);
    erro.setDetalhes(e.getMessage());
    erro.setTimestamp(System.currentTimeMillis());
    erro.setRouteTask(request.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
  }

  @ExceptionHandler(ProcessamentoErrorException.class)
  public ResponseEntity<DetailsHandlerError> handleProcessamentoErrorException
      (ProcessamentoErrorException e, HttpServletRequest request) {

    DetailsHandlerError erro = new DetailsHandlerError();
    erro.setStatus(404l);
    erro.setDetalhes(e.getMessage());
    erro.setTimestamp(System.currentTimeMillis());
    erro.setRouteTask(request.getRequestURI());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
  }

}
