package com.kursach;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class UsersPage {
    private Integer page;
    Integer per_page;
    Integer total;
    Integer total_pages;
    List<UserData> data;
    Support support;

}
