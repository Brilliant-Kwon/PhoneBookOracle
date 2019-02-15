package com.example.phonebook;


import com.example.phonebook.dao.PhoneBookDAO;
import com.example.phonebook.dao.PhoneBookDAOImpl;
import com.example.phonebook.vo.PhoneBookVO;

import java.util.List;
import java.util.Scanner;

public class PhoneBook {

    public static void main(String[] args)  {
        System.out.println("=========================================");
        System.out.println("=\t\t\t전화번호 관리 프로그램\t\t\t=");
        System.out.println("=========================================");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1.리스트 2.등록 3.삭제 4.검색 5.종료");
            System.out.println("=========================================");
            System.out.print(">메뉴번호:");
            int num = sc.nextInt();
            sc.nextLine();//오류방지
            switch (num) {
                case 1:
                    System.out.println("<1.리스트>");
                    printPhoneBook();
                    break;
                case 2:
                    System.out.println("<2.등록>");
                    System.out.print(">이름: ");
                    String name = sc.nextLine();
                    System.out.print(">휴대전화: ");
                    String hp = sc.nextLine();
                    System.out.print(">집전화: ");
                    String tel = sc.nextLine();
                    insertPhoneBook(reindexPhoneBook(), name, hp, tel);
                    break;
                case 3:
                    System.out.println("<3.삭제>");
                    System.out.print(">삭제할 번호: ");
                    int del = sc.nextInt();
                    sc.nextLine();//오류방지
                    deletePhoneBOok(Integer.toUnsignedLong(del));
                    break;
                case 4:
                    System.out.println("<4.검색>");
                    System.out.print(">검색할 내용: ");
                    String src = sc.nextLine();
                    searchPhoneBook(src);
                    break;
                case 5:
                    System.out.println("=========================================");
                    System.out.println("프로그램을 종료합니다. 감사합니다.");
                    System.out.println("=========================================");
                    break;
                default:
                    System.out.println("[다시 입력해 주세요.]");
                    break;

            }
            if (num == 5) {
                break;
            }
        }
//        insertPhoneBook(reindexPhoneBook(),"손건우", "010-9208-3864", "051-528-3869");
//        printPhoneBook();
//        getPhoneBook(1L);
//        deletePhoneBOok(1L);
    }

    private static void searchPhoneBook(String str) {
        PhoneBookDAO dao = new PhoneBookDAOImpl();
        List<PhoneBookVO> list = dao.getList();
        int count = 0;

        for (PhoneBookVO phoneBookVO : list) {
            if (phoneBookVO.getName().contains(str) || phoneBookVO.getHp().contains(str) || phoneBookVO.getTel().contains(str)) {
                System.out.println(phoneBookVO.getPhoneId() + ". " + phoneBookVO.getName() + " " + phoneBookVO.getHp() + " " + phoneBookVO.getTel());
                count++;
            }
        }
        System.out.println("총 " + count + " 개를 찾았습니다.");

    }

    private static Long reindexPhoneBook() {
        PhoneBookDAO dao = new PhoneBookDAOImpl();
        List<PhoneBookVO> list = dao.getList();

        Long temp = 1L;
        Long result = 0L;
        for (PhoneBookVO phoneBookVO : list) {
            if (phoneBookVO.getPhoneId().equals(temp)) {
                temp++;
            } else {
                result = temp;
                break;
            }
        }

        if (result != 0L) {
//            System.out.println("result : " + result);
            return result;
//            ((PhoneBookDAOImpl) dao).reindex(result);
        } else {
//            System.out.println("temp : " + temp);
            return temp;
//            ((PhoneBookDAOImpl) dao).reindex(temp);
        }
    }

    private static void getPhoneBook(Long id) {
        PhoneBookDAO dao = new PhoneBookDAOImpl();
        PhoneBookVO phoneBookVO = dao.get(id);
        if (phoneBookVO != null) {
            System.out.println(phoneBookVO.getPhoneId() + ". " + phoneBookVO.getName() + " " + phoneBookVO.getHp() + " " + phoneBookVO.getTel());
        } else {
            System.out.println("[번호 " + id + "를 찾지 못했습니다.]");
        }
    }

    private static void insertPhoneBook(Long id, String name, String hp, String tel) {
        PhoneBookDAO dao = new PhoneBookDAOImpl();
        PhoneBookVO phoneBookVO = new PhoneBookVO(id, name, hp, tel);
        boolean success = dao.insert(phoneBookVO);

        if (success) {
            System.out.printf("[번호 %d를 추가했습니다.]%n", id);
        } else {
            System.out.printf("[번호 %d를 추가하지 못 했습니다.] %n", id);
        }

//        printPhoneBook();
    }

    private static void deletePhoneBOok(Long id) {
        PhoneBookDAO dao = new PhoneBookDAOImpl();
        boolean success = dao.delete(id);

        if (success) {
            System.out.printf("[번호 %d를 삭제했습니다.] \n", id);
        } else {
            System.out.printf("[번호 %d를 삭제하지 못 했습니다.] \n", id);
        }

        printPhoneBook();
    }

    private static void printPhoneBook() {
        PhoneBookDAO dao = new PhoneBookDAOImpl();
        List<PhoneBookVO> list = dao.getList();

        for (PhoneBookVO phoneBookVO : list) {
            System.out.println(phoneBookVO.getPhoneId() + ". " + phoneBookVO.getName() + " " + phoneBookVO.getHp() + " " + phoneBookVO.getTel());
        }
        System.out.println();
    }

}
