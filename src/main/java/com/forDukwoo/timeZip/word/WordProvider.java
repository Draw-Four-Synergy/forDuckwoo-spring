package com.forDukwoo.timeZip.word;

import com.forDukwoo.timeZip.config.BaseException;
import com.forDukwoo.timeZip.user.UserDao;
import com.forDukwoo.timeZip.user.model.GetUserInfoRes;
import com.forDukwoo.timeZip.word.model.GetWordRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.forDukwoo.timeZip.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class WordProvider {

    private final WordDao wordDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public WordProvider(WordDao wordDao) {
        this.wordDao = wordDao;
    }


    public List<GetWordRes> retrieveWord(int userIdByJwt) throws BaseException{
        try{
            List<GetWordRes> getWordRes = wordDao.selectWord(userIdByJwt);
            return getWordRes;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public int checkDictionaryIdExist(int dictionaryId) throws BaseException {
        try {
            return wordDao.checkDictionaryIdExist(dictionaryId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public int checkDuplicateWord(int dictionaryId, int userId) throws BaseException {
        try {
            return wordDao.checkDuplicateWord(dictionaryId, userId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
