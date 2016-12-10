package Model;

import org.json.simple.JSONObject;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *转换器
 *1:将JavaBean 转换成Map、JSONObject
 *2:将JSONObject 转换成Map
 *
 * @author xxx
 */
public class BeanConverter {
    /**
     * 将javaBean转换成Map
     *
     * @param javaBean javaBean
     * @return Map对象
     */
    public static Map<String, Object> toMap(Object javaBean)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        Method[] methods = javaBean.getClass().getDeclaredMethods();

        for (Method method : methods) {
            try {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);

                    Object value = method.invoke(javaBean, (Object[]) null);
                    if(     value.getClass()==Integer.class||
                            value.getClass()==Double.class ||
                            value.getClass()==Float.class ||
                            value.getClass()==Boolean.class ||
                            value.getClass()==boolean.class ||
                            value.getClass()==int.class ||
                            value.getClass()==float.class ||
                            value.getClass()==double.class){
                        result.put(field, null == value ? "" : value);
                    }
                    else{
                        result.put(field, null == value ? "" : value.toString());
                    }
                }
            } catch (Exception e) {
            }
        }

        return result;
    }

    /**
     * 将json对象转换成Map
     *
     * @param jsonObject json对象
     * @return Map对象
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> toMap(JSONObject jsonObject)
    {
        Map<String, String> result = new HashMap<String, String>();
        Iterator<String> iterator = jsonObject.keySet().iterator();
        String key = null;
        String value = null;
        while (iterator.hasNext()) {
            key = iterator.next();
            value = (String)jsonObject.get(key.toString());
            result.put(key, value);
        }
        return result;
    }

    /**
     * 将javaBean转换成JSONObject
     *
     * @param bean javaBean
     * @return json对象
     */
    public static JSONObject toJSON(Object bean)
    {
        return new JSONObject(toMap(bean));
    }

    /**
     * 将map转换成Javabean
     *
     * @param javabean javaBean
     * @param data     map数据
     */
    public static Object toJavaBean(Object javabean, Map<String, String> data)
    {
        Method[] methods = javabean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            try {
                if (method.getName().startsWith("set")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("set") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    method.invoke(javabean, new Object[]
                            {
                                    data.get(field)
                            });
                }
            } catch (Exception e) {
            }
        }

        return javabean;
    }

}