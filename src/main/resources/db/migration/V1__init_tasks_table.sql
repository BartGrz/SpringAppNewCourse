drop table if exists tasks;

CREATE table if not exists tasks(

   id INTEGER primary key auto_increment,
   description varchar(100) not null,
    done bit
);