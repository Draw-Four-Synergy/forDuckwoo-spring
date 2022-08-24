package com.forDukwoo.timeZip.post;

import com.forDukwoo.timeZip.post.model.GetPostRes;
import com.forDukwoo.timeZip.post.model.PostPostReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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

    public List<GetPostRes> selectPosts() {
        String selectPostsQuery = "select community_id, photo, nick, content from community, user\n" +
                "where user.user_id = community.user_id\n" +
                "order by community_id desc";

        return this.jdbcTemplate.query(selectPostsQuery,
                (rs, rowNum) -> new GetPostRes(
                        rs.getInt("community_id"),
                        rs.getString("photo"),
                        rs.getString("nick"),
                        rs.getString("content")
                ));
    }

    public List<GetPostRes> selectPostsHashtag(int hashtag) {
        String selectPostsQuery = "select community_id, photo, nick, content from community, user\n" +
                "where user.user_id = community.user_id and hashtag = ?\n" +
                "order by community_id desc;";
        int selectPostsParam = hashtag;

        return this.jdbcTemplate.query(selectPostsQuery,
                (rs, rowNum) -> new GetPostRes(
                        rs.getInt("community_id"),
                        rs.getString("photo"),
                        rs.getString("nick"),
                        rs.getString("content")
                ), selectPostsParam);
    }
}
