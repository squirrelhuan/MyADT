package com.huan.myadt.test.excel;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

//import org.junit.Test;

public class test {

	public static void main(String[] args) {
		
	}
	/*@Test
	public void sss() throws Exception{
		ExcelPull ePull = new ExcelPull<ExceptionMoel>();
		List<HashMap<String, Object>> maps = ePull.main(new ExceptionMoel());
		System.out.println(maps.size());
		List<ExceptionMoel> exceptionMoels = new ArrayList<ExceptionMoel>();
		for(Map map : maps){
			exceptionMoels.add((ExceptionMoel)C.mapToObject(map,ExceptionMoel.class));
		}

		System.out.println(exceptionMoels.get(1).getName(exceptionMoels));
	}*/
}

/** 
 * 使用reflect进行转换 
 */  
class C {  
  
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)  
            return null;    
  
        Object obj = beanClass.newInstance();  
  
        Field[] fields = obj.getClass().getDeclaredFields();   
        for (Field field : fields) {    
            int mod = field.getModifiers();    
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){    
                continue;    
            }    
  
            field.setAccessible(true);    
            field.set(obj, map.get(field.getName()));   
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