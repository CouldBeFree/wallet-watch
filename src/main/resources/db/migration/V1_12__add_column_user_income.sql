alter table incomes_category
    add column color varchar(255);
update incomes_category
set color = '#205673'
where id = 1;
update incomes_category
set color = '#918B32'
where id = 2;
update incomes_category
set color = '#6BBDB1'
where id = 3;
update incomes_category
set color = '#F0A715'
where id = 4;