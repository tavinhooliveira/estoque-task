package br.com.ithappenssh.service;

import br.com.ithappenssh.mapper.PedidoSolicitadoMapper;
import br.com.ithappenssh.mapper.typehandler.Status;
import br.com.ithappenssh.model.Cliente;
import br.com.ithappenssh.model.LiberacaoPedido;
import br.com.ithappenssh.model.SolicitacaoPedido;
import br.com.ithappenssh.model.dto.PedidoSolicitadoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class LiberacaoPedidoCreditoService {

  @Autowired
  private ClienteService clienteService;

  @Autowired
  private PedidoSolicitadoMapper pedidoSolicitadoMapper;

  private static final String ZERO = "0";
  private static final String UM = "1";
  private static final String USER_SISTEMA = "256";

  private void solicitarPedido() {
    List<PedidoSolicitadoDTO> pedidos = pedidoSolicitadoMapper.listarPedidosSolicitados();
    if (log.isInfoEnabled()) {
      log.info("Recuperando os pedidos para o processamento | TOTAL: " + pedidos.size());
    }
    pedidos.forEach(p -> {
      if (pedidoJaProcessado(p.getPedidoId())) {
        if (log.isInfoEnabled()) {
          log.info("Pedido já processado: " + p.getPedidoId());
        }
      } else {
        Cliente cliente = clienteService.recuperarCliente(p.getCliente().getCD18_ID());
        if (log.isInfoEnabled()) {
          log.info("Recuperando o cliente | CODIGO: " + cliente.getCD18_ID());
        }
        if (verificarFormaPagamento(p.getFormaPagamento()) == 1) {
          compraAvista(p, cliente.getCD18_ID());
        } else if (verificarFormaPagamento(p.getFormaPagamento()) == 2) {
          compraBoletoAvista(p, cliente.getCD18_ID());
        } else {
          compraBoleto(p, cliente.getCD18_ID());
        }
      }
    });
  }

  private boolean pedidoJaProcessado(Long id) {
    String PROCESSADO_ERRO = "PE";
    SolicitacaoPedido pedido = pedidoSolicitadoMapper.buscarPedidosProcessadosMapper(id);
    if (pedido != null) {
      log.info("O pedido já está registrado |TRASACAO:" + pedido.getId() + "|PEDIDO:" + pedido.getPedido() +
          "|DT_PROCESSAMENTO:" + pedido.getDT_Processamento() + "|STATUS:" + pedido.getStatus() + "|detalhes:" + pedido.getDescricao());
      if (pedido.getStatus().equals(PROCESSADO_ERRO)) {
        return false;
      }
      return true;
    }
    return false;
  }

  private int verificarFormaPagamento(Long formaPagamento) {
    final int A_VISTA = 501;
    final int BOLETO_VISTA = 612;
    if (formaPagamento != null && formaPagamento == A_VISTA) {
      log.info("Forma de Pagamento A VISTA: " + formaPagamento);
      return 1;
    } else if (formaPagamento != null && formaPagamento == BOLETO_VISTA) {
      log.info("Forma de pagamento BOLETO AVISTA: " + formaPagamento);
      return 2;
    }
    return 0;
  }

  private void compraAvista(PedidoSolicitadoDTO pedidoSolicitado, Long clienteId) {
    /**Popular DTO Liberação de Pedido(ESTOQUE.DBO.ES19_LIBERACAO_PEDIDO)*/
    LiberacaoPedido liberacaoPedido = new LiberacaoPedido();
    liberacaoPedido.setES19_ID_PEDIDO(pedidoSolicitado.getPedidoId());
    liberacaoPedido.setES19_ID_USUARIO(Long.parseLong(USER_SISTEMA));
    liberacaoPedido.setES19_DT(new Date());
    liberacaoPedido.setES19_CHEQUE_REAPRESENTADO(ZERO);
    liberacaoPedido.setS19_CHEQUE_DEVOLVIDO(ZERO);
    liberacaoPedido.setES19_EXCEDEU_LIMITE(ZERO);
    liberacaoPedido.setES19_TITULO_VENCIDO(ZERO);
    liberacaoPedido.setES19_LIBERACAO_AUTOMATICA(Integer.parseInt(UM));
    liberacaoPedido.setES19_RESTRICAO_SEFAZ(clienteService.pedenciaSEFAZ(clienteId));
    /**Popular a Nova Tabela de log do JOB(Solicitação Pedido)*/
    SolicitacaoPedido solicitacaoPedido = new SolicitacaoPedido();
    solicitacaoPedido.setPedido(pedidoSolicitado.getPedidoId());
    solicitacaoPedido.setDT_Processamento(new Date());
    if (clienteService.pedenciaSEFAZ(clienteId) == Integer.valueOf(ZERO)) {
      solicitacaoPedido.setStatus(Status.S);
      solicitacaoPedido.setDescricao("PEDIDO LIBERADO!");
    } else {
      solicitacaoPedido.setStatus(Status.E);
      solicitacaoPedido.setDescricao("PEDIDO NÃO LIBERADO: Cliente com restrição na SEFAZ!");
    }
    processaPedido(liberacaoPedido, solicitacaoPedido);
  }

  private void compraBoletoAvista(PedidoSolicitadoDTO pedidoSolicitado, Long clienteId) {
    /**Popular DTO Liberação de Pedido(ESTOQUE.DBO.ES19_LIBERACAO_PEDIDO)*/
    LiberacaoPedido liberacaoPedido = new LiberacaoPedido();
    liberacaoPedido.setES19_ID_PEDIDO(pedidoSolicitado.getPedidoId());
    liberacaoPedido.setES19_ID_USUARIO(Long.parseLong(USER_SISTEMA));
    liberacaoPedido.setES19_DT(new Date());
    liberacaoPedido.setES19_CHEQUE_REAPRESENTADO(String.valueOf(clienteService.chequeReapresentado(clienteId)));
    liberacaoPedido.setS19_CHEQUE_DEVOLVIDO(String.valueOf(clienteService.chequeDevolvido(clienteId)));
    liberacaoPedido.setES19_EXCEDEU_LIMITE(String.valueOf(clienteService.excedeuLimiteSumarizadoDisponivel(pedidoSolicitado.getValorPedido(), clienteId)));
    liberacaoPedido.setES19_TITULO_VENCIDO(String.valueOf(clienteService.titulosVencidos(clienteId)));
    liberacaoPedido.setES19_LIBERACAO_AUTOMATICA(Integer.parseInt(UM));
    liberacaoPedido.setES19_RESTRICAO_SEFAZ(clienteService.pedenciaSEFAZ(clienteId));
    /**Popular a Nova Tabela de log do JOB(Solicitação Pedido)*/
    SolicitacaoPedido solicitacaoPedido = new SolicitacaoPedido();
    solicitacaoPedido.setPedido(pedidoSolicitado.getPedidoId());
    solicitacaoPedido.setDT_Processamento(new Date());
    if (clienteService.pedenciaSEFAZ(clienteId) == Integer.valueOf(ZERO)
        && clienteService.excedeuLimiteSumarizadoDisponivel(pedidoSolicitado.getValorPedido(), clienteId) == Integer.valueOf(ZERO)) {
      solicitacaoPedido.setStatus(Status.S);
      solicitacaoPedido.setDescricao("PEDIDO LIBERADO!");
    } else {
      solicitacaoPedido.setStatus(Status.E);
      solicitacaoPedido.setDescricao("PEDIDO NÃO LIBERADO!");
    }
    processaPedido(liberacaoPedido, solicitacaoPedido);
  }

  private void compraBoleto(PedidoSolicitadoDTO pedidoSolicitado, Long clienteId) {
    /**Popular DTO Liberação de Pedido(ESTOQUE.DBO.ES19_LIBERACAO_PEDIDO)*/
    LiberacaoPedido liberacaoPedido = new LiberacaoPedido();
    liberacaoPedido.setES19_ID_PEDIDO(pedidoSolicitado.getPedidoId());
    liberacaoPedido.setES19_ID_USUARIO(Long.parseLong(USER_SISTEMA));
    liberacaoPedido.setES19_DT(new Date());
    liberacaoPedido.setES19_CHEQUE_REAPRESENTADO(String.valueOf(clienteService.chequeReapresentado(clienteId)));
    liberacaoPedido.setS19_CHEQUE_DEVOLVIDO(String.valueOf(clienteService.chequeDevolvido(clienteId)));
    liberacaoPedido.setES19_EXCEDEU_LIMITE(String.valueOf(clienteService.excedeuLimiteSumarizadoDisponivel(pedidoSolicitado.getValorPedido(), clienteId)));
    liberacaoPedido.setES19_TITULO_VENCIDO(String.valueOf(clienteService.titulosVencidos(clienteId)));
    liberacaoPedido.setES19_LIBERACAO_AUTOMATICA(Integer.parseInt(UM));
    liberacaoPedido.setES19_RESTRICAO_SEFAZ(clienteService.pedenciaSEFAZ(clienteId));
    /**Popular a Nova Tabela de log do JOB(Solicitação Pedido)*/
    SolicitacaoPedido solicitacaoPedido = new SolicitacaoPedido();
    solicitacaoPedido.setPedido(pedidoSolicitado.getPedidoId());
    solicitacaoPedido.setDT_Processamento(new Date());
    if (clienteService.pedenciaSEFAZ(clienteId) == Integer.valueOf(ZERO)
        && clienteService.chequeReapresentado(clienteId) == Integer.valueOf(ZERO)
        && clienteService.chequeDevolvido(clienteId) == Integer.valueOf(ZERO)
        && clienteService.titulosVencidos(clienteId) == Integer.valueOf(ZERO)
        && clienteService.excedeuLimiteSumarizadoDisponivel(pedidoSolicitado.getValorPedido(), clienteId) == Integer.valueOf(ZERO)) {
      solicitacaoPedido.setStatus(Status.S);
      solicitacaoPedido.setDescricao("PEDIDO LIBERADO!");
    } else {
      solicitacaoPedido.setStatus(Status.E);
      solicitacaoPedido.setDescricao("PEDIDO NÃO LIBERADO!");
    }
    processaPedido(liberacaoPedido, solicitacaoPedido);
  }

  private void processaPedido(LiberacaoPedido liberacaoPedido, SolicitacaoPedido solicitacaoPedido) {
    if (log.isInfoEnabled()) {
      log.info("Processando pedido: " + solicitacaoPedido.getPedido());
    }
    pedidoSolicitadoMapper.salvarSolicitacaoPedidoMapper(solicitacaoPedido);
    pedidoSolicitadoMapper.salvarLiberacaoPedidoMapper(liberacaoPedido);
  }

}





