package com.forDukwoo.timeZip.post;

import com.forDukwoo.timeZip.post.model.PostPostReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository

public class PostDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int insertPosts(PostPostReq postPostReq) {
        String insertPostsQuery = "insert into community (title, content, hashtag, user_id) values (?, ?, ?, ?)";
        Object[] insertResultsParams = new Object[]{postPostReq.getTitle(), postPostReq.getContent(), postPostReq.getHashtag(), postPostReq.getUserId()};
        this.jdbcTemplate.update(insertPostsQuery, insertResultsParams);
        String lastInsertIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdxQuery, int.class);
    }

}
