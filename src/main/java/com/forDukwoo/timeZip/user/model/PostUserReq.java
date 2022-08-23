package com.forDukwoo.timeZip.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    String email;
    String nick;
    String pwd;

    public PostUserReq(){

    }
}
