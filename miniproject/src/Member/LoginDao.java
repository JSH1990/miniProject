package Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class LoginDao {

    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);
    private String memberId;

    public int idCheck() {
        System.out.println("아이디를 입력해주세요");
        String Id = sc.next();
        System.out.println("비밀번호를 입력해주세요");
        String Pwd = sc.next();

        try {
            String sql = "SELECT MEMBER_PWD FROM MEMBER WHERE MEMBER_ID = ?";
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getString(1).equals(Pwd)) ;
                System.out.println("존재하는 아이디가 있습니다.");
                return 1;

            }
            System.out.println("존재하는 아이디가 없습니다.");
            return -1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -2;

    }

    public int  login() {
        System.out.println("아이디를 입력해주세요");
        String Id = sc.next();
        System.out.println("비밀번호를 입력해주세요");
        String Pwd = sc.next();


        try {
            String sql = "SELECT MEMBER_PWD FROM MEMBER WHERE MEMBER_ID = ? AND MEMBER_PWD = ?";
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Id);
            pstmt.setString(2, Pwd);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("로그인 성공");
                memberId = Id;
                return 1;

            }
            System.out.println("로그인 실패");
            return -1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -2;

    }
    
    public int  isMember() {
        System.out.println("개인정보확인을 위해 다시한번 아이디를 입력해주세요");
        String Id = sc.next();
        System.out.println("비밀번호를 입력해주세요");
        String Pwd = sc.next();


        try {
            String sql = "SELECT MEMBER_PWD FROM MEMBER WHERE MEMBER_ID = ? AND MEMBER_PWD = ?";
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Id);
            pstmt.setString(2, Pwd);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("로그인 성공");
                memberId = Id;
                return 1;

            }
            System.out.println("로그인 실패");
            return -1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -2;

    }
    public String getMemberId(){
        return memberId;
    }
}