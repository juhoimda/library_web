package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


//UserRepository를 스프링빈으로 등록
@Repository
public class UserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isUserNotExist(long id){
        String readSql = "SELECT * FROM user WHERE id = ?";
        //내용이 있으면 0이 담긴 리스트 , 내용이 없으면 빈 리스트 반환
        return jdbcTemplate.query(readSql, (rs, rowNum)-> 0, id).isEmpty();
    }

    public void updateUserName(String name, long id){
        String sql = "UPDATE user SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, id);
    }

    public boolean isUserNotExist(String name){
        String readSql = "SELECT * FROM user WHERE name = ?";
        //내용이 있으면 0이 담긴 리스트 , 내용이 없으면 빈 리스트 반환
        return jdbcTemplate.query(readSql, (rs, rowNum)-> 0, name).isEmpty();
    }

    public void deleteUser(String name){
        String sql = "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(sql, name);
    }

    public void saveUser(String name, Integer age){
        String sql = "INSERT INTO user (name, age) VALUES (?,?)";
        jdbcTemplate.update(sql, name, age);
    }

    public List<UserResponse> getUsers(){
        String sql ="SELECT*FROM user";

        //query는 내용을 리스트형태로 만듦.
        return jdbcTemplate.query(sql, new RowMapper<UserResponse>() {
            @Override
            public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                return new UserResponse(id, name, age);
            }
        });
    }
}
