package com.expenses.walletwatch.utils.handler;

public class IncomesByCategoryQueryHandler {
    public static final String GET_INCOMES_BY_CATEGORY = """
                SELECT SUM(amount), user_transaction_incomes.income_category_id, incomes_category.incomes_category_name
                FROM user_transaction_incomes
                JOIN user_incomes_category
                ON user_transaction_incomes.user_id = user_incomes_category.user_id
                AND user_transaction_incomes.income_category_id = user_incomes_category.id
                JOIN incomes_category
                ON user_incomes_category.income_category_id = incomes_category.id
                WHERE user_transaction_incomes.user_id = ?
                AND date between ? and ?
                """;

    public static final String QUERY_OPERATION_EXTRA_CONDITION = """
                    AND user_transaction_incomes.income_category_id in (%s)
                    GROUP BY incomes_category.incomes_category_name, user_transaction_incomes.income_category_id;
                """;

    public static String appendQuery() {
        return GET_INCOMES_BY_CATEGORY + QUERY_OPERATION_EXTRA_CONDITION;
    }
}
