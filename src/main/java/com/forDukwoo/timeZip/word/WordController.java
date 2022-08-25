package com.forDukwoo.timeZip.word;

import com.forDukwoo.timeZip.config.BaseException;
import com.forDukwoo.timeZip.config.BaseResponse;
import com.forDukwoo.timeZip.user.model.GetUserInfoRes;
import com.forDukwoo.timeZip.utils.JwtService;
import com.forDukwoo.timeZip.word.model.GetWordRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/words")
public class WordController {
    @Autowired
    private final WordProvider wordProvider;
    @Autowired
    private final WordDao wordDao;
    @Autowired
    private final JwtService jwtService;

    public WordController(WordProvider wordProvider, WordDao wordDao, JwtService jwtService) {
        this.jwtService = jwtService;
        this.wordDao = wordDao;
        this.wordProvider = wordProvider;
    }

    // 단어장 조회
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetWordRes>> getWord() {
        try{
            int userIdByJwt = (int) jwtService.getUserId();
            List<GetWordRes> getWordRes = wordProvider.retrieveWord(userIdByJwt);
            return new BaseResponse<>(getWordRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
