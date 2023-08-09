package Member;


import java.sql.Date;

public class PostVO {
    private int p_num;
    private String board_name;
    private int m_num;
    private String post_name;
    private String post_con;
    private Date post_date;
    private String m_id;

    public PostVO(int p_num, String board_name, int m_num, String post_name, String post_con, Date post_date, String m_id) {
        this.p_num = p_num;
        this.board_name = board_name;
        this.m_num = m_num;
        this.post_name = post_name;
        this.post_con = post_con;
        this.post_date = post_date;
        this.m_id = m_id;
    }

    public int getP_num() {
        return p_num;
    }

    public void setP_num(int p_num) {
        this.p_num = p_num;
    }

    public String getBoard_name() {
        return board_name;
    }

    public void setBoard_name(String board_name) {
        this.board_name = board_name;
    }

    public int getM_num() {
        return m_num;
    }

    public void setM_num(int m_num) {
        this.m_num = m_num;
    }

    public String getPost_name() {
        return post_name;
    }

    public void setPost_name(String post_name) {
        this.post_name = post_name;
    }

    public String getPost_con() {
        return post_con;
    }

    public void setPost_con(String post_con) {
        this.post_con = post_con;
    }

    public Date getPost_date() {
        return post_date;
    }

    public void setPost_date(Date post_date) {
        this.post_date = post_date;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }
}