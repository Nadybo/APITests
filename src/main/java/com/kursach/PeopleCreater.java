package com.kursach;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PeopleCreater extends People {
    private Integer id;
    private String createdAt;

    public  PeopleCreater(){super();}

    public PeopleCreater(Integer id, String createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public String getName(){return super.getName();}

    public String getJob(){return super.getJob();}
}
