package com.forDukwoo.timeZip.user;

import com.forDukwoo.timeZip.config.BaseException;
import com.forDukwoo.timeZip.config.BaseResponse;
import com.forDukwoo.timeZip.config.BaseResponseStatus;
import com.forDukwoo.timeZip.user.model.GetUserInfoRes;
import com.forDukwoo.timeZip.user.model.PostUserReq;
import com.forDukwoo.timeZip.user.model.PostUserRes;
import com.forDukwoo.timeZip.utils.JwtService;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    // 사용자 정보 확인
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


}
