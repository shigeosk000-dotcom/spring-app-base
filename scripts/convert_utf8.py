from pathlib import Path
root = Path('.').resolve()
count = 0
for path in root.rglob('*'):
    if not path.is_file():
        continue
    if any(part.startswith('.') for part in path.relative_to(root).parts):
        continue
    try:
        data = path.read_bytes()
    except Exception:
        continue
    try:
        text = data.decode('utf-8-sig')
    except Exception:
        continue
    utf8_bytes = text.encode('utf-8')
    if data != utf8_bytes:
        path.write_bytes(utf8_bytes)
        count += 1
print(count, 'files rewritten')
