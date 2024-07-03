package org.koreait.Controller;

import org.koreait.dto.Member;
import org.koreait.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberController extends Controller {

    private Scanner sc;
    private List<Member> members;
    private String cmd;
    private Member loginedMember = null;

    private int lastMemberId = 3;

    public MemberController(Scanner sc) {
        this.sc = sc;
        members = new ArrayList<>();
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;

        switch (actionMethodName) {
            case "join":
                doJoin();
                break;
            case "login":
                doLogIn();
                break;
            case "logout":
                doLogOut();
                break;
            default:
                System.out.println("명령어 확인 (actionMethodName) 오류");
                break;
        }
    }

    private void doJoin() {
        System.out.println("==회원가입==");
        int id = lastMemberId + 1;
        String regDate = Util.getNow();
        String loginId = null;
        while (true) {
            System.out.print("로그인 아이디 : ");
            loginId = sc.nextLine().trim();
            if (isJoinableLoginId(loginId) == false) {
                System.out.println("이미 사용중이야");
                continue;
            }
            break;
        }
        String loginPw = null;
        while (true) {
            System.out.print("비밀번호 : ");
            loginPw = sc.nextLine();
            System.out.print("비밀번호 확인 : ");
            String loginPwConfirm = sc.nextLine();

            if (loginPw.equals(loginPwConfirm) == false) {
                System.out.println("비번 다시 확인해");
                continue;
            }
            break;
        }

        System.out.print("이름 : ");
        String name = sc.nextLine();

        Member member = new Member(id, regDate, loginId, loginPw, name);
        members.add(member);

        System.out.println(id + "번 회원이 가입되었습니다");
        lastMemberId++;
    }
    public boolean isLogined(){
        return loginedMember != null;
    }

    private void doLogOut() {
        if (!isLogined()) {
            System.out.println("이미 로그아웃 상태");
            return;
        }
        loginedMember = null;

        System.out.println("로그아웃 되었습니다");
    }

    private void doLogIn() {
        if (isLogined()) {
            System.out.println("이미 로그인중");
            return;
        }
        System.out.println("==로그인==");

        System.out.print("로그인 아이디 : ");
        String loginId = sc.nextLine().trim();
        System.out.print("비밀번호 : ");
        String loginPw = sc.nextLine();


        Member member = getMemberByLoginId(loginId);

        if (member == null) {
            System.out.println("일치하는 회원이 없어");
            return;
        }


        if (member.getLoginPw().equals(loginPw) == false) {
            System.out.println("비번이 틀렸어");
            return;
        }

        loginedMember = member;

        System.out.printf("%s님 로그인 성공\n", member.getName());
    }

    private boolean isJoinableLoginId(String loginId) {
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)) {
                return false;
            }
        }
        return true;
    }
    private boolean isJoinableLoginPw(String loginPw) {
        for (Member member : members) {
            if (member.getLoginPw().equals(loginPw)) {
                return false;
            }
        }
        return true;
    }
    private Member getMemberByLoginId(String loginId) {
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)) {
                return member;
            }
        }
        return null;
    }

    public void makeTestData() {
        System.out.println("회원 테스트 데이터 생성");
        members.add(new Member(1, Util.getNow(), "test1", "test1", "test1"));
        members.add(new Member(2, Util.getNow(), "test2", "test2", "test2"));
        members.add(new Member(3, Util.getNow(), "test3", "test3", "test3"));
    }
}
