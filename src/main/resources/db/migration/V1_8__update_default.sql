alter table user_expenses_category drop column is_active;
alter table user_expenses_category add column is_active boolean default true;