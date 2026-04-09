INSERT INTO user_list (id, name, password, authority, status) VALUES
  ('admin', 'システム管理者', 'admin-pass', TRUE, TRUE),
  ('staff01', '現場担当者A', 'staff-pass', FALSE, TRUE),
  ('staff02', '一般職員B', 'staff-pass', FALSE, FALSE);

INSERT INTO todo (user_id, title, description, due_date, priority, status) VALUES
  ('admin', 'データベース設計レビュー', '設計書を確認して、コメントを登録します。', DATE '2026-04-10', 'HIGH', FALSE),
  ('staff01', '顧客訪問準備', '訪問資料を最終確認し、パッケージを作成します。', DATE '2026-04-08', 'NORMAL', FALSE),
  ('staff01', '週次報告の提出', '進捗をまとめて上長へ報告します。', DATE '2026-04-09', 'LOW', TRUE);