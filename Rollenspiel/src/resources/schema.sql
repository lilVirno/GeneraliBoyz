SET REFERENTIAL_INTEGRITY FALSE;
DROP TABLE IF EXISTS Question_Theme;
DROP TABLE IF EXISTS gap_field;
DROP TABLE IF EXISTS mc_answer;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS question_set;
DROP TABLE IF EXISTS theme;
DROP TABLE IF EXISTS team;
SET REFERENTIAL_INTEGRITY TRUE;

CREATE TABLE team (
  team_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE theme (
  theme_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT
);

CREATE TABLE question_set (
  question_set_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  team_id INT NOT NULL,
  title VARCHAR(255) NOT NULL,
  CONSTRAINT fk_question_set_team FOREIGN KEY (team_id) REFERENCES team(team_id) ON DELETE CASCADE
);

CREATE TABLE question (
  question_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  question_set_id INT NOT NULL,
  question_type ENUM('MC','TF','GAP') NOT NULL,
  start_text TEXT,
  image_url TEXT,
  end_text TEXT,
  allows_multiple TINYINT(1) DEFAULT 0,
  points INT DEFAULT 1,
  CONSTRAINT fk_question_question_set FOREIGN KEY (question_set_id) REFERENCES question_set(question_set_id) ON DELETE CASCADE
);

CREATE TABLE Question_Theme (
  question_id INT NOT NULL,
  theme_id INT NOT NULL,
  PRIMARY KEY (question_id, theme_id),
  CONSTRAINT fk_qt_question FOREIGN KEY (question_id) REFERENCES question(question_id) ON DELETE CASCADE,
  CONSTRAINT fk_qt_theme FOREIGN KEY (theme_id) REFERENCES theme(theme_id)
);

CREATE TABLE mc_answer (
  answer_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  question_id INT NOT NULL,
  option_text TEXT,
  points INT DEFAULT 0,
  is_correct TINYINT(1) DEFAULT 0,
  option_order INT,
  CONSTRAINT fk_mc_question FOREIGN KEY (question_id) REFERENCES question(question_id) ON DELETE CASCADE
);

CREATE TABLE gap_field (
  gap_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  question_id INT NOT NULL,
  gap_index INT,
  input_type VARCHAR(50),
  correct_text TEXT,
  case_sensitive TINYINT(1) DEFAULT 0,
  CONSTRAINT fk_gap_question FOREIGN KEY (question_id) REFERENCES question(question_id) ON DELETE CASCADE
);

-- Seed Data
INSERT INTO team (team_id, name) VALUES (5, 'GBoyz');

INSERT INTO theme (theme_id, name, description) VALUES (5, 'SQL', 'Datenbankabfragen');
INSERT INTO theme (theme_id, name, description) VALUES (2, 'RECHT', 'Gesetzesgrundlagen');
INSERT INTO theme (theme_id, name, description) VALUES (3, 'WIRTSCHAFT', 'Prozesse');
INSERT INTO theme (theme_id, name, description) VALUES (4, 'DATENBANKEN_MODELLIERUNG', 'ERD und Modellierung');

INSERT INTO question_set (question_set_id, team_id, title) VALUES (1, 5, 'GBoyz Datenbank - SQL');

-- Fragen
INSERT INTO question (question_id, question_set_id, question_type, start_text, allows_multiple) VALUES
(1, 1, 'MC', 'In SQL wird ein SELECT-Statement verwendet, um Daten aus Tabellen zu lesen. Welche Anweisung liest alle Spalten einer Tabelle customer aus?', FALSE),
(2, 1, 'MC', 'Ein Primärschlüssel stellt Eindeutigkeit sicher. Welche Eigenschaft trifft nicht auf einen Primärschlüssel zu?', FALSE),
(3, 1, 'MC', 'Das WHERE-Statement dient der Filterung. Welche Abfrage liefert alle Kunden mit age > 30?', FALSE),
(4, 1, 'MC', 'Ein JOIN verbindet Tabellen basierend auf Beziehungen. Welche JOIN-Art liefert nur Zeilen mit passendem Match in beiden Tabellen?', FALSE),
(5, 1, 'MC', 'GROUP BY wird zur Aggregation genutzt. Welche Spalte muss im GROUP BY stehen?', FALSE),
(6, 1, 'MC', 'Aggregatfunktionen berechnen Werte über Zeilen hinweg. Welche der folgenden Funktionen sind Aggregatfunktionen? (Mehrfachauswahl)', TRUE),
(7, 1, 'MC', 'Transaktionen bündeln SQL-Operationen. Welche Anweisungen gehören zu Transaktionen? (Mehrfachauswahl)', TRUE),
(8, 1, 'MC', 'Um eine Ausgabe aus einer oder mehreren Tabellen in der absteigende Reihenfolge aufzulisten, verwendet man', FALSE),
(9, 1, 'MC', 'UPDATE verändert bestehende Zeilen. Welche Teile gehören zu einem vollständigen UPDATE?', TRUE),
(10, 1, 'MC', 'SQL-Tabellen nutzen definierte Datentypen. Welche gehören zu SQL-Standarddatentypen? (Mehrfachauswahl)', TRUE),
(11, 1, 'MC', 'Die Normalisierung reduziert Redundanz. Welche Normalform verlangt atomare Werte?', FALSE),
(12, 1, 'MC', 'SQL nutzt bestimmte Schlüsselwörter zum Filtern. Wähle das passende Schlüsselwort.', FALSE),
(13, 1, 'MC', 'SQL kennt unterschiedliche Delete-Operationen. Welche Operation löscht alle Zeilen und ist schneller als DELETE?', FALSE),
(14, 1, 'MC', 'Stored Procedures speichern SQL-Logik. Was zeichnet Stored Procedures aus? (Mehrfachauswahl)', TRUE),
(15, 1, 'MC', 'In SQL gibt es ein spezielles Schlüsselwort, das verwendet wird, um sicherzustellen, dass eine Abfrage nur eindeutige Werte aus einer Spalte zurückgibt.', FALSE),
(16, 1, 'TF', 'Ein PRIMARY KEY darf doppelte Werte enthalten.', FALSE),
(17, 1, 'TF', 'Ein FOREIGN KEY verweist auf einen PRIMARY KEY einer anderen Tabelle.', FALSE),
(18, 1, 'TF', 'SQL ist eine deklarative Sprache.', FALSE),
(19, 1, 'TF', 'DELETE löscht die gesamte Tabelle.', FALSE),
(20, 1, 'TF', 'Ein LEFT JOIN liefert auch Zeilen ohne Match.', FALSE),
(21, 1, 'TF', 'Ein Index kann Abfragen beschleunigen.', FALSE),
(22, 1, 'TF', 'NOT NULL bedeutet, dass ein Wert leer sein darf.', FALSE),
(23, 1, 'TF', 'COUNT(*) zählt nur nicht-NULL Werte.', FALSE),
(24, 1, 'TF', 'Ein View speichert keine Daten, sondern eine Abfrage.', FALSE),
(25, 1, 'TF', 'Ein UNIQUE-Constraint verhindert NULL-Werte.', FALSE),
(26, 1, 'TF', 'Tabellen können mehrere FOREIGN KEYS besitzen.', FALSE),
(27, 1, 'GAP', 'Zum Erstellen einer Tabelle verwendet man das Schlüsselwort ____.', FALSE),
(28, 1, 'GAP', 'Der Befehl zum Löschen einer Tabelle lautet ____.', FALSE),
(29, 1, 'GAP', 'In SQL werden Ergebnisse standardmäßig ____ sortiert.', FALSE),
(30, 1, 'GAP', 'Der Operator für Mustervergleiche lautet ____.', FALSE),
(31, 1, 'GAP', 'Die Klausel, die die Anzahl der Zeilen reduziert, heißt ____.', FALSE),
(32, 1, 'GAP', 'Mit ____ werden neue Zeilen eingefügt.', FALSE),
(33, 1, 'GAP', 'JOIN-Bedingungen stehen üblicherweise nach dem Schlüsselwort ____.', FALSE),
(34, 1, 'GAP', 'Eine SQL-Abfrage endet mit einem ____.', FALSE),
(35, 1, 'GAP', 'Ein Index wird mit ____ erstellt.', FALSE),
(36, 1, 'GAP', 'Die Klausel ____ sortiert Abfrageergebnisse.', FALSE),
(37, 1, 'GAP', 'Mit ____ können Zeilen gruppiert werden.', FALSE),
(38, 1, 'GAP', 'Die Funktion ____ berechnet den Durchschnitt.', FALSE),
(39, 1, 'TF', 'DISTINCT entfernt doppelte Werte.', FALSE),
(40, 1, 'TF', 'SQL verwendet Schlüsselwörter zur Datenanalyse.', FALSE);

-- Zuordnung Fragen zu Themen
INSERT INTO Question_Theme (question_id, theme_id)
SELECT question_id, 5 FROM question;

-- Antworten
INSERT INTO mc_answer (question_id, option_text, is_correct, option_order) VALUES
(1, 'SELECT customer FROM *;', FALSE, 1), (1, 'SELECT all FROM customer;', FALSE, 2), (1, 'SELECT * FROM customer;', TRUE, 3), (1, 'GET ALL customer;', FALSE, 4),
(2, 'Eindeutigkeit muss gewährleistet werden.', FALSE, 1), (2, 'NULL-Werte sind zulässig.', TRUE, 2), (2, 'Er identifiziert eine Zeile eindeutig.', FALSE, 3), (2, 'Er besteht aus einer oder mehreren Spalten.', FALSE, 4),
(3, 'SELECT * FROM customer WHERE age > 30;', TRUE, 1), (3, 'SELECT * FROM customer HAVING age > 30;', FALSE, 2), (3, 'FILTER customer BY age > 30;', FALSE, 3), (3, 'SELECT age > 30 FROM customer;', FALSE, 4),
(4, 'FULL OUTER JOIN', FALSE, 1), (4, 'LEFT JOIN', FALSE, 2), (4, 'INNER JOIN', TRUE, 3), (4, 'RIGHT JOIN', FALSE, 4),
(5, 'Jede Spalte, die nicht aggregiert wird.', TRUE, 1), (5, 'Nur Spalten mit Datentyp INT.', FALSE, 2), (5, 'Nur Primärschlüssel.', FALSE, 3), (5, 'Keine Spalten.', FALSE, 4),
(6, 'COUNT()', TRUE, 1), (6, 'SUM()', TRUE, 2), (6, 'MAX()', TRUE, 3), (6, 'WHERE', FALSE, 4),
(7, 'COMMIT', TRUE, 1), (7, 'ROLLBACK', TRUE, 2), (7, 'SAVEPOINT', TRUE, 3), (7, 'TRUNCATE', FALSE, 4),
(8, 'AVG()', FALSE, 1), (8, 'HAVING', FALSE, 2), (8, 'DESC', TRUE, 3), (8, 'COUNT()', FALSE, 4),
(9, 'UPDATE-Klausel', TRUE, 1), (9, 'SET-Klausel', TRUE, 2), (9, 'WHERE-Klausel (optional)', TRUE, 3), (9, 'ORDER-BY-Klausel', FALSE, 4),
(10, 'VARCHAR', TRUE, 1), (10, 'BOOLEAN', TRUE, 2), (10, 'INTEGER', TRUE, 3), (10, 'STRING', FALSE, 4),
(11, '1. Normalform', TRUE, 1), (11, '2. Normalform', FALSE, 2), (11, '3. Normalform', FALSE, 3), (11, 'Boyce-Codd Normalform', FALSE, 4),
(12, 'UNIQUE', FALSE, 1), (12, 'ORDER', FALSE, 2), (12, 'WHERE', TRUE, 3), (12, 'JOIN', FALSE, 4),
(13, 'CLEAR TABLE', FALSE, 1), (13, 'TRUNCATE', TRUE, 2), (13, 'FAST DELETE', FALSE, 3), (13, 'DROP ALL', FALSE, 4),
(14, 'Können Parameter besitzen', TRUE, 1), (14, 'Sind reine Views', FALSE, 2), (14, 'Können Schleifen enthalten', TRUE, 3), (14, 'Werden automatisch bei jedem SELECT ausgeführt', FALSE, 4),
(15, 'GROUP', FALSE, 1), (15, 'DISTINCT', TRUE, 2), (15, 'INDEX', FALSE, 3), (15, 'FOREIGN', FALSE, 4),
(16, 'True', FALSE, 1), (16, 'False', TRUE, 2),
(17, 'True', TRUE, 1), (17, 'False', FALSE, 2),
(18, 'True', TRUE, 1), (18, 'False', FALSE, 2),
(19, 'True', FALSE, 1), (19, 'False', TRUE, 2),
(20, 'True', TRUE, 1), (20, 'False', FALSE, 2),
(21, 'True', TRUE, 1), (21, 'False', FALSE, 2),
(22, 'True', FALSE, 1), (22, 'False', TRUE, 2),
(23, 'True', FALSE, 1), (23, 'False', TRUE, 2),
(24, 'True', TRUE, 1), (24, 'False', FALSE, 2),
(25, 'True', FALSE, 1), (25, 'False', TRUE, 2),
(26, 'True', TRUE, 1), (26, 'False', FALSE, 2),
(39, 'True', TRUE, 1), (39, 'False', FALSE, 2),
(40, 'True', TRUE, 1), (40, 'False', FALSE, 2);

-- Lückentexte
INSERT INTO gap_field (question_id, gap_index, input_type, correct_text) VALUES
(27, 1, 'FREE_TEXT', 'CREATE'),
(28, 1, 'FREE_TEXT', 'DROP TABLE'),
(29, 1, 'FREE_TEXT', 'aufsteigend'),
(30, 1, 'FREE_TEXT', 'LIKE'),
(31, 1, 'FREE_TEXT', 'WHERE'),
(32, 1, 'FREE_TEXT', 'INSERT INTO'),
(33, 1, 'FREE_TEXT', 'ON'),
(34, 1, 'FREE_TEXT', 'Semikolon'),
(35, 1, 'FREE_TEXT', 'CREATE INDEX'),
(36, 1, 'FREE_TEXT', 'ORDER BY'),
(37, 1, 'FREE_TEXT', 'GROUP BY'),
(38, 1, 'FREE_TEXT', 'AVG');

-- --- Tabellen für Spielstand-Speicherung ---

CREATE TABLE IF NOT EXISTS player (
  player_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  score INT DEFAULT 0,
  rank_name VARCHAR(50) DEFAULT 'Anfänger'
);

CREATE TABLE IF NOT EXISTS player_progress (
  player_id INT NOT NULL,
  question_id INT NOT NULL,
  solved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (player_id, question_id),
  CONSTRAINT fk_progress_player FOREIGN KEY (player_id) REFERENCES player(player_id) ON DELETE CASCADE,
  CONSTRAINT fk_progress_question FOREIGN KEY (question_id) REFERENCES question(question_id) ON DELETE CASCADE
);