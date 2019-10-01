package br.com.ithappenssh.model.dto;

import br.com.ithappenssh.model.Cliente;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author luis.o.oliveira
 * @implNote Model DTO, representação da query inicial de pedidos a ser processados!
 */
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