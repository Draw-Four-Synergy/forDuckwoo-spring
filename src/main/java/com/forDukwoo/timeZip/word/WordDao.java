package com.forDukwoo.timeZip.word;

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

    public void deleteWord(int userId, int dictionaryId) {
        String deleteWordQuery = "delete from dictionary where user_id = ? and dictionary_id = ?";
        Object[] deleteWordParams = new Object[]{userId, dictionaryId};
        this.jdbcTemplate.update(deleteWordQuery, deleteWordParams);
    }

    public int checkDictionaryIdExist(int dictionaryId) {
        String checkDictionaryIdExistQuery = "select exists(select dictionary_id as dictionaryId from dictionary where dictionary_id = ?)";
        long checkDictionaryIdExistParams = dictionaryId;
        return this.jdbcTemplate.queryForObject(checkDictionaryIdExistQuery,
                int.class,
                checkDictionaryIdExistParams);
    }

    public int checkDuplicateWord(int dictionaryId, int userId) {
        String checkDuplicateWordQuery = "select exists(select dictionary_id as dictionaryId, user_id from dictionary where dictionary_id = ? and user_id = ?)";
        int checkDuplicateWordParams1 = dictionaryId;
        int checkDuplicateWordParams2 = userId;
        return this.jdbcTemplate.queryForObject(checkDuplicateWordQuery, int.class, checkDuplicateWordParams1, checkDuplicateWordParams2);

    }
}
