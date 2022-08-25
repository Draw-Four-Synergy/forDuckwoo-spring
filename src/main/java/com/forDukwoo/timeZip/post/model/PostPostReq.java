package com.forDukwoo.timeZip.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostPostReq {
    int userId;
    String content;
    int hashtag;

    public PostPostReq () {}
}
