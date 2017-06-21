package com.lzsf.jdbc.jdbcfind;

import com.lzsf.jdbc.jdbcfind.utils.CallProcedure;
import com.lzsf.jdbc.jdbcfind.utils.JdbcHelper;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.R.id.message;

/**
 * This class echoes a string called from JavaScript.
 */
public class jdbcfind extends CordovaPlugin {

  private JdbcHelper jdbcHelper = JdbcHelper.getInstenince();

  @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
      if (action.equals("add")) {
            this.add(args, callbackContext);
            return true;
        }else if (action.equals("delete")) {
            this.delete(args, callbackContext);
            return true;
        }else if (action.equals("update")) {
            this.update(args, callbackContext);
            return true;
        }else if (action.equals("select")) {
            this.select(args, callbackContext);
            return true;
        }
        return false;
    }

    private void add(JSONArray args, CallbackContext callbackContext) throws JSONException {
      String message = args.getString(0);
      if (message != null && message.length() > 0) {
         JSONObject object = args.getJSONObject(0); //再根据key取值
          String call = (String) object.get("call");
          String str = (String) object.get("str");
          String[] strings = str.split("\\，");
          String index = (String) object.get("index");
          CallProcedure.initData(call,strings,index);
          callbackContext.success("添加数据成功");
        } else {
          callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void delete(JSONArray args, CallbackContext callbackContext) throws JSONException {
      String message = args.getString(0);
      if (message != null && message.length() > 0) {
        JSONObject object = args.getJSONObject(0); //再根据key取值
        String key = (String) object.get("key");
        String value = (String) object.get("value");
        String table = (String) object.get("table");
        String sql = (String) object.get("sql");
        jdbcHelper.deleteData(table,key,value);
        callbackContext.success(message);
      } else {
        callbackContext.error("Expected one non-empty string argument.");
      }
    }

    private void update(JSONArray args, CallbackContext callbackContext) throws JSONException {
      String message = args.getString(0);
      if (message != null && message.length() > 0) {
        JSONObject object = args.getJSONObject(0); //再根据key取值
        String key = (String) object.get("key");
        String value = (String) object.get("value");
        String table = (String) object.get("table");
        String sql = (String) object.get("sql");
        jdbcHelper.updateMessage(key,table,value,sql);
        callbackContext.success(message);
      } else {
        callbackContext.error("Expected one non-empty string argument.");
      }
    }

    private void select(JSONArray args, CallbackContext callbackContext) throws JSONException {
      String message = args.getString(0);
      if (message != null && message.length() > 0) {
//        JSONObject jsonArgs = new JSONObject(args.getString(0));
        JSONObject object = args.getJSONObject(0); //再根据key取值
        String key = (String) object.get("key");
//        String value = (String) object.get("value");
        String table = (String) object.get("table");
//        String sql = (String) object.get("sql");
        String selectData = jdbcHelper.selectData(key, table);
        callbackContext.success(selectData);
      } else {
        callbackContext.error("Expected one non-empty string argument.");
      }
    }
}
