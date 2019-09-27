package br.com.ithappenssh.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("default, deleteMe")
public class DeleteMeMapperTest {

  @Autowired
  private DeleteMeMapper deleteMeMapper;

  @Test
  public void recuperar() {
    DeleteMe deleteMe = deleteMeMapper.recuperar();
    assertEquals(Long.valueOf(1), deleteMe.getId());
  }
}