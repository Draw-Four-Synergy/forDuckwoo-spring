package com.forDukwoo.timeZip.post;

import com.forDukwoo.timeZip.config.BaseException;
import com.forDukwoo.timeZip.post.model.PostPostReq;
import com.forDukwoo.timeZip.post.model.PostPostRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.forDukwoo.timeZip.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class PostService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostDao postDao;
    private final PostProvider postProvider;

    public PostService(PostDao postDao, PostProvider postProvider) {
        this.postDao = postDao;
        this.postProvider = postProvider;
    }

    public PostPostRes createPosts(PostPostReq postPostReq) throws BaseException {
        try {
            int id = postDao.insertPosts(postPostReq);
            return new PostPostRes(id);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
