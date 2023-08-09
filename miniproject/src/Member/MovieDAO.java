package Member;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    Scanner sc = new Scanner(System.in);
   
    // 전체 영화 정보
    public List<MovieVO> MovieSelect() {
        List<MovieVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM MOVIE";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int MovieNo = rs.getInt("MOVIE_NUMBER");
                String MovieName = rs.getString("MOVIE_NAME");
                String Director = rs.getString("MOVIE_DIRECTOR");
                String MovieContent = rs.getString("MOVIE_CONTENT");
                String MovieGenre = rs.getString("MOVIE_GENRE");
                Date MovieDate = rs.getDate("MOVIE_DATE");
                MovieVO vo = new MovieVO(MovieNo, MovieName, Director, MovieContent, MovieGenre, MovieDate);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // 영화 제목 검색
    public void SearchMovieName() {
        System.out.print("검색할 영화 제목을 검색하세요 : ");
        String movieName = sc.next();
        try {
            String sql = "SELECT MOVIE_NUMBER, MOVIE_NAME, MOVIE_DIRECTOR, MOVIE_CONTENT, MOVIE_GENRE, MOVIE_DATE FROM MOVIE WHERE MOVIE_NAME = ?";
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movieName);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt("MOVIE_NUMBER")+ "   <" + rs.getString("MOVIE_NAME") + ">   " + rs.getString("MOVIE_DIRECTOR") + "   [" + rs.getString("MOVIE_CONTENT") + "]   " + rs.getString("MOVIE_GENRE") + "   " + rs.getDate("MOVIE_DATE"));
            }
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 영화 키워드검색 (값이 여러개면 4번 반복됨)
    public void keywordSearch() {
        System.out.print("검색할 영화 키워드를 입력하세요 : ");
        String keyword = sc.next();
        try {
            String sql = "SELECT MOVIE_NUMBER, MOVIE_NAME, MOVIE_DIRECTOR, MOVIE_CONTENT, MOVIE_GENRE, MOVIE_DATE FROM MOVIE "
                    + "WHERE MOVIE_NAME LIKE  ? "
                    + "OR MOVIE_DIRECTOR LIKE ?"
                    + "   OR MOVIE_CONTENT LIKE  ? "
                    + "   OR MOVIE_GENRE LIKE  ?";
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            String kwd = "%" + keyword + "%";
            pstmt.setString(1, kwd);
            pstmt.setString(2, kwd);
            pstmt.setString(3, kwd);
            pstmt.setString(4, kwd);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("MOVIE_NUMBER")+ "   <" + rs.getString("MOVIE_NAME") + ">   " + rs.getString("MOVIE_DIRECTOR") + "   [" + rs.getString("MOVIE_CONTENT") + "]   " + rs.getString("MOVIE_GENRE") + "   " + rs.getDate("MOVIE_DATE"));
            }
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 평점 높은순 조회
    public List<Object[]> RatingDesc() {
        List<Object[]> list = new ArrayList<>();
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
                String MovieName = rs.getString("MOVIE_NAME");
                int MovieNo = rs.getInt("MOVIE_NUMBER");
                MovieVO movie = new MovieVO(MovieNo, MovieName);

                double rating = rs.getDouble("평점");
                RatingVO ratingScore = new RatingVO(rating);

                Object[] ratingObj = new Object[2];
                ratingObj[0] = movie;
                ratingObj[1] = ratingScore;
                list.add(ratingObj);
            }
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // 영화별 평점 조회
    public List<Object[]> RatingSearch(String moviename) {
        List<Object[]> list = new ArrayList<>();
        try {
            String sql = "SELECT MOVIE_NAME , R.MOVIE_NUMBER , TRUNC(AVG(RATING_MOVIE),1) 평점 " +
                    "FROM MOVIE M JOIN RATING R " +
                    "ON M.MOVIE_NUMBER = R.MOVIE_NUMBER " +
                    "GROUP BY MOVIE_NAME, R.MOVIE_NUMBER " +
                    "HAVING MOVIE_NAME = ?";
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            String name = moviename;
            pstmt.setString(1, moviename);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String MovieName = rs.getString("MOVIE_NAME");
                int MovieNo = rs.getInt("MOVIE_NUMBER");
                MovieVO movie = new MovieVO(MovieNo, MovieName);
                double rating = rs.getDouble("평점");
                RatingVO ratingScore = new RatingVO(rating);
                Object[] searchObj = new Object[2];
                searchObj[0] = movie;
                searchObj[1] = ratingScore;
                list.add(searchObj);
            }
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void movieSelectPrint (List < MovieVO > list) {
        for (MovieVO e : list) {
            System.out.println("영화 번호 : " + e.getMovieNo());
            System.out.println("영화 제목 : " + e.getMovieName());
            System.out.println("감독 : " + e.getDirector());
            System.out.println("줄거리 : " + e.getMovieContent());
            System.out.println("장르 : " + e.getMovieGenre());
            System.out.println("개봉일 : " + e.getMovieDate());
            System.out.println("------------------------------------");
        }
    }
}