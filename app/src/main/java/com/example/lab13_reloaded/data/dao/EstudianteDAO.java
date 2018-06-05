package com.example.lab13_reloaded.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab13_reloaded.data.DatabaseManager;
import com.example.lab13_reloaded.model.Estudiante;

import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {

    public static final String TABLE_NAME = "Estudiantes";

    public static final String ID = "ID";
    public static final String NOMBRE = "NOMBRE";
    public static final String APELLIDOS = "APELLIDOS";
    public static final String EDAD = "EDAD";

    public EstudianteDAO() {
    }

    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " TEXT PRIMARY KEY, " +
                NOMBRE + " TEXT, " +
                APELLIDOS + " TEXT, " +
                EDAD + " INTEGER)";
    }

    public long insert(Estudiante estudiante) {
        long result;

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, estudiante.getId());
        values.put(NOMBRE, estudiante.getNombre());
        values.put(APELLIDOS, estudiante.getApellidos());
        values.put(EDAD, estudiante.getEdad());

        result =  db.insert(TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return result;
    }

    public Estudiante retrieve(String id) {
        Estudiante estudiante = new Estudiante();
        Cursor cursor = null;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + "=?";

        try {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            cursor = db.rawQuery(query, new String[]{id + ""});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                estudiante.setId(cursor.getString(0));
                estudiante.setNombre(cursor.getString(1));
                estudiante.setApellidos(cursor.getString(2));
                estudiante.setEdad(cursor.getInt(3));
            }
        } finally {
            if (cursor != null)
                cursor.close();

            DatabaseManager.getInstance().closeDatabase();
        }

        return estudiante;
    }

    public int update(Estudiante estudiante) {
        int rows;

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, estudiante.getId());
        values.put(NOMBRE, estudiante.getNombre());
        values.put(APELLIDOS, estudiante.getApellidos());
        values.put(EDAD, estudiante.getEdad());

        rows = db.update(TABLE_NAME, values,ID + "=?",
                new String[]{estudiante.getId() + ""});
        DatabaseManager.getInstance().closeDatabase();

        return rows;
    }

    public boolean delete(String id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        boolean result = db.delete(TABLE_NAME, ID + "=?", new String[]{id + ""}) > 0;

        DatabaseManager.getInstance().closeDatabase();
        return result;
    }

    public List<Estudiante> selectAll() {
        List<Estudiante> eList = new ArrayList<>();
        Estudiante estudiante = null;
        Cursor cursor = null;
        String query = "SELECT * FROM " + TABLE_NAME;

        try {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            cursor = db.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    estudiante = new Estudiante();
                    estudiante.setId(cursor.getString(0));
                    estudiante.setNombre(cursor.getString(1));
                    estudiante.setApellidos(cursor.getString(2));
                    estudiante.setEdad(cursor.getInt(3));
                    eList.add(estudiante);
                }
            }
        } finally {
            if (cursor != null)
                cursor.close();

            DatabaseManager.getInstance().closeDatabase();
        }

        return eList;
    }

//    public void delete() {
//        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
//        db.delete(TABLE_NAME, null, null);
//        DatabaseManager.getInstance().closeDatabase();
//    }
}
