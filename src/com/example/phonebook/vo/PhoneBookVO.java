package com.example.phonebook.vo;

public class PhoneBookVO {
    //필드
    private Long phoneId;
    private String name;
    private String hp;
    private String tel;

    //생성자


    public PhoneBookVO(Long phoneId, String name, String hp, String tel) {
        super();
        this.phoneId = phoneId;
        this.name = name;
        this.hp = hp;
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "PhoneBookVO{" +
                "phoneId=" + phoneId +
                ", name='" + name + '\'' +
                ", hp='" + hp + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
