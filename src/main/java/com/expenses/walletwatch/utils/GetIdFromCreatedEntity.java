package com.expenses.walletwatch.utils;

import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GetIdFromCreatedEntity {

    public static Optional<Object> getId(GeneratedKeyHolder holder) {
        List<Map<String, Object>> keys = holder.getKeyList();
        if (keys != null && !keys.isEmpty()) {
            Map<String, Object> generatedKey = keys.get(0);
            return Optional.ofNullable(generatedKey.get("id"));
        }
        return Optional.empty();
    }
}
