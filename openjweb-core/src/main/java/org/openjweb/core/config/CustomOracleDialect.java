package org.openjweb.core.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;
public class CustomOracleDialect implements IDialect {

    @Override
    public DialectModel buildPaginationSql(String originalSql, long offset, long limit) {
        String paginationSql = String.format(
                "SELECT * FROM (SELECT TMP_PAGE.*, ROWNUM MP_ROW_NUM FROM (%s) TMP_PAGE WHERE ROWNUM <= %d) WHERE MP_ROW_NUM > %d",
                originalSql,
                offset + limit,
                offset
        );
        return new DialectModel(paginationSql); // 返回 DialectModel 而非 String
    }

}