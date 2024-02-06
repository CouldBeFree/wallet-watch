alter table user_transaction_expenses drop column date;
alter table user_transaction_expenses add column date date;
alter table user_transaction_expenses alter column date set not null;