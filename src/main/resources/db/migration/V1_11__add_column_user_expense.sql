alter table expenses_category
add column color varchar(255);
update expenses_category
set color = '#20B15F'
where id = 1;
update expenses_category
set color = '#F9DC00'
where id = 2;
update expenses_category
set color = '#150721'
where id = 3;
update expenses_category
set color = '#273283'
where id = 4;
update expenses_category
set color = '#446A08'
where id = 5;
update expenses_category
set color = '#DA53EB'
where id = 6;
update expenses_category
set color = '#CA7142'
where id = 7;
update expenses_category
set color = '#9E042C'
where id = 8;
update expenses_category
set color = '#D7C9B2'
where id = 9;