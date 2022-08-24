package com.forDukwoo.timeZip.content.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetContentRes {
    int id;
    String title;
    int view;
    int scrap;
}
