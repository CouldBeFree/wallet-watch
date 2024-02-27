package com.expenses.walletwatch.utils.handler;

public class ExpensesByPeriodQueryHandler {

    public static final String GET_EXPENSES_BY_PERIOD = """
                SELECT SUM(amount), user_transaction_expenses.expense_category_id, expenses_category.expenses_category_name
                FROM user_transaction_expenses
                JOIN user_expenses_category
                ON user_transaction_expenses.user_id = user_expenses_category.user_id
                AND user_transaction_expenses.expense_category_id = user_expenses_category.id
                JOIN expenses_category
                ON user_expenses_category.expense_category_id = expenses_category.id
                WHERE user_transaction_expenses.user_id = ?
                AND date between ? and ?
                GROUP BY expenses_category.expenses_category_name, user_transaction_expenses.expense_category_id;
                """;
}
