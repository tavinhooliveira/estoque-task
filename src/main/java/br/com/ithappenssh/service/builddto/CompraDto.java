package br.com.ithappenssh.service.builddto;

import br.com.ithappenssh.mapper.typehandler.Status;
import br.com.ithappenssh.model.LiberacaoPedido;
import br.com.ithappenssh.model.SolicitacaoPedido;
import br.com.ithappenssh.model.dto.PedidoSolicitadoDTO;
import br.com.ithappenssh.service.impl.ClienteServiceImpl;
import br.com.ithappenssh.service.impl.LiberacaoPedidoCreditoServiceImpl;
import br.com.ithappenssh.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author luis.o.oliveira
 * @implNote DTO | objeto de transferência de dados, criar um Objeto temporario
 * para inserção das tabelas: {ES19_LIBERACAO_PEDIDO e ES19B_SOLICITACAO_PEDIDO}
 */
@Slf4j
@Service
public class CompraDto {

  @Autowired
  private ClienteServiceImpl clienteServiceImpl;

  @Autowired
  private LiberacaoPedidoCreditoServiceImpl liberacaoPedidoCreditoServiceImpl;

  @Autowired
  private DataUtil dataUtil;

  private static final Integer ZERO = 0;
  private static final Integer UM = 1;

  @Value("${config.user.sistema}")
  private String usuarioSistema;

  @Value("${config.message.liberado}")
  private String liberado;

  @Value("${config.message.naoliberado}")
  private String naoLiberado;

  /**
   * Popula uma Model com os dados de liberação do pedido e solicitacão do Pedido
   *
   * @param pedidoSolicitado , clienteId
   * processa o pedido gravando nas tabelas {ES19_LIBERACAO_PEDIDO e ES19B_SOLICITACAO_PEDIDO}
   */
  public void compraAvistaDto(PedidoSolicitadoDTO pedidoSolicitado, Long clienteId) {
    LiberacaoPedido liberacaoPedido = new LiberacaoPedido();
    liberacaoPedido.setES19_ID_PEDIDO(pedidoSolicitado.getPedidoId());
    liberacaoPedido.setES19_ID_USUARIO(Long.parseLong(this.usuarioSistema));
    liberacaoPedido.setES19_DT(dataUtil.dateNow());
    liberacaoPedido.setES19_RESTRICAO_SEFAZ(clienteServiceImpl.pedenciaSEFAZ(clienteId));
    liberacaoPedido.setES19_CHEQUE_REAPRESENTADO(String.valueOf(clienteServiceImpl.chequeReapresentado(clienteId)));
    liberacaoPedido.setS19_CHEQUE_DEVOLVIDO(String.valueOf(clienteServiceImpl.chequeDevolvido(clienteId)));
    liberacaoPedido.setES19_EXCEDEU_LIMITE(String.valueOf(clienteServiceImpl.excedeuLimiteSumarizadoDisponivel(pedidoSolicitado.getValorPedido(), clienteId)));
    liberacaoPedido.setES19_TITULO_VENCIDO(String.valueOf(clienteServiceImpl.titulosVencidos(clienteId)));
    liberacaoPedido.setES19_LIBERACAO_AUTOMATICA(UM);
    /**Popular a Nova Tabela de log do JOB {ES19B_SOLICITACAO_PEDIDO}}*/
    SolicitacaoPedido solicitacaoPedido = new SolicitacaoPedido();
    solicitacaoPedido.setPedido(pedidoSolicitado.getPedidoId());
    solicitacaoPedido.setDTProcessamento(dataUtil.dateNow());
    if (clienteServiceImpl.pedenciaSEFAZ(clienteId) == ZERO) {
      solicitacaoPedido.setStatus(Status.SUCCESS.getDescricao());
      solicitacaoPedido.setDescricao(this.liberado);
    } else {
      solicitacaoPedido.setStatus(Status.ERROR.getDescricao());
      solicitacaoPedido.setDescricao(this.naoLiberado + " |Cliente com restrição na SEFAZ|");
    }
    liberacaoPedidoCreditoServiceImpl.processaPedido(liberacaoPedido, solicitacaoPedido);
  }

