package com.kursach;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private Integer id;
    private String email;
    @JsonProperty("first_name")
    String first_name;
    @JsonProperty("last_name")
    String last_name;
    String avatar;

}
