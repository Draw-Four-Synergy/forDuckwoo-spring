package com.forDukwoo.timeZip.post;

import com.forDukwoo.timeZip.config.BaseException;
import com.forDukwoo.timeZip.config.BaseResponse;
import com.forDukwoo.timeZip.config.BaseResponseStatus;
import com.forDukwoo.timeZip.post.model.PostPostReq;
import com.forDukwoo.timeZip.post.model.PostPostRes;
import com.forDukwoo.timeZip.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostPostRes> createPosts (@RequestBody PostPostReq postPostReq) {
        try {
            int userIdByJwt = (int) jwtService.getUserId();
            postPostReq.setUserId(userIdByJwt);

            if (postPostReq.getTitle().length() > 30)
                return new BaseResponse<>(BaseResponseStatus.POST_INPUT_FAILED_TITLE);

            if (postPostReq.getContent().length() > 2000)
                return new BaseResponse<>(BaseResponseStatus.POST_INPUT_FAILED_CONTENTS);

            PostPostRes postPostRes = postService.createPosts(postPostReq);
            return new BaseResponse<>(postPostRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
