package org.eclms.socket.serialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import java.io.IOException;

public class JsonSerialization implements RpcSerialization {

    public <T> byte[] serialize(T obj) throws IOException {
        try {
            //Convert object to JsonString using FastJson
            String jsonString = JSON.toJSONString(obj);
            //Convert JsonString to byte[]
            return jsonString.getBytes("UTF-8");
        } catch (JSONException e) {
            throw new IOException("Error serializing object to JSON", e);
        }
    }

    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        try {
            //Convert byte[] to JsonString
            String jsonString = new String(data, "UTF-8");
            //Convert JsonString to object using Fastjson
            return JSON.parseObject(jsonString, clz);
        } catch (JSONException e) {
            throw new IOException("Error deserializing JSON to object", e);
        }

    }
}
