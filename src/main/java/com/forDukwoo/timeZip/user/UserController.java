package com.forDukwoo.timeZip.user;

import com.forDukwoo.timeZip.config.BaseException;
import com.forDukwoo.timeZip.config.BaseResponse;
import com.forDukwoo.timeZip.config.BaseResponseStatus;
import com.forDukwoo.timeZip.user.model.*;
import com.forDukwoo.timeZip.utils.JwtService;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }


    // 사용자 정보 확인 - myPage 에서 사용
    @ResponseBody
    @GetMapping("")
    public BaseResponse<GetUserInfoRes> getUser() {
        try{
            long userIdByJwt = jwtService.getUserId();
            GetUserInfoRes getUserInfoRes = userProvider.retrieveUser(userIdByJwt);
            return new BaseResponse<>(getUserInfoRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 사용자 등록
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        try{
            if(postUserReq.getEmail().length() > 30 || postUserReq.getEmail().length() < 7)
                return new BaseResponse<>(BaseResponseStatus.USERS_USERS_FAILED_ID);
            if(postUserReq.getNick().length() > 15 || postUserReq.getNick().length() < 7)
                return new BaseResponse<>(BaseResponseStatus.USERS_USERS_FAILED_NICK);
            if(postUserReq.getPwd().length() > 15 || postUserReq.getPwd().length() < 7)
                return new BaseResponse<>(BaseResponseStatus.USERS_USERS_FAILED_PWD);
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/email/{email}")
    public BaseResponse<String> checkId(@PathVariable ("email") String email) throws BaseException{
        try {
            if (userProvider.checkIdExist(email) == 1) {
                throw new BaseException(BaseResponseStatus.USERS_DUPLICATED_ID);
            }
            String result = "중복되지 않은 아이디입니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/nick/{nick}")
    public BaseResponse<String> checkNick(@PathVariable ("nick") String nick) throws BaseException{
        try {
            if (userProvider.checkNickExist(nick) == 1) {
                throw new BaseException(BaseResponseStatus.USERS_DUPLICATED_NICK);
            }
            String result = "중복되지 않은 닉네임입니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 스크랩 리스트 반환 (카테고리별)
    @ResponseBody
    @GetMapping("scrap/{category}")
    public BaseResponse<List<GetScrapRes>> getScrapNews (@PathVariable ("category") String category) throws BaseException {
        try {
            int userIdByJwt = (int) jwtService.getUserId();
            List<GetScrapRes> getScrapRes = userProvider.retrieveScrap(userIdByJwt, category);
            return new BaseResponse<>(getScrapRes);
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }

    // 스크랩 삭제
    @ResponseBody
    @DeleteMapping("/scrap/{scrapId}")
    public BaseResponse<String> deleteUserScrap (@PathVariable("scrapId") int scrapId) {
        try{
            int userIdByJwt = (int) jwtService.getUserId();
            userService.deleteScrap(userIdByJwt, scrapId);
            String result = "스크랩을 삭제했습니다.";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 단어 리스트 확인
    @ResponseBody
    @GetMapping("/word/{enNewsId}")
    public BaseResponse<List<GetWordRes>> getWord (@PathVariable("enNewsId") int enNewsId) {
        try{
            if (userProvider.checkEnNewsIdExist(enNewsId) == 0) {
                throw new BaseException(BaseResponseStatus.POSTS_EMPTY_POST_ID);
            }
            long userIdByJwt = jwtService.getUserId();
            List<GetWordRes> getWordRes = userProvider.retrieveWord(userIdByJwt, enNewsId);
            return new BaseResponse<>(getWordRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
