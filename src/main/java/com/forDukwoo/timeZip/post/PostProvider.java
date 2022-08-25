package com.forDukwoo.timeZip.post;

import com.forDukwoo.timeZip.TimeZipApplication;
import com.forDukwoo.timeZip.config.BaseException;
import com.forDukwoo.timeZip.post.model.GetCommentRes;
import com.forDukwoo.timeZip.post.model.GetPostDetailRes;
import com.forDukwoo.timeZip.post.model.GetPostRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.forDukwoo.timeZip.config.BaseResponseStatus.DATABASE_ERROR;
import static com.forDukwoo.timeZip.config.BaseResponseStatus.POSTS_EMPTY_POST_ID;

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

    public GetPostDetailRes retrievePostDetails(int community_id) throws BaseException{
        try {
            GetPostDetailRes getPostDetailRes = postDao.selectPostDetails(community_id);
            return getPostDetailRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkIdExist(int id) throws BaseException{
        try {
            return postDao.checkCommunityIdExist(id);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetCommentRes> retrieveComment(int communityId) throws BaseException {
        try {
            List<GetCommentRes> getCommentRes = postDao.selectComment(communityId);
            return getCommentRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
