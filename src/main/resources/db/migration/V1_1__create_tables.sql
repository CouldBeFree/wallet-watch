-- Database: expense

CREATE TABLE IF NOT EXISTS users(
    id serial primary key,
    password varchar(255),
    username varchar(255) unique not null,
    email varchar(255) unique not null,
    registrationDate timestamp default current_timestamp
);

CREATE TABLE IF NOT EXISTS expenses_category(
    id serial primary key,
    expenses_category_name varchar(255) unique not null
);

CREATE TABLE IF NOT EXISTS incomes_category(
    id serial primary key,
    incomes_category_name varchar(255) unique not null
);

CREATE TABLE IF NOT EXISTS user_expenses_category(
    id serial primary key,
    user_id int,
    expense_category_id int,
    foreign key (user_id) references users(id),
    foreign key (expense_category_id) references expenses_category(id),
    constraint uc_user_expenses_category unique (user_id, expense_category_id)
);

CREATE TABLE IF NOT EXISTS user_incomes_category(
    id serial primary key,
    user_id int,
    income_category_id int,
    foreign key (user_id) references users(id),
    foreign key (income_category_id) references incomes_category(id)
);

CREATE TABLE IF NOT EXISTS user_transaction_expenses(
    id serial primary key,
    amount int not null,
    date varchar(20),
    user_id int,
    expense_category_id int,
    foreign key (user_id) references users(id),
    foreign key (expense_category_id) references user_expenses_category(id)
);

CREATE TABLE IF NOT EXISTS user_transaction_incomes(
    id serial primary key,
    amount int not null,
    date varchar(20),
    user_id int,
    income_category_id int,
    foreign key (user_id) references users(id),
    foreign key (income_category_id) references user_incomes_category(id)
);