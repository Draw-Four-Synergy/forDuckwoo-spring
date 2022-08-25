package com.forDukwoo.timeZip.post;

import com.forDukwoo.timeZip.config.BaseException;
import com.forDukwoo.timeZip.config.BaseResponse;
import com.forDukwoo.timeZip.config.BaseResponseStatus;
import com.forDukwoo.timeZip.post.model.*;
import com.forDukwoo.timeZip.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.forDukwoo.timeZip.config.BaseResponseStatus.POSTS_EMPTY_POST_ID;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private final PostProvider postProvider;
    @Autowired
    private final PostService postService;
    @Autowired
    private final JwtService jwtService;

    public PostController(PostProvider postProvider, PostService postService, JwtService jwtService) {
        this.postProvider = postProvider;
        this.postService = postService;
        this.jwtService = jwtService;
    }

    // 게시물 등록
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostPostRes> createPosts (@RequestBody PostPostReq postPostReq) {
        try {
            int userIdByJwt = (int) jwtService.getUserId();
            postPostReq.setUserId(userIdByJwt);

            if (postPostReq.getContent().length() > 2000)
                return new BaseResponse<>(BaseResponseStatus.POST_INPUT_FAILED_CONTENTS);

            PostPostRes postPostRes = postService.createPosts(postPostReq);
            return new BaseResponse<>(postPostRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 게시물 조회
    @ResponseBody
    @GetMapping("/{hashtag}")
    public BaseResponse<List<GetPostRes>> postPosts(@PathVariable("hashtag") int hashtag) {
        try {
            List<GetPostRes> getPostRes = postProvider.retrievePosts(hashtag);
            return new BaseResponse<>(getPostRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 게시물 상세페이지
    @ResponseBody
    @GetMapping("detail/{communityId}")
    public BaseResponse<GetPostDetailRes> postPostsDetail(@PathVariable("communityId") int communityId) {
        try {
            if(postProvider.checkIdExist(communityId) == 0) {
                throw new BaseException(POSTS_EMPTY_POST_ID);
            }
            GetPostDetailRes getPostDetailRes = postProvider.retrievePostDetails(communityId);
            return new BaseResponse<>(getPostDetailRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 댓글 조회
    @ResponseBody
    @GetMapping("/comment/{communityId}")
    public BaseResponse<List<GetCommentRes>> getComment (@PathVariable("communityId") int communityId) {
        try {
            if (postProvider.checkIdExist(communityId) == 0) {
                throw new BaseException(POSTS_EMPTY_POST_ID);
            }
            List<GetCommentRes> getComment = postProvider.retrieveComment(communityId);
            return new BaseResponse<>(getComment);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 댓글 등록
    @ResponseBody
    @PostMapping("/comment/{communityId}")
    public BaseResponse<String> createComment (@PathVariable("communityId") int communityId, @RequestBody PostCommentReq postCommentReq) {
        try {
            if(postProvider.checkIdExist(communityId) == 0) {
                throw new BaseException(POSTS_EMPTY_POST_ID);
            }
            int userIdByJwt = (int) jwtService.getUserId();
            postCommentReq.setUserId(userIdByJwt);

            postCommentReq.setCommunityId(communityId);
            if (postCommentReq.getComment().length() > 200)
                return new BaseResponse<>(BaseResponseStatus.POST_INPUT_FAILED_CONTENTS);
            //String 으로 받자
            postService.createComment(postCommentReq);
            String result = "등록을 성공했습니다.";
            return new BaseResponse<>(result);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
