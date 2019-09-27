package br.com.ithappenssh.service;

import br.com.ithappenssh.mapper.ClienteMapper;
import br.com.ithappenssh.model.Cliente;
import br.com.ithappenssh.utils.exception.ClienteNaoEncontradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class ClienteService {

  @Autowired
  private ClienteMapper clienteMapper;

  public Cliente recuperarCliente(Long codigoCliente) {
    Cliente cliente = clienteMapper.recuperarClienteMapper(codigoCliente);
    if (cliente == null) {
      throw new ClienteNaoEncontradoException("O cliente pesquisada não foi encontrada");
    }
    return cliente;
  }

  public int limiteExcedente6Porcento(Long clienteId) {
    int cheque = 0;
    if (cheque == 1) {
      log.info("Cliente:" + clienteId + " Possue cheque devolvido!");
      return 1;
    }
    log.info("Cliente:" + clienteId + " NÃO Possue cheque devolvido!");
    return 0;
  }

  public int chequeReapresentado(Long clienteId) {
    int cheque = clienteMapper.recuperarChequeReapresentadoMapper(clienteId);
    if (cheque == 1) {
      if (log.isInfoEnabled()) {
        log.info("Cliente:" + clienteId + " Possue cheque reapresentado!");
      }
      return 1;
    }
    if (log.isInfoEnabled()) {
      log.info("Cliente:" + clienteId + " NÃO Possue cheque reapresentado!");
    }
    return 0;
  }

  public int chequeDevolvido(Long clienteId) {
    int cheque = clienteMapper.recuperarChequeDevolvidoMapper(clienteId);
    if (cheque == 1) {
      log.info("Cliente:" + clienteId + " Possue cheque devolvido!");
      return 1;
    }
    log.info("Cliente:" + clienteId + " NÃO Possue cheque devolvido!");
    return 0;
  }

  public int titulosVencidos(Long clienteId) {
    int cheque = clienteMapper.recuperarChequeDevolvidoMapper(clienteId);
    if (cheque == 1) {
      log.info("Cliente:" + clienteId + " Possue titulos vencidos!");
      return 1;
    }
    log.info("Cliente:" + clienteId + " NÃO Possue titulos vencidos!");
    return 0;
  }

  public int pedenciaSEFAZ(Long clienteId) {
    int stausSefaz = clienteMapper.recuperarStatusSefazMapper(clienteId);
    if (stausSefaz == 2) {
      if (log.isInfoEnabled()) {
        log.info("Cliente:" + clienteId + " tem restrição na SEFAZ");
      }
      return 1;
    }
    if (log.isInfoEnabled()) {
      log.info("Cliente:" + clienteId + " NÃO tem restrição na SEFAZ");
    }
    return 0;
  }

  public int excedeuLimiteSumarizadoDisponivel(BigDecimal valorPedido, Long clienteId) {
    BigDecimal limiteBase = limiteBaseCliente(clienteId);
    BigDecimal limiteSumarizadoTotal = BigDecimal.ZERO;
    if (limiteSumarizadoTotal.compareTo(valorPedido) >= 0) {
      return 0;
    }
    return 1;
  }

  private BigDecimal limiteBaseCliente(Long clienteId) {
    BigDecimal valorLimiteBase = clienteMapper.recuperarLimiteBaseDoClienteMapper(clienteId);
    return valorLimiteBase;
  }

  private BigDecimal limiteSumarizadoClienteDependente(Long clienteId) {
    BigDecimal valorLimiteSumarizado = clienteMapper.recuperarLimiteSumarizadoClienteEAssociadosMapper(clienteId);
    return valorLimiteSumarizado;
  }

}
