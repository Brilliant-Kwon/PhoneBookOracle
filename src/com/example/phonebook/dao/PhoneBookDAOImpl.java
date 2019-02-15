package com.example.phonebook.dao;

import com.example.phonebook.vo.PhoneBookVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhoneBookDAOImpl implements PhoneBookDAO {
    private Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
            conn = DriverManager.getConnection(dburl, "bituser", "bituser");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC 드라이버를 찾지 못하였습니다..");
        }
        return conn;
    }

    @Override
    public List<PhoneBookVO> getList() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        List<PhoneBookVO> list = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT id, name, hp, tel FROM phone_book ORDER BY id asc ";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                PhoneBookVO phoneBookVO = new PhoneBookVO(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
                list.add(phoneBookVO);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error!");
            System.err.println("ERROR : " + e.getMessage());
        }

        return list;
    }

    @Override
    public PhoneBookVO get(Long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PhoneBookVO phoneBookVO = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "SELECT id, name, hp, tel FROM phone_book WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                phoneBookVO = new PhoneBookVO(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException e) {
            System.err.println("SQL Error!");
            System.err.println("ERROR : " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.err.println("SQL Error!");
                System.err.println("ERROR : " + e.getMessage());
            }
        }
        return phoneBookVO;
    }

    public void reindex(Long index) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;

        try {
            conn = getConnection();
            String drop = "drop SEQUENCE SEQ_PHONE_BOOK";
            System.out.println(drop);
            pstmt = conn.prepareStatement(drop);

            System.out.println("drop 실행");
            pstmt.executeQuery();

            String create = "create SEQUENCE SEQ_PHONE_BOOK minvalue 1 maxvalue 999999999999999999999 start with ? increment by 1";
            System.out.println(create);
            pstmt2 = conn.prepareStatement(create);
            pstmt2.setLong(1, index);

            System.out.println("create실행" + index);
            pstmt2.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Error!");
            System.err.println("ERROR : " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("SQL Error!");
                System.err.println("ERROR : " + e.getMessage());
            }
        }
    }

    @Override
    public boolean insert(PhoneBookVO phoneBookVO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int insertedCount = 0;//삽입된 row 수

        try {
            conn = getConnection();
            String sql = "INSERT INTO phone_book VALUES(?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, phoneBookVO.getPhoneId());
            pstmt.setString(2, phoneBookVO.getName());
            pstmt.setString(3, phoneBookVO.getHp());
            pstmt.setString(4, phoneBookVO.getTel());

            insertedCount = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Error!");
            System.err.println("ERROR : " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("SQL Error!");
                System.err.println("ERROR : " + e.getMessage());
            }
        }
        return insertedCount == 1; // 인서트가 정상적으로 되면 1/아니면 0
    }

    @Override
    public boolean delete(Long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int deletedCount = 0;

        try {
            conn = getConnection();
            String sql = "DELETE FROM phone_book WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            deletedCount = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Error!");
            System.err.println("ERROR : " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("SQL Error!");
                System.err.println("ERROR : " + e.getMessage());
            }
        }

        return deletedCount == 1;
    }

    @Override
    public boolean update(PhoneBookVO phoneBookVO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int updatedCount = 0; //반영된 결과 수

        try {
            conn = getConnection();
            String sql = "UPDATE PHONE_BOOK SET name = ?, hp = ?, tel = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phoneBookVO.getName());
            pstmt.setString(2, phoneBookVO.getHp());
            pstmt.setString(3, phoneBookVO.getTel());
            pstmt.setLong(4, phoneBookVO.getPhoneId());

            updatedCount = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Error!");
            System.err.println("ERROR : " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("SQL Error!");
                System.err.println("ERROR : " + e.getMessage());
            }
        }

        return updatedCount == 1;
    }
}
