package com.expenses.walletwatch.utils;

public class ExpensesQueryHandler {
    public static final String QUERY_OPERATION = """
                SELECT amount, expenses_category.expenses_category_name
                FROM user_transaction_expenses
                JOIN user_expenses_category
                ON user_transaction_expenses.user_id = user_expenses_category.user_id
                JOIN expenses_category
                ON user_expenses_category.expense_category_id = expenses_category.id
                WHERE user_transaction_expenses.user_id = ?
                    AND date between ? and ? 
                """;
    public static final String QUERY_OPERATION_EXTRA_CONDITION = """
                    AND user_transaction_expenses.expense_category_id in (%s)
                """;

    public static String appendQuery() {
        return QUERY_OPERATION + QUERY_OPERATION_EXTRA_CONDITION;
    }
}
