package fr.ideria.nooblib.data.json;

import fr.ideria.nooblib.Nooblib;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Json {
    private static final String splitter = "/";
    private final File file;
    private JSONObject json;

    public Json(){
        this.file = null;
        this.json = new JSONObject();
    }

    public Json(String path, boolean inProject){ this(new File(inProject ? (System.getProperty("user.dir") + "/src/main/resources/" + path) : path)); }
    public Json(File file){
        this.file = file;

        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            this.json = (JSONObject) new JSONParser().parse(reader);
        }catch(IOException | ParseException e){
            e.printStackTrace();
        }
    }

    public Json(String text){
        this.file = null;

        try{
            this.json = (JSONObject) new JSONParser().parse(text);
        }catch(ParseException e){
            e.printStackTrace();
        }
    }

    public String getString(String key){
        key = key.replace(".", splitter);

        if(!key.contains(splitter)){
            Object value = json.get(key);
            return value == null ? "undefined" : value.toString();
        }

        String[] keys = key.split(splitter);
        JSONObject value = (JSONObject) json.get(keys[0]);

        for(int i = 1; i < keys.length - 1; i++) value = (JSONObject) value.get(keys[i]);

        return value == null ? "undefined" : value.get(Nooblib.lastOf(keys)).toString();
    }

    public int getInteger(String key){ return Integer.parseInt(getString(key)); }
    public long getLong(String key){ return Long.parseLong(getString(key)); }
    public float getFloat(String key){ return Float.parseFloat(getString(key)); }
    public double getDouble(String key){ return Double.parseDouble(getString(key)); }
    public boolean getBoolean(String key){ return Boolean.parseBoolean(getString(key)); }
    public Json getJson(String key){ return new Json(getObject(key).toJSONString()); }
    public JSONObject getObject(String key){
        key = key.replace(".", splitter);

        if(!key.contains(splitter))
            return (JSONObject) json.get(key);

        String[] keys = key.split(splitter);
        JSONObject value = (JSONObject) json.get(keys[0]);

        for(int i = 1; i < keys.length - 1; i++)
            value = (JSONObject) value.get(keys[i]);

        System.out.println(value.toJSONString());

        return (JSONObject) value.get(Nooblib.lastOf(keys));
    }

    public int[] getIntegerArray(String key){
        key = key.replace(".", splitter);

        if(!key.contains(splitter)) return Nooblib.integerArrayFromString(json.get(key).toString());

        String[] keys = key.split(splitter);
        JSONObject value = (JSONObject) json.get(keys[0]);

        for(int i = 1; i < keys.length - 1; i++) value = (JSONObject) value.get(keys[i]);

        return Nooblib.integerArrayFromString(value.get(Nooblib.lastOf(keys)).toString());
    }

    public float[] getFloatArray(String key){
        key = key.replace(".", splitter);

        if(!key.contains(splitter)) return Nooblib.floatArrayFromString(json.get(key).toString());

        String[] keys = key.split(splitter);
        JSONObject value = (JSONObject) json.get(keys[0]);

        for(int i = 1; i < keys.length - 1; i++) value = (JSONObject) value.get(keys[i]);

        return Nooblib.floatArrayFromString(value.get(Nooblib.lastOf(keys)).toString());
    }

    public String[] getStringArray(String key){
        JSONArray jsonArray = getArray(key);
        List<String> array = new ArrayList<>();

        jsonArray.forEach(object -> array.add(object.toString()));

        return array.toArray(String[]::new);
    }

    public JSONArray getArray(String key){
        key = key.replace(".", splitter);

        if(!key.contains(splitter)) return (JSONArray) json.get(key);

        String[] keys = key.split(splitter);
        JSONObject value = (JSONObject) json.get(keys[0]);

        for(int i = 1; i < keys.length - 1; i++) value = (JSONObject) value.get(keys[i]);

        return (JSONArray) value.get(Nooblib.lastOf(keys));
    }

    public void put(String key, Object value){
        json.put(key, value);

        if(file != null){
            try(FileWriter write = new FileWriter(file); BufferedWriter writer = new BufferedWriter(write)){
                writer.write(json.toJSONString());
                writer.flush();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return json.toJSONString();
    }

    public JSONObject toObject(){
        try {
            return (JSONObject) new JSONParser().parse(json.toJSONString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}