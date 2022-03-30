package org.mixed.exam.student.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mixed.exam.auth.api.po.Users;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChooseClass {

  private String sname;
  private long cno;
  private String data;
  private Class clazz;
  private long studentSum;
  private Users users;

  public long getStudentSum() {
    return studentSum;
  }

  public void setStudentSum(long studentSum) {
    this.studentSum = studentSum;
  }

  public Users getUsers() {
    return users;
  }

  public void setUsers(Users users) {
    this.users = users;
  }

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
