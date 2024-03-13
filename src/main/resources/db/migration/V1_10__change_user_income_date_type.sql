alter table user_transaction_incomes drop column date;
alter table user_transaction_incomes add column date date;
alter table user_transaction_incomes alter column date set not null;