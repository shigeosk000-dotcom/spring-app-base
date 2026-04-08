INSERT INTO user_list (id, name, password, authority, status) VALUES
  ('admin', 'システム管理者', 'admin-pass', TRUE, TRUE),
  ('staff01', '現場担当者A', 'staff-pass', FALSE, TRUE),
  ('staff02', '一般職員B', 'staff-pass', FALSE, FALSE);

INSERT INTO todo (user_id, title, description, due_date, priority, status) VALUES
  ('admin', 'リリース確認', 'リリース前の依存ライブラリと環境設定を再確認する。', DATE '2026-04-10', 'HIGH', FALSE),
  ('staff01', 'ドキュメント整理', '仕様書と議事録をひとまとめにし、共有ドライブに保存する。', DATE '2026-04-08', 'NORMAL', FALSE),
  ('staff01', '週次報告', '進捗と懸念点をまとめて部門長に報告する。', DATE '2026-04-09', 'LOW', TRUE);
