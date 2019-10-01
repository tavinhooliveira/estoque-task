package br.com.ithappenssh.service.impl;

import br.com.ithappenssh.mapper.ClienteMapper;
import br.com.ithappenssh.model.Cliente;
import br.com.ithappenssh.service.ClienteService;
import br.com.ithappenssh.utils.exception.ClienteNaoEncontradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author luis.o.oliveira
 * @implNote Regras de negocio para liberação de credito do Cliente solicitante!
 */
@Slf4j
@Service
public class ClienteServiceImpl implements ClienteService {

  @Autowired
  private ClienteMapper clienteMapper;

  private static final String CLIENTE = "Cliente {}";

  @Value("${config.cliente.restricao.sefaz}")
  private Integer clienteRestricaoSefaz;

  @Value("${config.cliente.limite.porcentagem.credito}")
  private Integer valorPorcentagem;

  public Cliente recuperarCliente(Long codigoCliente) {
    Cliente cliente = clienteMapper.recuperarClienteMapper(codigoCliente);
    log.info("Recuperando um cliente por ID {}", codigoCliente);
    if (cliente == null) {
      throw new ClienteNaoEncontradoException("O cliente pesquisada não foi encontrada");
    }
    return cliente;
  }

  public int chequeReapresentado(Long clienteId) {
    double cheque = clienteMapper.recuperarChequeReapresentadoMapper(clienteId);
    if (cheque > 0 ) {
      if (log.isInfoEnabled()) {
        log.info(CLIENTE, clienteId + " Possue cheque reapresentado!");
      }
      return 1;
    }
    if (log.isInfoEnabled()) {
      log.info(CLIENTE, clienteId + " NÃO Possue cheque reapresentado!");
    }
    return 0;
  }

  public int chequeDevolvido(Long clienteId) {
    double cheque = clienteMapper.recuperarChequeDevolvidoMapper(clienteId);
    if (cheque > 0) {
      log.info(CLIENTE, clienteId + " Possue cheque devolvido!");
      return 1;
    }
    log.info(CLIENTE, clienteId + " NÃO Possue cheque devolvido!");
    return 0;
  }

  public int titulosVencidos(Long clienteId) {
    double titulos = clienteMapper.recuperarTitulosVencidosMapper(clienteId);
    if (titulos > 0) {
      log.info(CLIENTE, +clienteId + " Possue titulos vencidos!");
      return 1;
    }
    log.info(CLIENTE, +clienteId + " NÃO Possue titulos vencidos!");
    return 0;
  }

  public int pedenciaSEFAZ(Long clienteId) {
    int stausSefaz = clienteMapper.recuperarStatusSefazMapper(clienteId);
    if (stausSefaz != 0 && stausSefaz == clienteRestricaoSefaz) {
      if (log.isInfoEnabled()) {
        log.info(CLIENTE, clienteId + " tem restrição na SEFAZ");
      }
      return 1;
    }
    if (log.isInfoEnabled()) {
      log.info(CLIENTE, clienteId + " NÃO tem restrição na SEFAZ");
    }
    return 0;
  }

  public int excedeuLimiteSumarizadoDisponivel(BigDecimal valorPedido, Long clienteId) {
    if (limiteDisponivelTotal(clienteId).compareTo(valorPedido) >= 0) {
      return 0;
    }
    return 1;
  }

  public BigDecimal limiteDisponivelTotal(Long clienteId){
    BigDecimal limiteSumarizadoAssociados = limiteSumarizadoClienteDependente(clienteId);
    BigDecimal limiteDecrementado = limiteDecrementado(clienteId);
    return limiteDecrementado.add(limiteTotalValorMaisPorcentagem(limiteSumarizadoAssociados));
  }

  public BigDecimal limiteTotalValorMaisPorcentagem(BigDecimal valorlimite) {
    BigDecimal totalCost = BigDecimal.ZERO;
    BigDecimal itemCost = valorlimite.multiply(new BigDecimal(this.valorPorcentagem));
    totalCost = totalCost.add(itemCost);
    return totalCost;
  }

  private BigDecimal limiteSumarizadoClienteDependente(Long clienteId) {
    return clienteMapper.recuperarLimiteSumarizadoClienteEAssociadosMapper(clienteId);
  }

  private BigDecimal limiteDecrementado(Long clienteId) {
    return clienteMapper.recuperarLimiteResanteClienteEAssociadosMapper(clienteId);
  }

}
