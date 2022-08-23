package com.forDukwoo.timeZip.user;

import com.forDukwoo.timeZip.user.model.GetUserInfoRes;
import com.forDukwoo.timeZip.user.model.PostUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // id 중복 확인
    public int checkUserIdExist(String email) {
        String checkIdExistQuery = "select exists(select email from user where email = ?)";
        String checkIdExistParams = email;
        return this.jdbcTemplate.queryForObject(checkIdExistQuery,
                int.class,
                checkIdExistParams);
    }

    // 닉네임 중복 확인
    public int checkUserNickExist(String nick) {
        String checkNickExistQuery = "select exists(select nick from user where nick = ?)";
        String checkNickExistParams = nick;
        return this.jdbcTemplate.queryForObject(checkNickExistQuery,
                int.class,
                checkNickExistParams);
    }

    public GetUserInfoRes selectUser(long userId) {
        String selectUserQuery = "\n" +
                "select nick from user where user_id = ?";
        long selectUserParam = userId;
        return this.jdbcTemplate.queryForObject(selectUserQuery,
                (rs, rowNum) -> new GetUserInfoRes(
                        rs.getString("nick")
                ), selectUserParam);
    }

    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into user(email, nick, pwd) VALUES (?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getEmail(), postUserReq.getNick(), postUserReq.getPwd()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);
        String lastInsertIdQuery = "select last_insert_id()";
        int userIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
        return userIdx;
    }

}
