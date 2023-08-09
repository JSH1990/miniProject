package Member;

import java.sql.Date;

public class ReplyVO {
    private int replyNo;
    private int postNo;
    private String replyContent;
    private Date replyDate;
    private String memberId;

    public ReplyVO(String replyContent, Date replyDate) {
        this.replyContent = replyContent;
        this.replyDate = replyDate;
    }


    public int getReplyNo() {
        return replyNo;
    }

    public void setReplyNo(int replyNo) {
        this.replyNo = replyNo;
    }

    public int getPostNo() {
        return postNo;
    }

    public void setPostNo(int postNo) {
        this.postNo = postNo;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}