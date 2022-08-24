package com.forDukwoo.timeZip.post;

import com.forDukwoo.timeZip.config.BaseException;
import com.forDukwoo.timeZip.post.model.GetPostRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.forDukwoo.timeZip.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class PostProvider {
    private final PostDao postDao;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PostProvider(PostDao postDao) {
        this.postDao = postDao;
    }

    public List<GetPostRes> retrievePosts(int hashtag) throws BaseException {
        try {
            // hashtag == 0 전체 게시물
            if(hashtag == 0) {
                List<GetPostRes> getPostRes = postDao.selectPosts();
                return getPostRes;
            }
            List<GetPostRes> getPostRes = postDao.selectPostsHashtag(hashtag);
            return getPostRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
