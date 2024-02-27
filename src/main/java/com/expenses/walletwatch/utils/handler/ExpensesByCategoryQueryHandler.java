package com.expenses.walletwatch.utils.handler;

public class ExpensesByCategoryQueryHandler {
    public static final String QUERY_OPERATION = """
                SELECT SUM(amount), user_transaction_expenses.expense_category_id, expenses_category.expenses_category_name
                FROM user_transaction_expenses
                JOIN user_expenses_category
                ON user_transaction_expenses.user_id = user_expenses_category.user_id
                AND user_transaction_expenses.expense_category_id = user_expenses_category.id
                JOIN expenses_category
                ON user_expenses_category.expense_category_id = expenses_category.id
                WHERE user_transaction_expenses.user_id = ?
                    AND date between ? and ?
                """;
    public static final String QUERY_OPERATION_EXTRA_CONDITION = """
                    AND user_transaction_expenses.expense_category_id in (%s)
                    GROUP BY expenses_category.expenses_category_name, user_transaction_expenses.expense_category_id;
                """;

    public static String appendQuery() {
        return QUERY_OPERATION + QUERY_OPERATION_EXTRA_CONDITION;
    }
}
