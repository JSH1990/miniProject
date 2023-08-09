package Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class RatingDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    Scanner sc = new Scanner(System.in);


// 평점 높은순 조회

    public void RatingDesc() {

        try {
            String sql = "SELECT MOVIE_NAME , R.MOVIE_NUMBER , TRUNC(AVG(RATING_MOVIE),1) AS 평점 " +
                    "FROM MOVIE M JOIN RATING R " +
                    "ON M.MOVIE_NUMBER = R.MOVIE_NUMBER " +
                    "GROUP BY MOVIE_NAME, R.MOVIE_NUMBER " +
                    "ORDER BY TRUNC(AVG(RATING_MOVIE),1) DESC";
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {

                System.out.println("     " + rs.getString("MOVIE_NAME")+"  ["+ rs.getInt("MOVIE_NUMBER")+"]  "+ rs.getDouble("평점") );

            }

            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



// 영화별 평점 조회
    public void RatingSearch() {
        System.out.print("제목을 검색하세요 : ");
        String name = sc.nextLine();
        try {
            String sql = "SELECT MOVIE_NAME , R.MOVIE_NUMBER , TRUNC(AVG(RATING_MOVIE),1) 평점 " +
                    "FROM MOVIE M JOIN RATING R " +
                    "ON M.MOVIE_NUMBER = R.MOVIE_NUMBER " +
                    "GROUP BY MOVIE_NAME, R.MOVIE_NUMBER " +
                    "HAVING MOVIE_NAME = ?";
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();


            while (rs.next()) {
                System.out.println("     " + rs.getString("MOVIE_NAME")+"  ["+ rs.getInt("MOVIE_NUMBER")+"]  "+ rs.getDouble("평점") );

            }
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}