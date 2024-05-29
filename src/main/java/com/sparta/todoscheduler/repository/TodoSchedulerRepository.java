package com.sparta.todoscheduler.repository;

import com.sparta.todoscheduler.dto.TodoSchedulerRequestDto;
import com.sparta.todoscheduler.dto.TodoSchedulerResponseDto;
import com.sparta.todoscheduler.entity.TodoScheduler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.List;

@Repository
public class TodoSchedulerRepository {

    private final JdbcTemplate jdbcTemplate;

    public TodoSchedulerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(TodoScheduler todoscheduler) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO scheduler (title, contents, username, password, date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, todoscheduler.getTitle());
            preparedStatement.setString(2, todoscheduler.getContents());
            preparedStatement.setString(3, todoscheduler.getUsername());
            preparedStatement.setString(4, todoscheduler.getPassword());
            preparedStatement.setDate(5, todoscheduler.getDate());
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<TodoSchedulerResponseDto> findAll() {
        String sql = "SELECT * FROM scheduler";
        return jdbcTemplate.query(sql, new RowMapper<TodoSchedulerResponseDto>() {
            @Override
            public TodoSchedulerResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String contents = rs.getString("contents");
                String username = rs.getString("username");
                String password = rs.getString("password");
                Date date = rs.getDate("date");
                return new TodoSchedulerResponseDto(id, title, contents, username, password, date);
            }
        });
    }

    public TodoScheduler findById(Long id) {
        String sql = "SELECT * FROM scheduler WHERE id = ?";
        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                TodoScheduler todoscheduler = new TodoScheduler();
                todoscheduler.setId(resultSet.getLong("id"));
                todoscheduler.setTitle(resultSet.getString("title"));
                todoscheduler.setContents(resultSet.getString("contents"));
                todoscheduler.setUsername(resultSet.getString("username"));
                todoscheduler.setPassword(resultSet.getString("password"));
                todoscheduler.setDate(resultSet.getDate("date"));
                return todoscheduler;
            } else {
                return null;
            }
        }, id);
    }

    public void update(Long id, TodoSchedulerRequestDto requestDto) {
        String sql = "UPDATE scheduler SET title = ?, contents = ?, username = ?, password = ?, date = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getContents(), requestDto.getUsername(),
                requestDto.getPassword(), requestDto.getDate(), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM scheduler WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
