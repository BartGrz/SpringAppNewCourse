Create table projects (
    id int primary key auto_increment,
    description varchar(100) not null
    );

Create table project_steps (
    id int primary key auto_increment,
    description varchar(100) not null ,
    project_id int not null ,
    foreign key (project_id) REFERENCES projects (id),
    days_to_deadline int not null
    );

Alter table task_groups
    add column project_id int null ;
alter table TASK_GROUPS
    add foreign key (project_id) REFERENCES projects (id);