INSERT INTO singer (name) VALUES
('Madonna'),
('Michael Jackson'),
('Billie Eilish'),
('Metallica');
commit;

INSERT INTO company (name) VALUES
('Black Company'),
('The best company'),
('Europe label'),
('Imagine label');
commit;


INSERT INTO recording (song_code, title, version, release_time, singer_id ) VALUES
('SN 123', 'Frozen', '1', '1008-01-15', 1);

INSERT INTO recording (song_code, title, version, release_time, singer_id ) VALUES
('SN 125', 'Earth songs', '1', '1982-01-15', 2);

INSERT INTO recording (song_code, title, version, release_time, singer_id ) VALUES
('SN 126', 'Bad Guy', '1', '2019-01-15', 3);

INSERT INTO recording (song_code, title, version, release_time, singer_id ) VALUES
('SN 127', 'Nothing else matters', '1', '1991-01-15', 4);


INSERT INTO copyright (start_time, end_time, fee, company_id, recording_id) VALUES
    ('1008-01-15', '2038-01-15', 1.57, 1, 1);

INSERT INTO copyright (start_time, end_time, fee, company_id, recording_id) VALUES
    ('2028-01-15', '2038-01-15', 2.73, 2, 1);

INSERT INTO copyright (start_time, end_time, fee, company_id, recording_id) VALUES
    ('2019-01-15', '2024-01-15', 1.82, 3, 3);

INSERT INTO copyright (start_time, end_time, fee, company_id, recording_id) VALUES
    ('2019-01-15', '2024-01-15', 3.54, 4, 3);

INSERT INTO copyright (start_time, end_time, fee, company_id, recording_id) VALUES
    ('2019-01-15', '2020-01-15', 2.85, 2, 3);
