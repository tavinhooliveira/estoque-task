package br.com.ithappenssh.mapper;

import br.com.ithappenssh.model.LiberacaoPedido;
import br.com.ithappenssh.model.SolicitacaoPedido;
import br.com.ithappenssh.model.dto.PedidoSolicitadoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PedidoSolicitadoMapper {

  List<PedidoSolicitadoDTO> listarPedidosSolicitados();

  SolicitacaoPedido buscarPedidosProcessadosMapper(@Param("id") Long id);

  boolean salvarLiberacaoPedidoMapper(@Param("liberacaoPedido") LiberacaoPedido liberacaoPedido);

  boolean salvarSolicitacaoPedidoMapper(@Param("solicitacaoPedido") SolicitacaoPedido solicitacaoPedido);

}
