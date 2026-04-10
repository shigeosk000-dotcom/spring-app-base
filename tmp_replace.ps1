 = 'src/main/java/com/example/demo/todo/TodoController.java'
 = Get-Content -Path 
 = @()
 = False
foreach ( in ) {
    if (-not  -and  -eq '    private boolean belongsToCurrentUser(Todo todo, User currentUser) {') {
         = True
         += '    private boolean belongsToCurrentUser(Todo todo, User currentUser) {'
         += '        if (todo == null || currentUser == null) {'
         += '            return false;'
         += '        }'
         += '        String taskOwner = todo.getUserId();'
         += '        String currentId = currentUser.getId();'
         += '        if (taskOwner == null || currentId == null) {'
         += '            return false;'
         += '        }'
         += '        return taskOwner.trim().equalsIgnoreCase(currentId.trim());'
         += '    }'
        continue
    }
    if () {
        if ( -eq '    }') {
             = False
        }
        continue
    }
     += 
}
Set-Content -Path  -Value 
