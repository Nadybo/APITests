package com.kursach;

public class PeopleCreater extends People {
    private Integer id;
    private String createdAt;

    public  PeopleCreater(){super();}

    public PeopleCreater(Integer id, String createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName(){return super.getName();}

    public String getJob(){return super.getJob();}
}
