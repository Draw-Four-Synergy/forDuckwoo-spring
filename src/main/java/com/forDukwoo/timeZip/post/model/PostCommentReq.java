package com.forDukwoo.timeZip.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostCommentReq {
    int communityId;
    String comment;
    int userId;

    public PostCommentReq () {}

}
