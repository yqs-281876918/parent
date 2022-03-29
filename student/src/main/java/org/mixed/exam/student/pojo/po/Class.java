package org.mixed.exam.student.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mixed.exam.auth.api.po.Users;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Class {

  private long cno;
  private String cname;
  private String invitation;
  private String creator;
  private Users users;

  public Users getUsers() {
    return users;
  }

  public void setUsers(Users users) {
    this.users = users;
  }

  public long getCno() {
    return cno;
  }

  public void setCno(long cno) {
    this.cno = cno;
  }


  public String getCname() {
    return cname;
  }

  public void setCname(String cname) {
    this.cname = cname;
  }


  public String getInvitation() {
    return invitation;
  }

  public void setInvitation(String invitation) {
    this.invitation = invitation;
  }


  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

}
