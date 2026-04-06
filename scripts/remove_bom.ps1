$count=0
Get-ChildItem -Recurse -File | Where-Object { $_.FullName -notlike '*\\.git\\*' -and $_.FullName -notlike '*\\.m2\\*' } | ForEach-Object {
    $path = $_.FullName
    $bytes = [System.IO.File]::ReadAllBytes($path)
    if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
        $newBytes = $bytes[3..($bytes.Length - 1)]
        [System.IO.File]::WriteAllBytes($path, $newBytes)
        $count++
    }
}
Write-Output $count files rewritten
