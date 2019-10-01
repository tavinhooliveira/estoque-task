package br.com.ithappenssh.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Component("dataUtil")
public class DataUtil {

  @Value("${config.format.data1}")
  private String formatData1;

  private final SimpleDateFormat dateFormat1 = new SimpleDateFormat(this.formatData1);

  public Date dateNow() {
    SimpleDateFormat formatoData1 = new SimpleDateFormat(this.formatData1);
    Date dateNow = null;
    try {
      dateNow = formatoData1.parse(new Date().toString());
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return dateNow;
  }

  public String getDateTime() {
    DateFormat dateFormat = new SimpleDateFormat(this.formatData1);
    Date date = new Date();
    return dateFormat.format(date);
  }

  public LocalDateTime localData() {
    return LocalDateTime.now();
  }

}
