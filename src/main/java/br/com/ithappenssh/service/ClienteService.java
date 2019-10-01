package br.com.ithappenssh.service;

import br.com.ithappenssh.model.Cliente;

import java.math.BigDecimal;

public interface ClienteService {

  Cliente recuperarCliente(Long codigoCliente);

  int chequeReapresentado(Long clienteId);

  int chequeDevolvido(Long clienteId);

  int titulosVencidos(Long clienteId);

  int pedenciaSEFAZ(Long clienteId);

  int excedeuLimiteSumarizadoDisponivel(BigDecimal valorPedido, Long clienteId);

}
