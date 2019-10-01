package br.com.ithappenssh.service;

import br.com.ithappenssh.model.LiberacaoPedido;
import br.com.ithappenssh.model.SolicitacaoPedido;

public interface LiberacaoPedidoCreditoService {
  void solicitarPedido();

  void processaPedido(LiberacaoPedido liberacaoPedido, SolicitacaoPedido solicitacaoPedido) throws Exception;
}
