package com.entity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zmh
 * @since 2022-04-25
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 邮箱
     */
    private String email;

    private String password;

    private Character roleType;

    private String lname;

    private String fname;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Character getRoleType() {
        return roleType;
    }

    public void setRoleType(Character roleType) {
        this.roleType = roleType;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    @Override
    public String toString() {
        return "User{" +
        "id=" + id +
        ", email=" + email +
        ", password=" + password +
        ", roleType=" + roleType +
        ", lname=" + lname +
        ", fname=" + fname +
        "}";
    }
}
