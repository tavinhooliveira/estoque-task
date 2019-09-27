package br.com.ithappenssh.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author luis.o.oliveira
 * @apiNote Classe de configuração dos ambiente de Produção e Homologação
 */
@Configuration
@MapperScan("br.com.ithappenssh.mapper")
public class DBConfig {

  @Bean
  @Primary
  @ConfigurationProperties("spring.datasource")
  public DataSource dataSourceSQLServer() {
    return DataSourceBuilder.create().build();
  }

  @Bean("sqlServerTransactionManager")
  public DataSourceTransactionManager cadastroDataSourceTransactionManager(@Qualifier("dataSourceSQLServer") DataSource dataSource) throws Exception {
    return new DataSourceTransactionManager(dataSource);
  }

}