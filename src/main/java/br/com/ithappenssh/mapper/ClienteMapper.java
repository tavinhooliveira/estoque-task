package br.com.ithappenssh.mapper;

import br.com.ithappenssh.model.Cliente;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Mapper
@Repository
public interface ClienteMapper {

  Cliente recuperarClienteMapper(@Param("id") Long id);

  BigDecimal recuperarLimiteBaseDoClienteMapper(@Param("id") Long id);

  BigDecimal recuperarLimiteSumarizadoClienteEAssociadosMapper(@Param("id") Long id);

  int recuperarStatusSefazMapper(@Param("id") Long id);

  int recuperarChequeReapresentadoMapper(@Param("id") Long id);

  int recuperarChequeDevolvidoMapper(@Param("id") Long id);


}
