package org.koreait;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
    static List<Article> articles = new ArrayList<>();
    static List<Member> members = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("==프로그램 시작==");
        makeTestData();
        int lastArticleId = 3;
        while (true) {
            System.out.print("명령어) ");
            String cmd = sc.nextLine().trim();
            int a = cmd.length();
            int b = cmd.replace(" ", "").length();
            int c = a - b;
            if (cmd.length() == 0) {
                System.out.println("명령어를 입력하세요");
                continue;
            }
            if (cmd.equals("exit")) {
                break;
            }
            if (cmd.equals("article write")) {
                System.out.println("==게시글 작성==");
                int id = lastArticleId + 1;
                String regDate = Util.getNow();
                String updateDate = regDate;
                System.out.print("제목 : ");
                String title = sc.nextLine();
                System.out.print("내용 : ");
                String body = sc.nextLine();

                Article article = new Article(id, regDate, updateDate, title, body);
                articles.add(article);

                System.out.println(id + "번 글이 생성되었습니다");
                lastArticleId++;
            }else if (cmd.substring(0,cmd.lastIndexOf(" ")).equals("article list")&&c == 2) {
                System.out.println("==게시글 목록==");
                if (articles.size() == 0) {
                    System.out.println("아무것도 없어");
                } else {
                    String find = cmd.substring(cmd.lastIndexOf(" "));
                    System.out.println("  번호   /    날짜   /   제목   /   내용   ");
                    for (int i = articles.size() - 1; i >= 0; i--) {
                        Article article = articles.get(i);
                        if ((" " + article.getTitle()).equals(find)) {
                            article = articles.get(i);
                            if (Util.getNow().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                                System.out.printf("  %d   /   %s      /   %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody());
                            } else {
                                System.out.printf("  %d   /   %s      /   %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody());
                            }
                        }
                    }
                }
            }
            else if (cmd.equals("article list")) {
                System.out.println("==게시글 목록==");
                if (articles.size() == 0) {
                    System.out.println("아무것도 없어");
                }
                else {
                    System.out.println("  번호   /    날짜   /   제목   /   내용   ");
                    for (int i = articles.size() - 1; i >= 0; i--) {
                        Article article = articles.get(i);
                        if (Util.getNow().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                            System.out.printf("  %d   /   %s      /   %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody());
                        } else {
                            System.out.printf("  %d   /   %s      /   %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody());
                        }

                    }
                }
            } else if (cmd.startsWith("article detail")) {
                System.out.println("==게시글 상세보기==");
                int id = Integer.parseInt(cmd.split(" ")[2]);
                Article foundArticle = getArticleById(id);
                System.out.println("번호 : " + foundArticle.getId());
                System.out.println("작성날짜 : " + foundArticle.getRegDate());
                System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
                System.out.println("제목 : " + foundArticle.getTitle());
                System.out.println("내용 : " + foundArticle.getBody());
            } else if (cmd.startsWith("article delete")) {
                System.out.println("==게시글 삭제==");
                int id = Integer.parseInt(cmd.split(" ")[2]);
                Article foundArticle = getArticleById(id);
                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                articles.remove(foundArticle);
                System.out.println(id + "번 게시글이 삭제되었습니다");
            } else if (cmd.startsWith("article modify")) {
                System.out.println("==게시글 수정==");
                int id = Integer.parseInt(cmd.split(" ")[2]);
                Article foundArticle = getArticleById(id);
                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                System.out.println("기존 제목 : " + foundArticle.getTitle());
                System.out.println("기존 내용 : " + foundArticle.getBody());
                System.out.print("새 제목 : ");
                String newTitle = sc.nextLine();
                System.out.print("새 내용 : ");
                String newBody = sc.nextLine();

                    foundArticle.setTitle(newTitle);
                    foundArticle.setBody(newBody);
                    foundArticle.setUpdateDate(Util.getNow());


                    System.out.println(id + "번 게시글이 수정되었습니다");
                } else {
                    System.out.println("사용할 수 없는 명령어입니다");
                }
    }
        System.out.println("==프로그램 종료==");
        sc.close();
}
private static Article getArticleById(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
}

    private static void makeTestData() {
        System.out.println("테스트 데이터 생성");
        articles.add(new Article(1,Util.getNow(),Util.getNow(),"1","abc"));
        articles.add(new Article(2,Util.getNow(),Util.getNow(),"2","bcd"));
        articles.add(new Article(3,Util.getNow(),Util.getNow(),"3","cde"));
    }
}
class Member{
    private int id;
    private String regDate;
    private String loginId;
    private String loginPw;
    private String name;

    public Member(int id, String regDate, String loginId, String loginPw, String name) {
        this.id = id;
        this.regDate = regDate;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
    }
    public String getRegDate() {
        return regDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPw() {
        return loginPw;
    }

    public void setLoginPw(String loginPw) {
        this.loginPw = loginPw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
class Login{
    private String loginId;
}
class NewAccount{
    private String loginId;
    private String loginPw;
    private String name;
    private int lastArticleId = 0;
    private int id = lastArticleId + 1;
    private String regDate = Util.getNow();
    public void NewAc() {
        Scanner sc = new Scanner(System.in);
        Member member = new Member(id,regDate,sc.next(),sc.next(),sc.next());
        System.out.print("ID를 입력해주세요 : ");
        loginId = sc.nextLine();
        System.out.println("PW를 입력해주세요 : ");
        loginPw = sc.nextLine();
        System.out.println("이름을 입력해주세요 : ");
        name = sc.nextLine();
        if(loginId.equals(member.getLoginId())){
            System.out.println("중복된 ID가 존재합니다.");
        }
        else{
            int id = lastArticleId + 1;
            member.setId(id);
            member.setRegDate(Util.getNow());
            member.setLoginId(loginId);
            member.setLoginPw(loginPw);
            member.setName(name);
            lastArticleId++;
        }
    }
}

class Article {
    private int id;
    private String regDate;
    private String updateDate;
    private String title;
    private String body;

    public Article(int id, String regDate, String updateDate, String title, String body) {
        this.id = id;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.title = title;
        this.body = body;
    }

    public String getRegDate() {
        return regDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}