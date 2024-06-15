package com.kursach;

public class People {
   private String name;
   private String job;

   public People(){super();}

    public People(String name, String job){
       this.name=name;
       this.job=job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public boolean equals(Object obj) {

        return super.equals(obj);
    }
}