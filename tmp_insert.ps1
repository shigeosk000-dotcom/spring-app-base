 = 'src/main/java/com/example/demo/todo/TodoMapper.java'
 = Get-Content -Path 
 = [char]64
 = '    Todo findById(' +  + 'Param( id) Long id);'
 = '    Todo findByIdAndUserId(' +  + 'Param(id) Long id, ' +  + 'Param(userId) String userId);'
 = [Array]::IndexOf(, )
if ( -ge 0) {
     = [0..]
     = [(+1)..(.Length-1)]
    Set-Content -Path  -Value ( +  + )
}
