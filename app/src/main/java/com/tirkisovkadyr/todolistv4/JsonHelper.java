package com.tirkisovkadyr.todolistv4;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonHelper {
    private static final String FILE_NAME = "database.json";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    public static void init() {
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    }


    public static boolean exportToJson(Context context, List<Todo> dataList) {
        String jsonString = "";

        DataItems dataItems = new DataItems();
        dataItems.setTodos(dataList);
        try {
            jsonString = objectWriter.writeValueAsString(dataItems);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try (FileOutputStream fileOutputStream =
                context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static List<Todo> importFromJson(Context context) {
        try (FileInputStream fileInputStream =
                context.openFileInput(FILE_NAME)) {

            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            DataItems dataItems = objectMapper.readValue(inputStreamReader, new TypeReference<DataItems>(){});
            return dataItems.getTodos();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String getJustString(List<Todo> dataList) {
        String jsonString = "";

        DataItems dataItems = new DataItems();
        dataItems.setTodos(dataList);
        try {
            jsonString = objectWriter.writeValueAsString(dataItems);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        return jsonString;
    }

    private static class DataItems {
        private List<Todo> todos;

        List<Todo> getTodos() { return todos; }
        void setTodos(List<Todo> todos) { this.todos = todos; }
    }
}
