alter table goal add column goal_name varchar(50) not null;
alter table goal add constraint check_already_saved_less_than_target check ( already_saved < target_amount );