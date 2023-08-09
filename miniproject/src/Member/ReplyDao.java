package Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ReplyDao {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);
    String name = null;

    public void ReplyInsert() {
        LoginDao loginDao = new LoginDao();

        int loginResult = loginDao.login();
        if (loginResult == 1) {
            try {
                String member_id = loginDao.getMemberId();
                System.out.println("회원ID : " + member_id);
                System.out.println("게시글 번호를 입력하세요 : ");
                int post_num = sc.nextInt();
                System.out.println("댓글을 작성하세요 : ");
                sc.nextLine();
                String reply = sc.nextLine();

                String sql = "INSERT INTO REPLY(REPLY_NUMBER, POST_NUMBER, REPLY_CONTENT, REPLY_DATE, MEMBER_ID) VALUES(REPLY_NUMBER.NEXTVAL, ?, ?, SYSDATE, ?)";
                conn = Common.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, post_num);
                pstmt.setString(2, reply);
                pstmt.setString(3, member_id);
                pstmt.executeUpdate();


            } catch (Exception e) {
                e.printStackTrace();
            }
            Common.close(conn);
            Common.close(pstmt);
            System.out.println("댓글을 등록하였습니다.");
        } else {
            System.out.println("로그인에 실패하였습니다.");
        }
    }
}