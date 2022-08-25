package com.forDukwoo.timeZip.word;

import com.forDukwoo.timeZip.user.model.GetScrapRes;
import com.forDukwoo.timeZip.word.model.GetWordRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class WordDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetWordRes> selectWord(int userId) {
        String selectWordQuery = "select dictionary_id as dictionaryId, word, meaning1, meaning2\n" +
                "from dictionary\n" +
                "where user_id = ?";
        int selectWordParam = userId;

        return this.jdbcTemplate.query(selectWordQuery,
                (rs, rowNum) -> new GetWordRes(
                        rs.getInt("dictionaryId"),
                        rs.getString("word"),
                        rs.getString("meaning1"),
                        rs.getString("meaning1")
                ), selectWordParam);
    }
}
