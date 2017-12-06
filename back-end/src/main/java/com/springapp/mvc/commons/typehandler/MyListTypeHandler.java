package com.springapp.mvc.commons.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author
 * @Description:
 * @date : 2017/12/2
 **/
@MappedTypes(List.class)
@MappedJdbcTypes({JdbcType.VARCHAR})
public class MyListTypeHandler extends BaseTypeHandler<List<Integer>>{
    public void setNonNullParameter(PreparedStatement var1, int var2, List<Integer> var3, JdbcType var4) throws SQLException{
        if(var3.size() == 0) {
            var1.setString(var2, null);
            return;
        }
        //List集合转字符串
        StringBuffer sb = new StringBuffer();
        for(Integer integer : var3){
            sb.append(String.valueOf(integer)).append(",");
        }
        var1.setString(var2, sb.toString().substring(0, sb.toString().length() - 1));
    }

    public  List<Integer> getNullableResult(ResultSet var1, String var2) throws SQLException{
        if(var1 == null || var2 == null || var1.getString(var2) == null || var2.equals("") )
            return null;
        String[] split = var1.getString(var2).split(",");
        List<Integer> result = new ArrayList<Integer>();
        for(String string : split){
            if(string.equals("")) continue;
            result.add(Integer.valueOf(string));
        }
        return result;
    }

    public  List<Integer> getNullableResult(ResultSet var1, int var2) throws SQLException{
        if(var1 == null || var1.getString(var2) == null) return null;
        String[] split = var1.getString(var2).split(",");
        List<Integer> result = new ArrayList<Integer>();
        for(String string : split){
            if(string.equals("")) continue;
            result.add(Integer.valueOf(string));
        }
        return result;
    }
    public List<Integer> getNullableResult(CallableStatement var1, int var2) throws SQLException{
        if(var1 == null || var1.getString(var2) == null) return null;
        String[] split = var1.getString(var2).split(",");
        List<Integer> result = new ArrayList<Integer>();
        for(String string : split){
            if(string.equals("")) continue;
            result.add(Integer.valueOf(string));
        }
        return result;
    }

}
