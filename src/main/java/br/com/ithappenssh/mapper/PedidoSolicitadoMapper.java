package br.com.ithappenssh.mapper;

import br.com.ithappenssh.model.LiberacaoPedido;
import br.com.ithappenssh.model.SolicitacaoPedido;
import br.com.ithappenssh.model.dto.PedidoSolicitadoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author luis.o.oliveira
 * @implNote Interface de mapeamento
 */
@Mapper
@Repository
public interface PedidoSolicitadoMapper {

  List<PedidoSolicitadoDTO> listarPedidosSolicitados();

  List<PedidoSolicitadoDTO> listarPedidosSolicitadosPorCliente(@Param("id") Long id);

  List<SolicitacaoPedido> listaPedidosSolicitadosBloqueadosPorCliente(@Param("id") Long id);

  SolicitacaoPedido buscarPedidosProcessadosMapper(@Param("id") Long id);

  boolean salvarSolicitacaoPedidoMapper(@Param("solicitacaoPedido") SolicitacaoPedido solicitacaoPedido);

  boolean salvarLiberacaoPedidoMapper(@Param("liberacaoPedido") LiberacaoPedido liberacaoPedido);


}
