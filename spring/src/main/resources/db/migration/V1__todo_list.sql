create table task_list(
    id identity primary key,
    title varchar not null
);

create table task(
    id identity primary key,
    title varchar not null,
    due_date timestamp with time zone,
    is_done boolean
);

create table tasklist_task(
    list_id bigint references task_list(id),
    task_id bigint references task(id)
);

