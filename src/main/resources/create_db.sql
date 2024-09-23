CREATE DATABASE journal;

USE journal;

CREATE TABLE Team (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE Student (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    team_id INT UNSIGNED,
    FOREIGN KEY (team_id) REFERENCES Team(id) ON DELETE SET NULL
);

CREATE TABLE Lesson (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    lesson_date BIGINT NOT NULL
);

CREATE TABLE AttendanceLog (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    student_id INT UNSIGNED,
    lesson_id INT UNSIGNED,
    attended BOOLEAN NOT NULL,
    FOREIGN KEY (student_id) REFERENCES Student(id) ON DELETE CASCADE,
    FOREIGN KEY (lesson_id) REFERENCES Lesson(id) ON DELETE CASCADE,
    INDEX (student_id),
    INDEX (lesson_id)
);

INSERT INTO Team (name) VALUES
('Team Yellow'),
('Team Blue');

INSERT INTO Student (name, team_id) VALUES
('Victoria Rusalovskaya', 1),
('Rustem Andassov', 1),
('Aidyn Ryzyk', 1),
('Aliaksei Sazonau', 1),
('Denis Savchuk', 1),
('Dmitry Gaydabura', 2),
('Maksym Yarema', 2),
('Artem Voroshylin', 2),
('Nuradil Zhaxylyk', 2),
('Ruslana Buchynska', 2);


INSERT INTO Lesson (lesson_date) VALUES
(1726758000);  -- 19 Sep 2024

INSERT INTO AttendanceLog (student_id, lesson_id, attended) VALUES
(1, 1, TRUE),
(2, 1, TRUE),
(3, 1, FALSE),
(4, 1, TRUE),
(5, 1, TRUE),
(6, 1, TRUE),
(7, 1, TRUE),
(8, 1, TRUE),
(9, 1, TRUE),
(10, 1, TRUE);
