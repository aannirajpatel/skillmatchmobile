package com.aanpatel.skillmatch3;

/**
 * Created by Aan Patel on 12/11/18.
 */

public class User {
    private String name;
    private String reg;
    private String bio;

    public User(String name, String reg, String bio) {
        this.name = name;
        this.reg = reg;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
