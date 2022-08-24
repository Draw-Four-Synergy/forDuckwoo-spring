package com.forDukwoo.timeZip.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPostRes {
    int community_id;
    String photo;
    String nick;
    String content;
}
