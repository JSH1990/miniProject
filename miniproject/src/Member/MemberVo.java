package Member;

import java.util.Date;

public class MemberVo {
    private String id;
    private String name;
    private String pwd;
    private String email;
    private String phone;
    private Date date;

    MemberVo(){};
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MemberVo(String id, String name, String pwd, String email, String phone) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "회원정보{" +
                "아이디='" + id + '\'' +
                ", 이름='" + name + '\'' +
                ", 비밀번호='" + pwd + '\'' +
                ", 이메일='" + email + '\'' +
                ", 전화번호='" + phone + '\'' +
                ", 날짜=" + date +
                '}';
    }
}