package com.huan.myadt.provider;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.huan.myadt.MyApp;
import com.huan.myadt.bean.CGQ_exception.ExceptionMoel;
import com.huan.myadt.bean.CGQ_log.LogCate;
import com.huan.myadt.bean.CGQ_log.LogCate_Tag;
import com.huan.myadt.event.LogCateEvent.LogCateManager;
import com.huan.myadt.test.excel.ExcelPull;

/**
 * logcate内容提供者
 * 
 * @author CGQ
 *
 */
public class ExceptionProvider {
	private static List<ExceptionMoel> exceptions = new ArrayList<ExceptionMoel>();
	static ExceptionProvider logProvider = new ExceptionProvider();
	private static Context mContext;

	public static ExceptionProvider getInstance() {
		
		return logProvider;
	}

	private ExceptionProvider() {
		
		mContext = MyApp.getContext();
		ExcelPull ePull = new ExcelPull<ExceptionMoel>();
		List<String> names = new ArrayList<String>();
		names.add("parentName");
		names.add("simpleName");
		names.add("description");
		names.add("suggestion");
		List<HashMap<String, Object>> maps = ePull.main(mContext,new ExceptionMoel(),names);
		for(Map map : maps){
			//exceptions.add((ExceptionMoel)C.mapToObject(map,ExceptionMoel.class));
			ExceptionMoel exceptionMoel = new ExceptionMoel();
			exceptionMoel.setSimpleName((String)map.get("simpleName"));
			exceptionMoel.setDescription((String)map.get("description"));
			exceptionMoel.setSuggestion((String)map.get("suggestion"));
			exceptionMoel.setParentName((String)map.get("parentName"));
			exceptions.add(exceptionMoel);
		}
	}

	public void init(Context context) {
		mContext = context;
	}

	public static List<ExceptionMoel> getExceptions() {
		return exceptions;
	}
	public ExceptionMoel getExceptionMoelByName(String ename) {
		for (ExceptionMoel app : exceptions) {
			if (ename.trim().equals(app.getSimpleName().toString().trim())) {
				return app;
			}
		}
		return null;
	}


	public void addApp(ExceptionMoel app) {

		LogCateManager.getInstance().onTagsChanged();
	}

	public static class LogList extends ArrayList<LogCate> {

		@Override
		public void add(int index, LogCate element) {
			// TODO Auto-generated method stub
			super.add(index, element);
			LogCateManager.getInstance().onLogsChanged();
		}

		@Override
		public boolean add(LogCate e) {
			// TODO Auto-generated method stub
			super.add(e);
			LogCateManager.getInstance().onLogsChanged();
			return true;
		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub
			super.clear();
			LogCateManager.getInstance().onLogsChanged();
		}

	}

	public interface LEVEL {
		int verbose = 1;
		int debug = 2;
		int info = 3;
		int warn = 4;
		int error = 5;
		int exception = 6;
	}

	public static String getLevel(int i) {
		String str = null;
		switch (i) {

		case 1:
			str = "verbose";
			break;
		case 2:
			str = "debug";
			break;
		case 3:
			str = "info";
			break;
		case 4:
			str = "warn";
			break;
		case 5:
			str = "error";
			break;
		case 6:
			str = "exception";
			break;
		default:
			str = "unknow";
			break;
		}
		return str;
	}

	public static int getLevel(String name) {
		int level = 0;
		switch (name) {
		case "all":
			level = 0;
		case "verbose":
			level = 1;
			break;
		case "debug":
			level = 2;
			break;
		case "info":
			level = 3;
			break;
		case "warn":
			level = 4;
			break;
		case "error":
			level = 5;
			break;
		case "exception":
			level = 6;
			break;
		default:
			level = 0;
			break;
		}
		return level;
	}

}


/** 
 * 使用reflect进行转换 
 */  
class C {  
  
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass){    
        if (map == null)  
            return null;    
  
        Object obj = null;
		try {
			obj = beanClass.newInstance();
  
        Field[] fields = obj.getClass().getDeclaredFields();   
        for (Field field : fields) {    
            int mod = field.getModifiers();    
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){    
                continue;    
            }    
  
            field.setAccessible(true);    
            field.set(obj, map.get(field.getName()));   
        }   
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return obj;    
    }    
  
    public static Map<String, Object> objectToMap(Object obj) throws Exception {    
        if(obj == null){    
            return null;    
        }   
  
        Map<String, Object> map = new HashMap<String, Object>();    
  
        Field[] declaredFields = obj.getClass().getDeclaredFields();    
        for (Field field : declaredFields) {    
            field.setAccessible(true);  
            map.put(field.getName(), field.get(obj));  
        }    
  
        return map;  
    }   
    }
