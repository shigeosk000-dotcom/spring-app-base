 = Get-Content -Path src/main/java/com/example/demo/todo/TodoController.java -Raw
 = '    private boolean belongsToCurrentUser\(Todo todo, User currentUser\) \{.*?return todo != null && currentUser != null && Objects\.equals\(todo\.getUserId\(\), currentUser\.getId\(\)\);\s*    \}'
 = @'
    private boolean belongsToCurrentUser(Todo todo, User currentUser) {
        if (todo == null || currentUser == null) {
            return false;
        }
        String taskOwner = todo.getUserId();
        String currentId = currentUser.getId();
        if (taskOwner == null || currentId == null) {
            return false;
        }
        return taskOwner.trim().equalsIgnoreCase(currentId.trim());
    }
'@
[regex]::Replace(, , , [System.Text.RegularExpressions.RegexOptions]::Singleline) | Set-Content -Path src/main/java/com/example/demo/todo/TodoController.java
