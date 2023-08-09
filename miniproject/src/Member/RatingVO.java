package Member;

public class RatingVO {
    private int MovieNo;
    private double Rating;
    private String Rcomment;
    private String Id;

    public RatingVO(int movieNo,double rating, String rcomment, String id) {
        MovieNo = movieNo;
        Rcomment = rcomment;
        Rating = rating;
        Id = id;
    }

    public RatingVO(double rating) {
        this.Rating = rating;
    }


    public int getMovieNo() {
        return MovieNo;
    }

    public void setMovieNo(int movieNo) {
        MovieNo = movieNo;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public String getRcomment() {
        return Rcomment;
    }

    public void setRcomment(String rcomment) {
        Rcomment = rcomment;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}