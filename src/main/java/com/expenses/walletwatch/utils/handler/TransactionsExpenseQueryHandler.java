package com.expenses.walletwatch.utils.handler;

public class TransactionsExpenseQueryHandler {
    public static final String GET_EXPENSES_TRANSACTIONS = """
            select user_transaction_expenses.id, amount, date, expenses_category.expenses_category_name from user_transaction_expenses
            left outer join user_expenses_category
            on user_transaction_expenses.expense_category_id = user_expenses_category.id
            left outer join expenses_category
            on user_expenses_category.expense_category_id = expenses_category.id
            where user_expenses_category.user_id = ?
            """;

    public static final String DATE_RANGE = """
            and date between to_date(?, 'YYYY-MM-DD') and to_date(?, 'YYYY-MM-DD')
            """;
    public static final String ORDER_BY_ID = """
            order by date desc;
            """;

    public static String appendQuery() {
        return GET_EXPENSES_TRANSACTIONS + DATE_RANGE;
    }
}
