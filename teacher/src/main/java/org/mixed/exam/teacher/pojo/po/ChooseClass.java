package org.mixed.exam.teacher.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChooseClass {

  private String sname;
  private long cno;
  private String data;
  private Class clazz;

  public Class getClazz() {
    return clazz;
  }

  public void setClazz(Class clazz) {
    this.clazz = clazz;
  }

  public String getSname() {
    return sname;
  }

  public void setSname(String sname) {
    this.sname = sname;
  }


  public long getCno() {
    return cno;
  }

  public void setCno(long cno) {
    this.cno = cno;
  }


  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

}
