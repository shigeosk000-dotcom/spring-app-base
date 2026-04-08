from pathlib import Path
path = Path('doc/fsd.md').read_bytes()
print(path[:64])
print(path.decode('utf-8')[:200])
print(path.decode('cp932', errors='replace')[:200])
import os, sys
print('PYTHONUTF8=' + str(os.environ.get('PYTHONUTF8')))
print('stdout encoding=' + str(sys.stdout.encoding))
