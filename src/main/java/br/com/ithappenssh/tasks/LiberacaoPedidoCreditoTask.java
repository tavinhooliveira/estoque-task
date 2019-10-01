package br.com.ithappenssh.tasks;

import br.com.ithappens.lib.task.annotation.Tarefa;
import br.com.ithappens.lib.task.model.Argumentos;
import br.com.ithappens.lib.task.service.task.IEngTask;
import br.com.ithappenssh.service.impl.LiberacaoPedidoCreditoServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.repository.TaskExecution;

@Slf4j
@Tarefa("liberacaoPedidoCreditoTask")
@RequiredArgsConstructor
public class LiberacaoPedidoCreditoTask implements IEngTask {

  @Autowired
  private LiberacaoPedidoCreditoServiceImpl liberacaoPedidoCreditoServiceImpl;

  @Override
  public void executar(Argumentos args) {
    try {
      if (log.isInfoEnabled()) {
        log.info("Iniciando processo de liberação de pedido...");
      }
      liberacaoPedidoCreditoServiceImpl.solicitarPedido();
    } catch (Exception e) {
      if (log.isErrorEnabled()) {
        log.error(e.getMessage());
      }
    }

  }

  @Override
  public void preTarefa(TaskExecution taskExecution) {
  }

  @Override
  public void posTarefa(TaskExecution taskExecution) {
  }

  @Override
  public void tarefaFalha(TaskExecution taskExecution, Throwable cause) {
  }

  @Override
  public void falhaNegocio(Throwable cause) {
  }
}
