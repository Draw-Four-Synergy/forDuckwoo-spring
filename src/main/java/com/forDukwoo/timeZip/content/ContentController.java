package com.forDukwoo.timeZip.content;

import com.forDukwoo.timeZip.config.BaseException;
import com.forDukwoo.timeZip.config.BaseResponse;
import com.forDukwoo.timeZip.config.BaseResponseStatus;
import com.forDukwoo.timeZip.content.model.GetContentDetailRes;
import com.forDukwoo.timeZip.content.model.GetContentRes;
import com.forDukwoo.timeZip.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contents")
public class ContentController {
    @Autowired
    private final ContentProvider contentProvider;
    @Autowired
    private final ContentService contentService;
    @Autowired
    private final JwtService jwtService;

    public ContentController(ContentProvider contentProvider, ContentService contentService, JwtService jwtService) {
        this.contentProvider = contentProvider;
        this.contentService = contentService;
        this.jwtService = jwtService;
    }

    // 인기순 나열
    @ResponseBody
    @GetMapping("interest/{category}")
    public BaseResponse<List<GetContentRes>> getInterest (@PathVariable ("category") String category) throws BaseException {
        try {
            List<GetContentRes> getContentRes = contentProvider.retrieveContent(category);
            return new BaseResponse<>(getContentRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 최신순 나열
    @ResponseBody
    @GetMapping("/{category}")
    public BaseResponse<List<GetContentRes>> getContent (@PathVariable ("category") String category) throws BaseException {
        try {
            List<GetContentRes> getContentRes = contentProvider.retrieveContentRecent(category);
            return new BaseResponse<>(getContentRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 본문 출력
    @ResponseBody
    @GetMapping("/{category}/{id}")
    public BaseResponse<GetContentDetailRes> getContentDetail (@PathVariable ("category") String category, @PathVariable("id") int id) throws BaseException {
        try {
            if(contentProvider.checkNewsId(id) == 0) {
                throw new BaseException(BaseResponseStatus.POSTS_EMPTY_POST_ID);
            }
            if(contentProvider.checkEnNewsId(id) == 0) {
                throw new BaseException(BaseResponseStatus.POSTS_EMPTY_POST_ID);
            }
            if(contentProvider.checkAudioId(id) == 0) {
                throw new BaseException(BaseResponseStatus.POSTS_EMPTY_POST_ID);
            }
            GetContentDetailRes getContentDetailRes = contentProvider.retrieveContentDetail(category, id);
            return new BaseResponse<>(getContentDetailRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 스크랩 등록
    @ResponseBody
    @PostMapping("/scrap/{category}/{id}")
    public BaseResponse<String> createPosts (@PathVariable("category") String category, @PathVariable("id") int id) {
        try {
            int userIdByJwt = (int) jwtService.getUserId();
            String result = "스크랩 등록에 성공하였습니다.";
            contentProvider.createScrap(userIdByJwt, category, id);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
