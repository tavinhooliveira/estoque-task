package br.com.ithappenssh.model.dto;

import br.com.ithappenssh.model.Cliente;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PedidoSolicitadoDTO {
  private Long pedidoId;
  private Long tipoPedido;
  private Long statusPedido;
  private BigDecimal valorPedido;
  private Long tipoMovimento;
  private Long formaPagamento;
  private Cliente cliente;
}