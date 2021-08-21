CREATE TABLE `Institutions` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `institution_name` varchar(255)
);

CREATE TABLE `Courses` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `course_name` varchar(255),
  `institution` int
);

CREATE TABLE `Students` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `student_name` varchar(255),
  `course` int
);

ALTER TABLE `Courses` ADD FOREIGN KEY (`institution`) REFERENCES `Institutions` (`id`);

ALTER TABLE `Students` ADD FOREIGN KEY (`course`) REFERENCES `Courses` (`id`);

INSERT INTO `institutions` (`id`, `institution_name`) VALUES (NULL, 'Multimedia University'), (NULL, 'Moi University'), (NULL, 'UON');

INSERT INTO `courses` (`id`, `course_name`, `institution`) VALUES (NULL, 'Arts', '1'), (NULL, 'Music', '1'), (NULL, 'Construction', '2'), (NULL, 'Engineering', '2'), (NULL, 'Pharmacy', '3'), (NULL, 'Software', '3');

INSERT INTO `students` (`id`, `student_name`, `course`) VALUES (NULL, 'Salah', '1'), (NULL, 'Mane', '1'), (NULL, 'Debruyne', '2'), (NULL, 'Mahrez', '2'), (NULL, 'Fernandez', '3'), (NULL, 'Pogba', '3'), (NULL, 'Haverts', '4'), (NULL, 'Lukaku', '4'), (NULL, 'Son', '5'), (NULL, 'Kane', '5'), (NULL, 'Lacazette', '6'), (NULL, 'Aubameyang', '6');
