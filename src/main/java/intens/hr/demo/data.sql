USE `intens-java`;

INSERT INTO skills (id, name) VALUES
(1, 'java'),
(2, 'python'),
(3, 'rust'),
(4, 'spring boot'),
(5, 'angular'),
(6, 'react'),
(7, 'docker'),
(8, 'kafka'),
(9, 'js'),
(10, 'ts'),
(11, 'english'),
(12, 'serbian');

INSERT INTO candidates (id, full_name, date_of_birth, contact_number, email) VALUES
(1, 'Test Candidate', '2001-01-01', '123456789', 'test@gmail.com'),
(2, 'Another Candidate', '1999-12-03', '5435345543', 'another@gmail.com'),
(3, 'Yet Another', '2000-07-17', '657656756', 'yet@gmail.com');

INSERT INTO candidate_skills (candidate_id, skill_id) VALUES
(1, 1),
(1, 3),
(1, 4);

INSERT INTO candidate_skills (candidate_id, skill_id) VALUES
(2, 2),
(2, 7),
(2, 9),
(2, 10);

INSERT INTO candidate_skills (candidate_id, skill_id) VALUES
(3, 3),
(3, 5),
(3, 6),
(3, 7);