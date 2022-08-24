package com.forDukwoo.timeZip.content;

import com.forDukwoo.timeZip.config.BaseException;
import com.forDukwoo.timeZip.config.BaseResponse;
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


}
