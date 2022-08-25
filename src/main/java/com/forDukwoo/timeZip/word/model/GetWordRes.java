package com.forDukwoo.timeZip.word.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetWordRes {
    int dictionaryId;
    String word;
    String meaning1;
    String meaning2;
}
