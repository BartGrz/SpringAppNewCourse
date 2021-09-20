drop table if exists TASK_EVENTS;
CREATE table if not exists task_events
(
    id  INTEGER primary key auto_increment,
    task_id int,
    occurrence datetime,
    name varchar(30)

);