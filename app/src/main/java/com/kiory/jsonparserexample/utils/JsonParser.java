package com.kiory.jsonparserexample.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static Object parse(Object data, String json){
        Class<?> cls = data.getClass();
        Field[] fields = cls.getDeclaredFields();

        Class<?> integerType = Integer.TYPE;
        Class<?> integerClass = Integer.class;
        Class<?> longType = Long.TYPE;
        Class<?> floatType = Float.class;
        Class<?> stringType = java.lang.String.class;
        Class<?> listType = java.util.List.class;

        try {
            JSONObject jsonObject = new JSONObject(json);

            for(Field f : fields){
                f.setAccessible(true);
                Annotation annotation = f.getAnnotation(AlternativeName.class);
                String jsonFieldName = f.getName();

                if(annotation instanceof AlternativeName){
                    AlternativeName myAnnotation = (AlternativeName) annotation;
                    jsonFieldName = myAnnotation.value();
                }

                if( (f.getType().isAssignableFrom(integerType)) || (f.getType().isAssignableFrom(integerClass)) ){
                    try {
                        f.set(data, jsonObject.getInt(jsonFieldName));
                    } catch (IllegalAccessException e) {
                        Log.e("JsonParser", "Unable to write to integer attribute");
                    }
                }
                if(f.getType().isAssignableFrom(longType)){
                    try {
                        f.set(data, jsonObject.getLong(jsonFieldName));
                    } catch (IllegalAccessException e) {
                        Log.e("JsonParser", "Unable to write to long attribute");
                    }
                }
                else if(f.getType().isAssignableFrom(floatType)){
                    try {
                        f.set(data, jsonObject.getDouble(jsonFieldName));
                    } catch (IllegalAccessException e) {
                        Log.e("JsonParser", "Unable to write to float attribute");
                    }
                }
                else if(f.getType().isAssignableFrom(stringType)){
                    try {
                        f.set(data, jsonObject.getString(jsonFieldName));
                    } catch (IllegalAccessException e) {
                        Log.e("JsonParser", "Unable to write to string attribute");
                    }
                }
                else if( f.getType().isAssignableFrom(listType) ){
                    try{
                        JSONArray jsonArray = jsonObject.getJSONArray(jsonFieldName);

                        Type type = f.getGenericType();
                        String genericType = null;
                        if (type instanceof ParameterizedType) {
                            ParameterizedType pt = (ParameterizedType) type;
                            for (Type t : pt.getActualTypeArguments()) {
                                genericType = t+"";
                            }
                        }
                        genericType = genericType.replace("class ", "");

                        List objectList = new ArrayList();


                        for(int i=0; i<jsonArray.length(); i++){
                            String subJson = jsonArray.getJSONObject(i).toString();
                            Object objectToAdd = instantiate(genericType);
                            //Object objectToAdd = instantiate(genericType);
                            objectList.add(parse(objectToAdd, subJson));
                        }
                        f.set(data, objectList);
                    }catch(JSONException e){
                        Log.e("JsonParser", "Error while reading Array json");
                    }catch (Exception e) {
                        e.printStackTrace();
                        Log.e("JsonParser", "Error creating class");
                    }
                }
                else{
                    try{
                        Log.e("JsonParser", "Trying to read Object "+jsonFieldName);
                        JSONObject jsonSubObject = jsonObject.getJSONObject(jsonFieldName);
                        f.set(data, parse(instantiate(f.getType().getName()), jsonSubObject.toString()));
                    }catch(JSONException ex){
                        ex.printStackTrace();
                        Log.e("JsonParser", "Error while reading json Object");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("JsonParser", "Unable to instantiate object class "+f.getType().getName());
                    }

                }

                f.setAccessible(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

    static Object instantiate(String className) throws Exception {
        Class<?> myClass = Class.forName(className);

        for (Constructor<?> ctor : myClass.getConstructors()) {
            return ctor.newInstance();
        }

        throw new IllegalArgumentException("Error while instantiating " + className);
    }

}
