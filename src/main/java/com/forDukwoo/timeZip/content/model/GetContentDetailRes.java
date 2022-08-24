package com.forDukwoo.timeZip.content.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetContentDetailRes {
    String title;
    String content;
    int smile;
    int cry;
    int angry;
}
