origin -> git


+----------------------+
|        User          |
+----------------------+
| id: Long (PK)        |
| username: String     |
| password: String     |
| nickname: String     |
| role: UserRoleEnum   |
| createdAt: LocalDateTime |
+----------------------+

+----------------------+
|    UserRoleEnum      |
+----------------------+
| ADMIN                |
| USER                 |
+----------------------+

+----------------------+
|    TodoScheduler     |
+----------------------+
| id: Long (PK)        |
| userId: Long (FK)    |
| title: String        |
| content: String      |
| state: Boolean       |
| createdAt: LocalDateTime |
+----------------------+

+----------------------+
|       Comment        |
+----------------------+
| id: Long (PK)        |
| todoSchedulerId: Long (FK) |
| userId: Long (FK)    |
| content: String      |
| createdAt: LocalDateTime |
+----------------------+


