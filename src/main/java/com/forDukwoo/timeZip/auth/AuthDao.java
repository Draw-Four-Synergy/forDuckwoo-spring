package com.forDukwoo.timeZip.auth;

import com.forDukwoo.timeZip.auth.model.PostLoginReq;
import com.forDukwoo.timeZip.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class AuthDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User getUserInfo(PostLoginReq postLoginReq){
        String getPwdQuery = "select user_id, pwd from user where email=?";
        String getPwdParams = postLoginReq.getEmail();

        try{
        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                        rs.getLong("user_id"),
                        rs.getString("pwd")
                ),
                getPwdParams
        );
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
