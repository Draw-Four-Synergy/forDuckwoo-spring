package com.forDukwoo.timeZip.content;

import com.forDukwoo.timeZip.content.model.GetContentRes;
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
}
