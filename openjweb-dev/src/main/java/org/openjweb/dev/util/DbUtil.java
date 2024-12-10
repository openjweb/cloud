package org.openjweb.dev.util;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.dev.entity.TableColumnInfo;
import org.springframework.util.StringUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class DbUtil {
    public static String getTableComment(String driverName,String url,String schemaName,String username,String password,String tableName) throws SQLException {
        // 建立数据库连接
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // 获取数据库元数据
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet rs = databaseMetaData.getTables(null, null, tableName, new String[] {"TABLE"});
            if (rs.next()) {
                String tableComment = rs.getString("REMARKS");
                //System.out.println("表注释：：：");
                //System.out.println(tableComment);
                // 处理表注释
                if (StringUtils.isEmpty(tableComment)){
                    String query = "SELECT TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
                    PreparedStatement pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, schemaName);
                    pstmt.setString(2, tableName);
                    ResultSet rs1 = pstmt.executeQuery();
                    if (rs1.next()) {
                        String tableComment1 = rs1.getString("TABLE_COMMENT");
                        return tableComment1;
                        // 处理你的表注释
                    }
                }
                else{
                    return tableComment;
                }
            }
        }
        return "";
    }

    public static List<TableColumnInfo> getTableColumnInfo(String driverName,String url,String schemaName,String username,String password,String tableName) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        // 获取数据库元数据
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        databaseMetaData.getTables(null, schemaName,tableName.toUpperCase(),new String[]{"TABLE"});
        ResultSet rs = databaseMetaData.getColumns(null,schemaName, tableName, "%");
        //获得列数
        int columns = rs.getMetaData().getColumnCount();    // 获得列数
        List<TableColumnInfo> columnInfoList = new ArrayList<>();
        //record_version字段好像没查出来
        while(rs.next()){
            String columnName="";
            String columnType="";
            String comment ="";//字段注释
            columnName = rs.getString("COLUMN_NAME").toLowerCase();
            //log.info("这里得到的字段名:::::");
            //log.info(columnName);
            columnType = rs.getString("TYPE_NAME").toLowerCase();
            int datasize = rs.getInt("COLUMN_SIZE");
            int digits = rs.getInt("DECIMAL_DIGITS");
            int nullable = rs.getInt("NULLABLE");
            comment = rs.getString("REMARKS")==null?"":rs.getString("REMARKS");//注释
            TableColumnInfo colEnt = new TableColumnInfo();
            colEnt.setColumnName(columnName);
            colEnt.setColumnType(columnType);
            colEnt.setColumnDesc(comment);
            colEnt.setDigits(digits);
            colEnt.setDataSize(datasize);
            colEnt.setIsNull(nullable);
            columnInfoList.add(colEnt);
        }
        return columnInfoList;
    }



}
