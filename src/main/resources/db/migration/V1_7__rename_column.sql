alter table user_expenses_category drop column isactive;
alter table user_expenses_category add column is_active boolean default false;