package Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostDAO {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pStmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);

    public void postSelect() {
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT P.POST_NUMBER, M.MOVIE_NAME, P.POST_NAME, P.POST_CONTENT, P.MEMBER_ID AS P_MEMBER_ID, P.POST_DATE, R.REPLY_CONTENT, R.MEMBER_ID AS R_MEMBER_ID " +
                    "FROM POST P " +
                    "INNER JOIN MOVIE M ON P.MOVIE_NUMBER = M.MOVIE_NUMBER " +
                    "LEFT JOIN REPLY R ON P.POST_NUMBER = R.POST_NUMBER " +
                    "ORDER BY P.POST_NUMBER";
            rs = stmt.executeQuery(sql);
            int previousPostNumber = -1;
            while (rs.next()) {
                int currentPostNumber = rs.getInt("POST_NUMBER");
                if (currentPostNumber != previousPostNumber) {
                    System.out.print("     " + currentPostNumber + ".     <" + rs.getString("MOVIE_NAME") + ">    [" + rs.getString("POST_NAME") + "]    " + rs.getString("POST_CONTENT") + "  |  " + rs.getString("P_MEMBER_ID") + "  |  " + rs.getDate("POST_DATE") + "\n            댓글 : ");
                    if (rs.getString("REPLY_CONTENT") != null) {
                        System.out.print(rs.getString("REPLY_CONTENT") + "  |  " + rs.getString("R_MEMBER_ID"));
                        while (rs.next()) {
                            if (rs.getInt("POST_NUMBER") != currentPostNumber) {
                                break;
                            }
                            if (rs.getString("REPLY_CONTENT") != null) {
                                System.out.print(", " + rs.getString("REPLY_CONTENT") + "  |  " + rs.getString("R_MEMBER_ID"));
                            }
                        }
                    }
                    System.out.println();
                    previousPostNumber = currentPostNumber;
                }
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
public void boardSelectPost() {
        try {
                System.out.println("게시판을 선택하세요");
                List<String> board_name = boardList();
                int boardCnt = 1;
                for (String e : board_name) {
                    System.out.print("[" + boardCnt++ + "]" + e + " ");
                }
                System.out.println();
                int tempSel2 = sc.nextInt();
                selBoard(board_name, tempSel2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postInsert() {
      try{
            String sql = "SELECT MOVIE_NUMBER, MOVIE_NAME, MOVIE_DIRECTOR FROM MOVIE";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while(rs.next()) {
                System.out.println("     " + rs.getInt("MOVIE_NUMBER") + "     <" + rs.getString("MOVIE_NAME") + ">     " + rs.getString("MOVIE_DIRECTOR"));
            }
						System.out.println("게시판을 선택하세요");
            List<String> board_name = boardList();
            int boardCnt = 1;
            for (String e : board_name) {
                System.out.print("[" + boardCnt++ + "]" + e + " ");
            }
						System.out.println();
            int tempSel2 = sc.nextInt();
            String name = board_name.get(tempSel2-1);

			  System.out.print("아이디를 입력하세요 : ");
        String member_id = sc.next();
        System.out.print("영화 번호를 입력하세요 : ");
        int movie_number = sc.nextInt();
				sc.nextLine();
        System.out.print("게시글 제목을 입력하세요 : ");
        String post_name = sc.nextLine();
        System.out.print("게시글 내용을 입력하세요 : ");
        String post_content = sc.nextLine();

				String sql2 = "INSERT INTO POST(BOARD_NAME, POST_NUMBER, MOVIE_NUMBER, MEMBER_ID, POST_NAME, POST_CONTENT, POST_DATE) values (?,POST_NUMBER.NEXTVAL,?,?,?,?,SYSDATE)";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql2);
            pStmt.setString(1, name);
            pStmt.setInt(2, movie_number);
						pStmt.setString(3, member_id);
            pStmt.setString(4, post_name);
            pStmt.setString(5, post_content);
            pStmt.executeUpdate();
		} catch (Exception e) {
            e.printStackTrace();
      }
			Common.close(rs);
        Common.close(pStmt);
        Common.close(conn);
        System.out.println("게시글을 등록하였습니다");
    } 

    public void postUpdate() {
        System.out.print("변경할 게시글의 번호를 입력하세요 : ");
        int p_num = sc.nextInt();
        System.out.print("게시글 제목 : ");
        String post_name = sc.next();
        System.out.print("글 내용 : ");
        String post_con = sc.next();

        try {
            String sql = "UPDATE POST SET POST_NAME = ?, POST_CONTENT = ? WHERE POST_NUMBER = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, post_name);
            pStmt.setString(2, post_con);
            pStmt.setInt(3, p_num);
            pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        System.out.println("게시글을 수정하였습니다");
    }

    public List<String> boardList() {
        List<String> board_name = new ArrayList<>();
        conn = null;
        pStmt = null;
        rs = null;
        try {
            String sql = "SELECT * FROM BOARD";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                board_name.add(rs.getString("BOARD_NAME"));
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return board_name;
    }

    public void selBoard(List<String> board_name, int sel) {
        try {
            String sql = "SELECT BOARD_NAME,POST_NUMBER ,MOVIE_NAME, POST_NAME, POST_CONTENT, MEMBER_ID, POST_DATE FROM POST P, MOVIE M WHERE P.MOVIE_NUMBER = M.MOVIE_NUMBER AND BOARD_NAME = ? ORDER BY POST_NUMBER";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            String name = board_name.get(sel - 1);
            pStmt.setString(1, name);
            rs = pStmt.executeQuery();
            System.out.println(name + "게시판의 글을 조회합니다");
            while (rs.next()) {
                System.out.println("     " + rs.getString("BOARD_NAME") + "  |  " + rs.getInt("POST_NUMBER") + ".    " + rs.getString("MOVIE_NAME") + "    [" + rs.getString("POST_NAME") + "]    " + rs.getString("POST_CONTENT") + "     " + rs.getString("MEMBER_ID") + "  |  " + rs.getDate("POST_DATE"));
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchPostName() {
        System.out.print("제목을 검색하세요 : ");
        String name = sc.next();

        try {
           String sql = "SELECT BOARD_NAME,POST_NUMBER ,MOVIE_NAME, POST_NAME, POST_CONTENT, MEMBER_ID, POST_DATE FROM POST P, MOVIE M WHERE P.MOVIE_NUMBER = M.MOVIE_NUMBER AND POST_NAME LIKE '%'||?||'%' ORDER BY POST_NUMBER";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, name);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                System.out.println("     " + rs.getString("BOARD_NAME") + "  |  " + rs.getInt("POST_NUMBER") + ".     <" + rs.getString("MOVIE_NAME") + ">     [" + rs.getString("POST_NAME") + "]    " + rs.getString("POST_CONTENT") + "     " + rs.getString("MEMBER_ID") + "  |  " + rs.getDate("POST_DATE"));
                System.out.println();
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchPostCon() {
        System.out.print("검색할 내용을 입력하세요 : ");
        String p_con = sc.next();
        try {
            String sql = "SELECT BOARD_NAME,POST_NUMBER ,MOVIE_NAME, POST_NAME, POST_CONTENT, MEMBER_ID, POST_DATE FROM POST P, MOVIE M WHERE P.MOVIE_NUMBER = M.MOVIE_NUMBER AND POST_CONTENT LIKE '%'||?||'%' ORDER BY POST_NUMBER";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, p_con);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                System.out.println("     " + rs.getString("BOARD_NAME") + "  |  " + rs.getInt("POST_NUMBER") + ".     <" + rs.getString("MOVIE_NAME") + ">     [" + rs.getString("POST_NAME") + "]    " + rs.getString("POST_CONTENT") + "     " + rs.getString("MEMBER_ID") + "  |  " + rs.getDate("POST_DATE"));
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchPostID() {
        System.out.print("검색할 아이디를 입력하세요 : ");
        String mem_id = sc.next();

        try {
            String sql = "SELECT BOARD_NAME,POST_NUMBER ,MOVIE_NAME, POST_NAME, POST_CONTENT, MEMBER_ID, POST_DATE FROM POST P, MOVIE M WHERE P.MOVIE_NUMBER = M.MOVIE_NUMBER AND MEMBER_ID = ? ORDER BY POST_NUMBER";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, mem_id);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                System.out.println("     " + rs.getString("BOARD_NAME") + "  |  " + rs.getInt("POST_NUMBER") + ".     <" + rs.getString("MOVIE_NAME") + ">     [" + rs.getString("POST_NAME") + "]    " + rs.getString("POST_CONTENT") + "     " + rs.getString("MEMBER_ID") + "  |  " + rs.getDate("POST_DATE"));
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
		public void searchPostMovie() {
            System.out.print("검색할 영화 제목을 입력하세요 : ");
            String mov_name = sc.next();
            try {
                String sql = "SELECT BOARD_NAME,POST_NUMBER ,MOVIE_NAME, POST_NAME, POST_CONTENT, MEMBER_ID, POST_DATE FROM POST P, MOVIE M WHERE P.MOVIE_NUMBER = M.MOVIE_NUMBER  AND MOVIE_NAME LIKE '%'||?||'%' ORDER BY POST_NUMBER";
                conn = Common.getConnection();
                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, mov_name);
                rs = pStmt.executeQuery();
                while (rs.next()) {
                    System.out.println("     " + rs.getString("BOARD_NAME") + "  |  " + rs.getInt("POST_NUMBER") + ".     <" + rs.getString("MOVIE_NAME") + ">     [" + rs.getString("POST_NAME") + "]    " + rs.getString("POST_CONTENT") + "     " + rs.getString("MEMBER_ID") + "  |  " + rs.getDate("POST_DATE"));
                    System.out.println();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
    }
}