create table if not exists goal(
    id serial primary key,
    user_id int,
    target_amount int,
    already_saved int,
    desired_date date,
    foreign key (user_id) references users(id),
    created_at timestamp default current_timestamp
)