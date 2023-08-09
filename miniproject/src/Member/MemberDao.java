package Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;




public class MemberDao {
    LoginDao logdao = new LoginDao();
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    Scanner sc = new Scanner(System.in);

    public void memberSelectAll() { //전체조회
        String sql = "SELECT * FROM MEMBER";
        List<MemberVo> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();


            while (rs.next()) {
                MemberVo vo = new MemberVo(rs.getString("MEMBER_ID"), rs.getString("MEMBER_NAME"),
                        rs.getString("MEMBER_PWD"), rs.getString("MEMBER_EMAIL"), rs.getString("MEMBER_PHONE"));
                vo.setDate(rs.getDate("MEMBER_DATE"));
                list.add(vo);
            }
            for(MemberVo tmp : list) {
                System.out.println(tmp);
            }

            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void memberSelect() {
        if (logdao.login() == 1) {
            String sql = "SELECT * FROM MEMBER WHERE MEMBER_ID = ?";
            MemberVo result = null;
            try {
                String member_id = logdao.getMemberId();
                conn = Common.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, member_id);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    result = new MemberVo(rs.getString("MEMBER_ID"), rs.getString("MEMBER_NAME"),
                            rs.getString("MEMBER_PWD"), rs.getString("MEMBER_EMAIL"), rs.getString("MEMBER_PHONE"));
                    result.setDate(rs.getDate("MEMBER_DATE"));
                }
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Common.close(rs);
                Common.close(pstmt);
                Common.close(conn);
            }
        } else {
            System.out.println("로그인 실패");
        }
    }

    public void memberInsert() { //저장
        System.out.println("정보를 입력 하세요.");
        System.out.print("아이디를 입력 하세요 : ");
        String id = sc.next();
        System.out.print("이름 : ");
        String name = sc.next();
        System.out.print("비밀번호 : ");
        String pwd = sc.next();
        System.out.print("이메일 : ");
        String email = sc.next();
        System.out.print("핸드폰 번호 : ");
        String phone = sc.next();

        String sql = "INSERT INTO MEMBER(MEMBER_ID,MEMBER_NAME,MEMBER_PWD,MEMBER_EMAIL,MEMBER_PHONE,MEMBER_DATE) VALUES (?,?,?,?,?,NOW())";

        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, pwd);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pstmt);
        Common.close(conn);
        System.out.println("회원 등록되었습니다.");
    }

    public void memberDelete() { //삭제
        System.out.print("삭제할 이름을 입력 하세요 : ");
        String word = sc.next();
        String sql = "DELETE FROM MEMBER WHERE MEMBER_NAME = ?";

        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, word);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);
        System.out.println(word + "님이 삭제되었습니다.");
    }

    public void memberUpdate() { //변경
        System.out.print("변경할 회원의 이름을 입력 하세요 : ");
        String name = sc.next();
        System.out.print("이메일 : ");
        String email = sc.next();
        System.out.print("핸드폰번호 : ");
        String phone = sc.next();

        String sql = "UPDATE MEMBER SET MEMBER_EMAIL = ?, MEMBER_PHONE = ? WHERE MEMBER_NAME = ?";

        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, phone);
            pstmt.setString(3, name);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);
        System.out.println("회원 정보가 변경되었습니다.");
    }
public void selectReply() {     //내가 쓴 댓글 조회하기
        System.out.print("비밀번호를 입력하세요 : ");
        String m_pwd = sc.next();
        try {
            String sql = "SELECT BOARD_NAME, R.REPLY_NUMBER, POST_NAME, REPLY_CONTENT, R.MEMBER_ID, REPLY_DATE FROM POST P, MEMBER M, REPLY R WHERE P.POST_NUMBER = R.POST_NUMBER AND R.MEMBER_ID = M.MEMBER_ID AND M.MEMBER_PWD = ? ORDER BY P.POST_NUMBER";
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, m_pwd);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("     " + rs.getString("BOARD_NAME") + "  |  " + rs.getInt("REPLY_NUMBER") + "     <" + rs.getString("REPLY_CONTENT") + ">     원문 게시글 : " + rs.getString("POST_NAME") + "  |  " + rs.getDate("REPLY_DATE"));
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(pstmt);
        Common.close(conn);
    }
	
	public void updateReply () {
        System.out.print("비밀번호를 입력하세요 : ");
        String m_pwd = sc.next();
        try {
            String sql = "SELECT BOARD_NAME, R.REPLY_NUMBER, POST_NAME, REPLY_CONTENT, R.MEMBER_ID, REPLY_DATE FROM POST P, MEMBER M, REPLY R WHERE P.POST_NUMBER = R.POST_NUMBER AND R.MEMBER_ID = M.MEMBER_ID AND M.MEMBER_PWD = ? ORDER BY P.POST_NUMBER";
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, m_pwd);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                System.out.println("     " + rs.getString("BOARD_NAME") + "  |  " + rs.getInt("REPLY_NUMBER") + "     <" + rs.getString("REPLY_CONTENT") + ">     원문 게시글 : " + rs.getString("POST_NAME") + "  |  " + rs.getDate("REPLY_DATE"));
                System.out.println();
            }
            System.out.print("변경할 댓글 번호를 선택하세요 : ");
            int replyNum = sc.nextInt();
            sc.nextLine();
            System.out.print("변경할 댓글 내용을 입력하세요 : ");
            String replyCon = sc.nextLine();
            String sql2 = "UPDATE REPLY SET REPLY_CONTENT = ? WHERE REPLY_NUMBER = ? ";
            System.out.println("댓글을 수정하였습니다.");
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, replyCon);
            pstmt.setInt(2, replyNum);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(pstmt);
        Common.close(conn);
    }
	public void selectPost() {
            System.out.print("비밀번호를 입력하세요 : ");
            String m_pwd = sc.next();
            try {
                String sql = "SELECT BOARD_NAME ,POST_NUMBER, POST_NAME, POST_CONTENT, POST_DATE FROM POST P, MEMBER M WHERE P.MEMBER_ID = M.MEMBER_ID AND MEMBER_PWD = ? ORDER BY POST_NUMBER";
                conn = Common.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, m_pwd);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    System.out.println("     " + rs.getString("BOARD_NAME") + "  |  " + rs.getString("POST_NUMBER") + ".     <" + rs.getString("POST_NAME") + ">     " + rs.getString("POST_CONTENT") + "  |  " + rs.getDate("POST_DATE"));
                    System.out.println();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

}