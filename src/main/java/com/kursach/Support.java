package com.kursach;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Support {
    private String url;
    private String text;

    public Support(){super();}

    public Support(String url, String text) {
        this.url = url;
        this.text = text;
    }

}
