package com.forDukwoo.timeZip.content;

import com.forDukwoo.timeZip.config.BaseException;
import com.forDukwoo.timeZip.content.model.GetContentDetailRes;
import com.forDukwoo.timeZip.content.model.GetContentRes;
import com.forDukwoo.timeZip.user.UserDao;
import com.forDukwoo.timeZip.user.model.GetScrapRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.forDukwoo.timeZip.config.BaseResponseStatus.*;

@Service
public class ContentProvider {
    private final ContentDao contentDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ContentProvider(ContentDao contentDao) {
        this.contentDao = contentDao;
    }

    public List<GetContentRes> retrieveContent(String category) throws BaseException {
        try {
            if(category.equals("news")) {
                List<GetContentRes> getContentRes  = contentDao.getInterestNews();
                return getContentRes;
            }
            else if(category.equals("en_news")) {
                List<GetContentRes> getContentRes  = contentDao.getInterestEnNews();
                return getContentRes;
            }
            else if (category.equals("audio")) {
                List<GetContentRes> getContentRes  = contentDao.getInterestAudio();
                return getContentRes;
            }
            throw new BaseException(POSTS_EMPTY_CATEGORY_ID);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetContentRes> retrieveContentRecent(String category) throws BaseException {
        try {
            if(category.equals("news")) {
                List<GetContentRes> getContentRes  = contentDao.getNews();
                return getContentRes;
            }
            else if(category.equals("en_news")) {
                List<GetContentRes> getContentRes  = contentDao.getEnNews();
                return getContentRes;
            }
            else if (category.equals("audio")) {
                List<GetContentRes> getContentRes  = contentDao.getAudio();
                return getContentRes;
            }
            throw new BaseException(POSTS_EMPTY_CATEGORY_ID);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetContentDetailRes retrieveContentDetail(String category, int id) throws BaseException{
        try {
            if(category.equals("news")) {
               GetContentDetailRes getContentDetailRes  = contentDao.getNewsDetail(id);
                return getContentDetailRes;
            }
            else if(category.equals("en_news")) {
                GetContentDetailRes getContentDetailRes  = contentDao.getEnNewsDetail(id);
                return getContentDetailRes;
            }
            else if (category.equals("audio")) {
                GetContentDetailRes getContentDetailRes  = contentDao.getAudioDetail(id);
                return getContentDetailRes;
            }
            throw new BaseException(POSTS_EMPTY_CATEGORY_ID);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkNewsId(int id) throws BaseException {
        try {
            return contentDao.checkNewsIdExist(id);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkEnNewsId(int id) throws BaseException {
        try {
            return contentDao.checkEnNewsIdExist(id);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkAudioId(int id) throws BaseException {
        try {
            return contentDao.checkAudioIdExist(id);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
