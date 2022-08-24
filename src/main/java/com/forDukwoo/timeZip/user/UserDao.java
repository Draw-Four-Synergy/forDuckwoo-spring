package com.forDukwoo.timeZip.user;

import com.forDukwoo.timeZip.user.model.GetScrapRes;
import com.forDukwoo.timeZip.user.model.GetUserInfoRes;
import com.forDukwoo.timeZip.user.model.PostUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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
                "select photo, nick from user where user_id = ?";
        long selectUserParam = userId;
        return this.jdbcTemplate.queryForObject(selectUserQuery,
                (rs, rowNum) -> new GetUserInfoRes(
                        rs.getString("photo"),
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

    // 스크랩 리스트 출력
    public List<GetScrapRes> getScrapNews (int userId) {
        String getScrapNewsQuery = "select scrap_id as scrapId, title, left(content, 30) as content\n" +
                "from scrap, news\n" +
                "where scrap.news_id = news.news_id and user_id = ?";
        int getScrapNewsParam = userId;
        return this.jdbcTemplate.query(getScrapNewsQuery,
                (rs, rowNum) -> new GetScrapRes(
                        rs.getInt("scrapId"),
                        rs.getString("title"),
                        rs.getString("content")
                ),getScrapNewsParam);
    }

    public List<GetScrapRes> getScrapEnNews (int userId) {
        String getScrapNewsQuery = "select scrap_id as scrapId, title, left(content, 30) as content\n" +
                "                from scrap, en_news\n" +
                "                where scrap.en_news_id = en_news.en_news_id and user_id = ?;";
        int getScrapEnNewsParam = userId;
        return this.jdbcTemplate.query(getScrapNewsQuery,
                (rs, rowNum) -> new GetScrapRes(
                        rs.getInt("scrapId"),
                        rs.getString("title"),
                        rs.getString("content")
                ),getScrapEnNewsParam);
    }

    public List<GetScrapRes> getScrapAudio (int userId) {
        String getScrapNewsQuery = "select scrap_id as scrapId, title, left(content, 30) as content\n" +
                "from scrap, audio\n" +
                "where scrap.audio_id = audio.audio_id and user_id = ?";
        int getScrapAudioParam = userId;
        return this.jdbcTemplate.query(getScrapNewsQuery,
                (rs, rowNum) -> new GetScrapRes(
                        rs.getInt("scrapId"),
                        rs.getString("title"),
                        rs.getString("content")
                ), getScrapAudioParam);
    }


}