  /**
   * Popula uma Model com os dados de liberação do pedido e solicitacão do Pedido
   *
   * @param pedidoSolicitado , clienteId
   * processa o pedido gravando nas tabelas {ES19_LIBERACAO_PEDIDO e ES19B_SOLICITACAO_PEDIDO}
   */
  public void compraBoletoAvistaDto(PedidoSolicitadoDTO pedidoSolicitado, Long clienteId) {
    LiberacaoPedido liberacaoPedido = new LiberacaoPedido();
    liberacaoPedido.setES19_ID_PEDIDO(pedidoSolicitado.getPedidoId());
    liberacaoPedido.setES19_ID_USUARIO(Long.parseLong(this.usuarioSistema));
    liberacaoPedido.setES19_DT(dataUtil.dateNow());
    liberacaoPedido.setES19_RESTRICAO_SEFAZ(clienteServiceImpl.pedenciaSEFAZ(clienteId));
    liberacaoPedido.setES19_CHEQUE_REAPRESENTADO(String.valueOf(clienteServiceImpl.chequeReapresentado(clienteId)));
    liberacaoPedido.setS19_CHEQUE_DEVOLVIDO(String.valueOf(clienteServiceImpl.chequeDevolvido(clienteId)));
    liberacaoPedido.setES19_EXCEDEU_LIMITE(String.valueOf(clienteServiceImpl.excedeuLimiteSumarizadoDisponivel(pedidoSolicitado.getValorPedido(), clienteId)));
    liberacaoPedido.setES19_TITULO_VENCIDO(String.valueOf(clienteServiceImpl.titulosVencidos(clienteId)));
    liberacaoPedido.setES19_LIBERACAO_AUTOMATICA(UM);
    SolicitacaoPedido solicitacaoPedido = new SolicitacaoPedido();
    /**Popular a Nova Tabela de log do JOB {ES19B_SOLICITACAO_PEDIDO}}*/
    solicitacaoPedido.setPedido(pedidoSolicitado.getPedidoId());
    solicitacaoPedido.setDTProcessamento(dataUtil.dateNow());
    if (clienteServiceImpl.pedenciaSEFAZ(clienteId) == ZERO
        && clienteServiceImpl.excedeuLimiteSumarizadoDisponivel(pedidoSolicitado.getValorPedido(), clienteId) == ZERO) {
      solicitacaoPedido.setStatus(Status.SUCCESS.getDescricao());
      solicitacaoPedido.setDescricao(this.liberado);
    } else {
      solicitacaoPedido.setStatus(Status.ERROR.getDescricao());
      solicitacaoPedido.setDescricao(this.naoLiberado);
    }
    liberacaoPedidoCreditoServiceImpl.processaPedido(liberacaoPedido, solicitacaoPedido);
  }

  /**
   * Popula uma Model com os dados de liberação do pedido e solicitacão do Pedido
   *
   * @param pedidoSolicitado , clienteId
   * processa o pedido gravando nas tabelas {ES19_LIBERACAO_PEDIDO e ES19B_SOLICITACAO_PEDIDO}
   */
  public void compraBoletoDto(PedidoSolicitadoDTO pedidoSolicitado, Long clienteId) {
    LiberacaoPedido liberacaoPedido = new LiberacaoPedido();
    liberacaoPedido.setES19_ID_PEDIDO(pedidoSolicitado.getPedidoId());
    liberacaoPedido.setES19_ID_USUARIO(Long.parseLong(this.usuarioSistema));
    liberacaoPedido.setES19_DT(dataUtil.dateNow());
    liberacaoPedido.setES19_RESTRICAO_SEFAZ(clienteServiceImpl.pedenciaSEFAZ(clienteId));
    liberacaoPedido.setES19_CHEQUE_REAPRESENTADO(String.valueOf(clienteServiceImpl.chequeReapresentado(clienteId)));
    liberacaoPedido.setS19_CHEQUE_DEVOLVIDO(String.valueOf(clienteServiceImpl.chequeDevolvido(clienteId)));
    liberacaoPedido.setES19_EXCEDEU_LIMITE(String.valueOf(clienteServiceImpl.excedeuLimiteSumarizadoDisponivel(pedidoSolicitado.getValorPedido(), clienteId)));
    liberacaoPedido.setES19_TITULO_VENCIDO(String.valueOf(clienteServiceImpl.titulosVencidos(clienteId)));
    liberacaoPedido.setES19_LIBERACAO_AUTOMATICA(UM);
    /**Popular a Nova Tabela de log do JOB {ES19B_SOLICITACAO_PEDIDO}}*/
    SolicitacaoPedido solicitacaoPedido = new SolicitacaoPedido();
    solicitacaoPedido.setPedido(pedidoSolicitado.getPedidoId());
    solicitacaoPedido.setDTProcessamento(dataUtil.dateNow());
    if (clienteServiceImpl.pedenciaSEFAZ(clienteId) == ZERO
        && clienteServiceImpl.chequeReapresentado(clienteId) == ZERO
        && clienteServiceImpl.chequeDevolvido(clienteId) == ZERO
        && clienteServiceImpl.titulosVencidos(clienteId) == ZERO
        && clienteServiceImpl.excedeuLimiteSumarizadoDisponivel(pedidoSolicitado.getValorPedido(), clienteId) == ZERO) {
      solicitacaoPedido.setStatus(Status.SUCCESS.getDescricao());
      solicitacaoPedido.setDescricao(this.liberado);
    } else {
      solicitacaoPedido.setStatus(Status.ERROR.getDescricao());
      solicitacaoPedido.setDescricao(this.naoLiberado);
    }
    liberacaoPedidoCreditoServiceImpl.processaPedido(liberacaoPedido, solicitacaoPedido);
  }

}