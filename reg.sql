insert into profiles (avatar_url, email, first_name, last_name, patronymic, is_active, profile_type)
values ('', 'andrejkingsley@gmail.com', 'Nikita', 'Bosov', 'Alexeevich', false, 0);
insert into students (profile_id, university, faculty, step, course, "from", "to", gpa, cv_url) VALUES
    (1, 'SPbSU', 'MCS', 'Bachelor', 4, 'Sep 2018', 'Jul 2022', 4.92, '');

insert into chats(user1_id, user2_id, msg_cnt) values (1, 2, 0);