alter table goal add column currency varchar(3) not null
check (currency in ('usd', 'uah'));