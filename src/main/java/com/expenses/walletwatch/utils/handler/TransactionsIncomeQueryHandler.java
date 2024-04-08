package com.expenses.walletwatch.utils.handler;

public class TransactionsIncomeQueryHandler {
    public static final String GET_INCOMES_TRANSACTION = """
            select user_transaction_incomes.id, amount, date, incomes_category.incomes_category_name from user_transaction_incomes
            left outer join user_incomes_category
            on user_transaction_incomes.income_category_id = user_incomes_category.id
            left outer join incomes_category
            on user_incomes_category.income_category_id = incomes_category.id
            where user_incomes_category.user_id = ?
            """;

    public static final String DATE_RANGE = """
            and date between to_date(?, 'YYYY-MM-DD') and to_date(?, 'YYYY-MM-DD')
            """;
    public static final String ORDER_BY_ID = """
            order by date desc;
            """;

    public static String appendQuery() {
        return GET_INCOMES_TRANSACTION + DATE_RANGE;
    }
}
