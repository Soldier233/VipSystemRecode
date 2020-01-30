package me.zhanshi123.vipsystem;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.zhanshi123.vipsystem.custom.CustomArg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTest {
    public static void main(String[] args) {
        List<CustomArg> argList = new ArrayList<>();
        argList.add(new CustomArg("arg1", "value1"));
        argList.add(new CustomArg("arg2", "value2"));
        Map<String, String> tmp = new HashMap<>();
        argList.forEach(customArg -> tmp.put(customArg.getName(), customArg.getValue()));
        Gson gson = new Gson();

        JsonObject result = new JsonObject();
        result.add("must", gson.toJsonTree(tmp));
        result.add("custom", gson.toJsonTree(""));
        System.out.println(result);
    }
}
