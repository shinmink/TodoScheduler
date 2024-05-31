origin -> git

## Entity Relationship Diagram (ERD)

### User
```
+---------------------------+
|          User             |
+---------------------------+
| id: Long (PK)             |
| username: String          |
| password: String          |
| nickname: String          |
| role: UserRoleEnum        |
| createdAt: LocalDateTime  |
+---------------------------+
```

### UserRoleEnum
```
+---------------------------+
|       UserRoleEnum        |
+---------------------------+
| ADMIN                     |
| USER                      |
+---------------------------+
```

### TodoScheduler
```
+---------------------------+
|      TodoScheduler        |
+---------------------------+
| id: Long (PK)             |
| userId: Long (FK)         |
| title: String             |
| content: String           |
| state: Boolean            |
| createdAt: LocalDateTime  |
+---------------------------+
```

### Comment
```
+---------------------------+
|          Comment          |
+---------------------------+
| id: Long (PK)             |
| todoSchedulerId: Long (FK)|
| userId: Long (FK)         |
| content: String           |
| createdAt: LocalDateTime  |
+---------------------------+
```

## API 설계

| 엔티티        | 메서드    | 엔드포인트         | 설명                           | 요청 본문 예시                                                                                           | 응답 예시                                                                                              |
|---------------|-----------|-------------------|------------------------------|------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------|
| User          | POST      | /users            | 사용자 생성                     | `{"username": "test_username", "password": "test_password", "nickname": "test_nickname", "role": "USER"}` | `{"id": 1, "username": "test_username", "password": "test_password", "nickname": "test_nickname", "role": "USER", "createdAt": "2024-05-31T00:00:00"}` |
| User          | GET       | /users/{id}       | ID로 사용자 조회                 | 없음                                                                                                   | `{"id": 1, "username": "test_username", "password": "test_password", "nickname": "test_nickname", "role": "USER", "createdAt": "2024-05-31T00:00:00"}` |
| User          | GET       | /users            | 모든 사용자 조회                 | 없음                                                                                                   | `[{"id": 1, "username": "test_username", "password": "test_password", "nickname": "test_nickname", "role": "USER", "createdAt": "2024-05-31T00:00:00"}, ...]` |
| User          | DELETE    | /users/{id}       | ID로 사용자 삭제                 | 없음                                                                                                   | 없음                                                                                                    |
| TodoScheduler | POST      | /todoschedulers   | TodoScheduler 생성              | `{"userId": 1, "title": "test_title", "content": "test_content", "state": false}`                        | `{"id": 1, "userId": 1, "title": "test_title", "content": "test_content", "state": false, "createdAt": "2024-05-31T00:00:00"}`                       |
| TodoScheduler | GET       | /todoschedulers/{id} | ID로 TodoScheduler 조회         | 없음                                                                                                   | `{"id": 1, "userId": 1, "title": "test_title", "content": "test_content", "state": false, "createdAt": "2024-05-31T00:00:00"}`                       |
| TodoScheduler | GET       | /todoschedulers   | 모든 TodoScheduler 조회         | 없음                                                                                                   | `[{"id": 1, "userId": 1, "title": "test_title", "content": "test_content", "state": false, "createdAt": "2024-05-31T00:00:00"}, ...]`                |
| TodoScheduler | DELETE    | /todoschedulers/{id} | ID로 TodoScheduler 삭제         | 없음                                                                                                   | 없음                                                                                                    |
| Comment       | POST      | /comments         | Comment 생성                    | `{"todoSchedulerId": 1, "userId": 1, "content": "test_content"}`                                         | `{"id": 1, "todoSchedulerId": 1, "userId": 1, "content": "test_content", "createdAt": "2024-05-31T00:00:00"}`                                       |
| Comment       | GET       | /comments/{id}    | ID로 Comment 조회               | 없음                                                                                                   | `{"id": 1, "todoSchedulerId": 1, "userId": 1, "content": "test_content", "createdAt": "2024-05-31T00:00:00"}`                                       |
| Comment       | GET       | /comments         | 모든 Comment 조회               | 없음                                                                                                   | `[{"id": 1, "todoSchedulerId": 1, "userId": 1, "content": "test_content", "createdAt": "2024-05-31T00:00:00"}, ...]`                                |
| Comment       | DELETE    | /comments/{id}    | ID로 Comment 삭제               | 없음                                                                                                   | 없음                                                                                                    |

---



