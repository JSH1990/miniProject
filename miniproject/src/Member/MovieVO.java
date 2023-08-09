package Member;

import java.sql.Date;
public class MovieVO {
    private int MovieNo;
    private String MovieName;
    private String Director;
    private String MovieContent;
    private String MovieGenre;
    private Date MovieDate;

    public MovieVO(int movieNo, String movieName, String director, String movieContent, String movieGenre, Date movieDate) {
        MovieNo = movieNo;
        MovieName = movieName;
        Director = director;
        MovieContent = movieContent;
        MovieGenre = movieGenre;
        MovieDate = movieDate;
    }

    public MovieVO(int movieNo, String movieName) {
        MovieNo = movieNo;
        MovieName = movieName;
    }
    
    public int getMovieNo() {
        return MovieNo;
    }

    public void setMovieNo(int movieNo) {
        MovieNo = movieNo;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getMovieContent() {
        return MovieContent;
    }

    public void setMovieContent(String movieContent) {
        MovieContent = movieContent;
    }

    public String getMovieGenre() {
        return MovieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        MovieGenre = movieGenre;
    }

    public Date getMovieDate() {
        return MovieDate;
    }

    public void setMovieDate(Date movieDate) {
        MovieDate = movieDate;
    }
}