package org.linlinjava.litemall.db.mybatis;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/*
  <columnOverride column="urls" javaType="java.lang.String[]" typeHandler="JsonStringArrayTypeHandler"/>
*/
public class JsonStringArrayTypeHandler extends BaseTypeHandler<String[]> {
  private static final ObjectMapper mapper = new ObjectMapper();

  @Override
  public void setNonNullParameter(
      PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
    ps.setString(i, toJson(parameter));
  }

  private String toJson(String[] params) {
    try {
      return mapper.writeValueAsString(params);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "[]";
  }

  @Override
  public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return this.toObject(rs.getString(columnName));
  }

  private String[] toObject(String content) {
    if (content != null && !content.isEmpty()) {
      try {
        return (String[]) mapper.readValue(content, String[].class);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    } else {
      return null;
    }
  }

  @Override
  public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return this.toObject(rs.getString(columnIndex));
  }

  @Override
  public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return this.toObject(cs.getString(columnIndex));
  }
}
