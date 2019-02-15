package com.example.phonebook.dao;

import com.example.phonebook.vo.PhoneBookVO;

import java.util.List;

public interface PhoneBookDAO {
    public List<PhoneBookVO> getList();
    public PhoneBookVO get(Long id);

    public boolean insert(PhoneBookVO phoneBookVO);

    public boolean delete(Long id);

    public boolean update(PhoneBookVO phoneBookVO);
}
