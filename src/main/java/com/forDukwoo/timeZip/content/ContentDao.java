package com.forDukwoo.timeZip.content;

import com.forDukwoo.timeZip.content.model.GetContentDetailRes;
import com.forDukwoo.timeZip.content.model.GetContentRes;
import com.forDukwoo.timeZip.user.model.GetUserInfoRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ContentDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetContentRes> getInterestNews() {
        String getInterestNewsQuery = "select title, view, scrap from news order by view desc";
        return this.jdbcTemplate.query(getInterestNewsQuery,
                (rs, rowNum) -> new GetContentRes(
                        rs.getString("title"),
                        rs.getInt("view"),
                        rs.getInt("scrap")
                ));
    }

    public List<GetContentRes> getInterestEnNews() {
        String getInterestNewsQuery = "select title, view, scrap from en_news order by view desc";
        return this.jdbcTemplate.query(getInterestNewsQuery,
                (rs, rowNum) -> new GetContentRes(
                        rs.getString("title"),
                        rs.getInt("view"),
                        rs.getInt("scrap")
                ));
    }

    public List<GetContentRes> getInterestAudio() {
        String getInterestNewsQuery = "select title, view, scrap from audio order by view desc";
        return this.jdbcTemplate.query(getInterestNewsQuery,
                (rs, rowNum) -> new GetContentRes(
                        rs.getString("title"),
                        rs.getInt("view"),
                        rs.getInt("scrap")
                ));
    }

    public List<GetContentRes> getNews() {
        String getInterestNewsQuery = "select title, view, scrap from news order by news_id desc";
        return this.jdbcTemplate.query(getInterestNewsQuery,
                (rs, rowNum) -> new GetContentRes(
                        rs.getString("title"),
                        rs.getInt("view"),
                        rs.getInt("scrap")
                ));
    }

    public List<GetContentRes> getEnNews() {
        String getInterestNewsQuery = "select title, view, scrap from en_news order by en_news_id desc";
        return this.jdbcTemplate.query(getInterestNewsQuery,
                (rs, rowNum) -> new GetContentRes(
                        rs.getString("title"),
                        rs.getInt("view"),
                        rs.getInt("scrap")
                ));
    }

    public List<GetContentRes> getAudio() {
        String getInterestNewsQuery = "select title, view, scrap from audio order by audio_id desc";
        return this.jdbcTemplate.query(getInterestNewsQuery,
                (rs, rowNum) -> new GetContentRes(
                        rs.getString("title"),
                        rs.getInt("view"),
                        rs.getInt("scrap")
                ));
    }

    public GetContentDetailRes getNewsDetail(int id) {
        String getNewsDetailQuery = "select title, content, smile, cry, angry\n" +
                "from news, emoticon\n" +
                "where emoticon.emoticon_id = news.emoticon_id\n" +
                "and news_id = ?";
        long getNewsDetailParam = id;
        return this.jdbcTemplate.queryForObject(getNewsDetailQuery,
                (rs, rowNum) -> new GetContentDetailRes(
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("smile"),
                        rs.getInt("cry"),
                        rs.getInt("angry")
                ), getNewsDetailParam);
    }

    public GetContentDetailRes getEnNewsDetail(int id) {
        String getEnNewsDetailQuery = "select title, content, smile, cry, angry\n" +
                "from en_news, emoticon\n" +
                "where emoticon.emoticon_id = en_news.emoticon_id\n" +
                "and en_news_id = ?";
        long getEnNewsDetailParam = id;
        return this.jdbcTemplate.queryForObject(getEnNewsDetailQuery,
                (rs, rowNum) -> new GetContentDetailRes(
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("smile"),
                        rs.getInt("cry"),
                        rs.getInt("angry")
                ), getEnNewsDetailParam);
    }

    public GetContentDetailRes getAudioDetail(int id) {
        String getAudioDetailQuery = "select title, content, smile, cry, angry\n" +
                "from audio, emoticon\n" +
                "where emoticon.emoticon_id = audio.emoticon_id\n" +
                "and audio_id = ?";
        long getAudioDetailParam = id;
        return this.jdbcTemplate.queryForObject(getAudioDetailQuery,
                (rs, rowNum) -> new GetContentDetailRes(
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("smile"),
                        rs.getInt("cry"),
                        rs.getInt("angry")
                ), getAudioDetailParam);
    }

    public int checkNewsIdExist(int id) {
        String checkContentIdExistQuery = "select exists(select news_id from news where news_id = ?)";
        long checkContentIdExistParams = id;
        return this.jdbcTemplate.queryForObject(checkContentIdExistQuery,
                int.class,
                checkContentIdExistParams);
    }

    public int checkEnNewsIdExist(int id) {
        String checkEnNewsIdExistQuery = "select exists(select en_news_id from en_news where en_news_id = ?)";
        long checkEnNewsIdExistParams = id;
        return this.jdbcTemplate.queryForObject(checkEnNewsIdExistQuery,
                int.class,
                checkEnNewsIdExistParams);
    }

    public int checkAudioIdExist(int id) {
        String checkAudioIdExistQuery = "select exists(select audio_id from audio where audio_id = ?)";
        long checkAudioIdExistParams = id;
        return this.jdbcTemplate.queryForObject(checkAudioIdExistQuery,
                int.class,
                checkAudioIdExistParams);
    }
}
