package cn.myapp.util.reflection;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class UtilReflect {
	
    /**
     * 获取属性名数组
     * */
    public String[] getFieldName(Object o){
    	Field[] fields = getFields(o) ;
    	
       	String[] fieldNames = new String[fields.length] ;
    	for(int i = 0; i < fields.length; i++) {
    		fieldNames[i] = fields[i].getName();
    		System.out.println(fields[i].getType()) ;
    	}
    	return fieldNames;
    }
    
    /**
     * 获取属性数组
     * */
    public Field[] getFields(Object o) {
    	Field[] fields = o.getClass().getDeclaredFields() ;//获得类的所有属性    	
    	return fields ;
    }
        
    /**
     * 获取对象的所有属性值，返回一个对象数组
     * */
    public Object[] getFiledValues(Object o){
    	String[] fieldNames=this.getFieldName(o);
    	Object[] value=new Object[fieldNames.length];
    	for(int i=0;i<fieldNames.length;i++){
    		value[i]=this.getFieldValueByName(fieldNames[i], o);
    	}
    	return value;
    }	
	
	/**
	 * 根据属性名 getter
	 * */
    public Object getFieldValueByName(String fieldName, Object o) {
        try {  
        	String firstLetter = fieldName.substring(0, 1).toUpperCase();  
            String getter = "get" + firstLetter + fieldName.substring(1);  
            Method method = o.getClass().getMethod(getter, new Class[] {});  
            Object value = method.invoke(o, new Object[] {});  
            return value;  
        } catch (Exception e) {  
            //log.error(e.getMessage(),e);  
            return null;  
        }  
    } 
    
	/**
	 * 根据属性名 setter
	 * */
    public void setFieldNameValueByName(Field field, Object o, Object... args) {
		try {
			String fieldName = field.getName() ;
			String firstLetter = fieldName.substring(0, 1).toUpperCase();  
            String getter = "set" + firstLetter + fieldName.substring(1);  
            Method method = o.getClass().getMethod(getter,new Class[]{field.getType()});
            method.invoke(o, args);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
    
}
