package Member;

import java.util.List;
import java.util.Scanner;

import java.util.List;
import java.util.Scanner;

import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MemberDao dao = new MemberDao();
        LoginDao logdao = new LoginDao();
        MovieDAO movdao = new MovieDAO();
        PostDAO posdao = new PostDAO();
        ReplyDao repdao = new ReplyDao();

        while (true) {
            System.out.println("1. 기존회원 | 2. 신규회원 | 3. 종료");
            System.out.print("> ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("로그인을 하세요: ");
                    if (logdao.login() == 1) {
                        System.out.println("로그인 되었습니다.");
                        while (true) {
                            System.out.println("1. 영화정보 | 2. 게시판 | 3. 마이페이지 ");
                            System.out.print("> ");
                            int Boardchoice = sc.nextInt();
                            switch (Boardchoice) {
                                case 1: // 영화게시판
                                    System.out.println("======================= 영화 =======================");
                                    System.out.println("[1]전체 영화 [2]영화 검색 [3] 평점 보기 [4]종료");
                                    int sel = sc.nextInt();
                                    switch (sel) {
                                        case 1:
                                            List<MovieVO> list = movdao.MovieSelect();
                                            movdao.movieSelectPrint(list);
																						break;
                                        case 2:
																						System.out.println("[1]제목 검색 [2]키워드 검색 [3] 종료 ");
                                            int sel2 = sc.nextInt();
																						switch (sel2) {
                                                case 1: movdao.SearchMovieName(); break;
                                                case 2: movdao.keywordSearch(); break;
                                                case 3:
                                                    System.out.println("종료 합니다.");
                                                    System.exit(0);
                                                    break; }
                                        case 3:
                                            System.out.println("[1]평점 높은순 [2]영화별 평점 검색 [3]종료");
                                            int sel3 = sc.nextInt();
                                            switch (sel3) {
                                                case 1: movdao.RatingDesc(); break;
                                                case 2: movdao.RatingSearch(""); break;
                                                case 3: System.out.println("종료 합니다.");
                                                    System.exit(0);
                                            } break;
                                        case 4:
                                            System.out.println("종료 합니다.");
                                            System.exit(0);
                                    }
                                case 2: // 게시판
                                    while (true) {
                                        System.out.println("======================= 게시글 =======================");
                                        System.out.print("[1]게시글 조회 [2]게시글 등록 [3]게시글 검색 [4]종료 : ");
                                        int postSel = sc.nextInt();
                                        switch (postSel) {
                                           case 1: //게시글 조회
                                            System.out.print("1.전체 게시글 조회   2.게시판 별 게시글 조회 : ");
                                            int tempSel = sc.nextInt();
                                            switch (tempSel) {
                                                case 1: posdao.postSelect(); 
                                               System.out.println("1. 댓글 작성 2. 종료");
                                                        int tempSel2 = sc.nextInt();
                                                        switch (tempSel2) {
                                                            case 1: repdao.ReplyInsert(); break;
                                                            case 2: System.out.println("종료합니다."); return;
                                                        }
                                                case 2: posdao.boardSelectPost();break;
                                            }break;
                                        case 2:    //게시글 등록
                                            posdao.postInsert();
                                            break;
                                        case 3:      // 게시글 검색
                                            System.out.print("1.제목 검색   2.내용 검색   3.영화별 검색   4.작성자 검색 : ");
                                            int searchSel = sc.nextInt();
                                            switch (searchSel) {
                                                case 1: posdao.searchPostName(); break;
                                                case 2: posdao.searchPostCon(); break;
                                                case 3: posdao.searchPostMovie(); break;
                                                case 4: posdao.searchPostID(); break;
                                                } break;
                                            case 4: System.out.println("메뉴를 종료합니다"); return;
                                        } break;
                                        }
                                case 3: // 마이페이지
							System.out.println("======================= 마이페이지 =======================");
							System.out.println("[1] 나의 정보조회  [2] 나의 정보수정  [3] 나의 댓글 보기 [4] 나의 게시글 보기");

							int myself = sc.nextInt();
							switch (myself) {
							case 1: dao.memberSelect(); break;
							case 2: if (logdao.isMember() == 1) dao.memberUpdate(); break;
							case 3: dao.selectReply(); break;
							case 4: dao.selectPost(); break;
							} break;
						}
					}
				}
		          else if (logdao.login() != 1) {
	               System.out.println("아이디 및 비밀 번호를 다시 확인해주세요");
                    } break;
                case 2:
                    if (logdao.idCheck() == -1) {
                        dao.memberInsert();} break;
                case 3: System.out.println("프로그램을 종료합니다."); return;
                default: System.out.println("잘못된 입력입니다. 다시 입력해주세요."); break;
            }
        }
    }
}