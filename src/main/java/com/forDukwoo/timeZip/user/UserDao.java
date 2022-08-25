package com.forDukwoo.timeZip.user;

import com.forDukwoo.timeZip.user.model.GetScrapRes;
import com.forDukwoo.timeZip.user.model.GetUserInfoRes;
import com.forDukwoo.timeZip.user.model.GetWordRes;
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


    public int checkScrapIdExist(int scrapId) {
        String checkScrapIdExistQuery = "select exists(select scrap_id from scrap where scrap_id = ?)";
        long checkScrapIdExistParams = scrapId;
        return this.jdbcTemplate.queryForObject(checkScrapIdExistQuery,
                int.class,
                checkScrapIdExistParams);
    }

    public int checkDuplicateScrap(int scrapId, int userId) {
        String checkDuplicateScrapQuery = "select exists(select scrap_id, user_id from scrap where scrap_id = ? and user_id = ?)";
        int checkDuplicateScrapParams1 = scrapId;
        int checkDuplicateScrapParams2 = userId;
        return this.jdbcTemplate.queryForObject(checkDuplicateScrapQuery, int.class, checkDuplicateScrapParams1, checkDuplicateScrapParams2);

    }

    public int deleteScrap(int userId, int scrapId) {
        String deleteScrapQuery = "delete from scrap where user_id = ? and scrap_id = ?";
        Object[] deleteScrapParams = new Object[]{userId, scrapId};
        return this.jdbcTemplate.update(deleteScrapQuery,
                deleteScrapParams);
    }

    public List<GetWordRes> getWord(long userId, int enNewsId) {
        String getWordQuery = "select word, meaning1 \n" +
                "from dictionary \n" +
                "where user_id=? and en_news_id = ?";
        Object[] getWordParam = new Object[]{userId, enNewsId};
        return this.jdbcTemplate.query(getWordQuery,
                (rs, rowNum) -> new GetWordRes(
                        rs.getString("word"),
                        rs.getString("meaning1")
                ), getWordParam);
    }

    public int checkEnNewsIdExist(int enNewsId) {
        String checkEnNewsIdExistQuery = "select exists(select en_news_id from en_news where en_news_id = ?)";
        int checkEnNewsIdExistParams = enNewsId;
        return this.jdbcTemplate.queryForObject(checkEnNewsIdExistQuery, int.class, checkEnNewsIdExistParams);
    }
}
