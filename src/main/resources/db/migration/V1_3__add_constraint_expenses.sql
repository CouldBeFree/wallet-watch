alter table user_incomes_category add constraint uc_user_incomes_category unique (user_id, income_category_id);