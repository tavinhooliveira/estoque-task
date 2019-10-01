package br.com.ithappenssh.service.impl;

import br.com.ithappenssh.mapper.PedidoSolicitadoMapper;
import br.com.ithappenssh.mapper.typehandler.Status;
import br.com.ithappenssh.model.Cliente;
import br.com.ithappenssh.model.LiberacaoPedido;
import br.com.ithappenssh.model.SolicitacaoPedido;
import br.com.ithappenssh.model.dto.PedidoSolicitadoDTO;
import br.com.ithappenssh.service.LiberacaoPedidoCreditoService;
import br.com.ithappenssh.service.builddto.CompraDto;
import br.com.ithappenssh.utils.exception.ProcessamentoErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author luis.o.oliveira
 * @implNote Regras para liberação de pedidos!
 */
@Slf4j
@Service
public class LiberacaoPedidoCreditoServiceImpl implements LiberacaoPedidoCreditoService {

  @Autowired
  private ClienteServiceImpl clienteServiceImpl;

  @Autowired
  private PedidoSolicitadoMapper pedidoSolicitadoMapper;

  @Autowired
  private CompraDto compraDto;

  @Value("${config.user.sistema}")
  private String usuarioSistema;

  @Value("${config.message.liberado}")
  private String liberado;

  @Value("${config.message.naoliberado}")
  private String naoLiberado;

  @Value("${config.formapagamento.avista}")
  private String avista;

  @Value("${config.formapagamento.boletoavista}")
  private String boletoAvista;

  public void solicitarPedido() {
    List<PedidoSolicitadoDTO> pedidos = pedidoSolicitadoMapper.listarPedidosSolicitados();
    if (log.isInfoEnabled()) {
      log.info("Recuperando os pedidos para o processamento | TOTAL: {}", pedidos.size());
    }
    pedidos.forEach(p -> {
      if (isPedidoProcessado(p.getPedidoId())) {
        if (log.isInfoEnabled()) {
          log.info("Pedido já processado: {}", p.getPedidoId());
        }
      } else {
        Cliente cliente = clienteServiceImpl.recuperarCliente(p.getCliente().getCD18_ID());
        if (log.isInfoEnabled()) {
          log.info("Recuperando o cliente | CODIGO: {}", cliente.getCD18_ID());
        }
        if (verificarFormaPagamento(p.getFormaPagamento()) == 1) {
          compraDto.compraAvistaDto(p, cliente.getCD18_ID());
        } else if (verificarFormaPagamento(p.getFormaPagamento()) == 2) {
          compraDto.compraBoletoAvistaDto(p, cliente.getCD18_ID());
        } else {
          compraDto.compraBoletoDto(p, cliente.getCD18_ID());
        }
      }
    });
  }

  private boolean isPedidoProcessado(Long id) {
    SolicitacaoPedido pedido = pedidoSolicitadoMapper.buscarPedidosProcessadosMapper(id);
    if (pedido != null) {
      log.info("O pedido já está registrado |TRASACAO:" + pedido.getId() + "|PEDIDO:" + pedido.getPedido() +
          "|DT_PROCESSAMENTO:" + pedido.getDTProcessamento() + "|STATUS:" + pedido.getStatus() + "|detalhes:" + pedido.getDescricao());
      if (pedido.getStatus().equals(Status.SUCCESS)) {
        return true;
      }
    }
    return false;
  }

  private int verificarFormaPagamento(Long formaPagamento) {
    if (formaPagamento != null && formaPagamento == Long.parseLong(this.avista)) {
      log.info("Forma de Pagamento A VISTA: {}", formaPagamento);
      return 1;
    } else if (formaPagamento != null && formaPagamento == Long.parseLong(this.boletoAvista)) {
      log.info("Forma de pagamento BOLETO AVISTA: {}", formaPagamento);
      return 2;
    }
    return 0;
  }

  public void processaPedido(LiberacaoPedido liberacaoPedido, SolicitacaoPedido solicitacaoPedido) throws Exception {
    try {
      log.info("Processando pedido...: {}", solicitacaoPedido.getPedido());

      pedidoSolicitadoMapper.salvarSolicitacaoPedidoMapper(solicitacaoPedido);
      if (log.isDebugEnabled()) {
        log.debug("Pedido registrado  table|ES19B_SOLICITACAO_PEDIDO| Transacao:{}, Pedido:{}", solicitacaoPedido.getId(), solicitacaoPedido.getPedido());
      }
      pedidoSolicitadoMapper.salvarLiberacaoPedidoMapper(liberacaoPedido);
      if (log.isDebugEnabled()) {
        log.debug("Pedido registrado table|ES19_LIBERACAO_PEDIDO| Transacao:{}, Pedido:{}", liberacaoPedido.getES19_ID(), liberacaoPedido.getES19_ID_PEDIDO());
      }
    }catch (Exception e){
      throw new ProcessamentoErrorException("Erro ao processar o pedido");
    }
  }

}