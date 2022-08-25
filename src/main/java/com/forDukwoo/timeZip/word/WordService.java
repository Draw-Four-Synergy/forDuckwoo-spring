package com.forDukwoo.timeZip.word;

import com.forDukwoo.timeZip.config.BaseException;
import com.forDukwoo.timeZip.user.UserDao;
import com.forDukwoo.timeZip.user.UserProvider;
import com.forDukwoo.timeZip.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.forDukwoo.timeZip.config.BaseResponseStatus.*;

@Service
public class WordService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WordDao wordDao;
    private final WordProvider wordProvider;
    private final JwtService jwtService;

    @Autowired
    public WordService(WordDao wordDao, WordProvider wordProvider, JwtService jwtService) {
        this.wordDao = wordDao;
        this.wordProvider = wordProvider;
        this.jwtService = jwtService;
    }

    public void deleteWord(int userId, int dictionaryId) throws BaseException {
        // dictionaryId 가 존재하지 않는 경우
        if(wordProvider.checkDictionaryIdExist(dictionaryId) == 0) {
            throw new BaseException(POSTS_EMPTY_POST_ID);
        }
        // dictionaryId userId의 쌍이 존재하지 않는 경우
        if(wordProvider.checkDuplicateWord(dictionaryId, userId) == 0) {
            throw new BaseException(EMPTY_WORD);
        }
        try {
            wordDao.deleteWord(userId, dictionaryId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
