package br.com.ithappenssh.mapper;

import br.com.ithappenssh.model.Cliente;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @author luis.o.oliveira
 * @implNote Interface de mapeamento
 */
@Mapper
@Repository
public interface ClienteMapper {

  Cliente recuperarClienteMapper(@Param("id") Long id);

  BigDecimal recuperarLimiteSumarizadoClienteEAssociadosMapper(@Param("id") Long id);

  BigDecimal recuperarLimiteResanteClienteEAssociadosMapper(@Param("id") Long id);

  int recuperarStatusSefazMapper(@Param("id") Long id);

  int recuperarChequeReapresentadoMapper(@Param("id") Long id);

  double recuperarChequeDevolvidoMapper(@Param("id") Long id);

  double recuperarTitulosVencidosMapper(@Param("id") Long id);

}
