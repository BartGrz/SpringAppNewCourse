

CREATE table if not exists tasks (

    id int primary key auto_increment ,
    description varchar(100) not null,
    done bit
    );
alter table tasks alter column id int auto_increment;