package com.example.lab13_reloaded.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab13_reloaded.data.DatabaseManager;
import com.example.lab13_reloaded.model.Curso;

import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public static final String TABLE_NAME = "Cursos";

    public static final String ID = "ID";
    public static final String DESCRIPCION = "DESCRIPCION";
    public static final String CREDITOS = "CREDITOS";

    public CursoDAO() {
    }

    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " TEXT PRIMARY KEY, " +
                DESCRIPCION + " TEXT, " +
                CREDITOS + " INTEGER)";
    }

    public long insert(Curso curso) {
        long result;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, curso.getId());
        values.put(DESCRIPCION, curso.getDescripcion());
        values.put(CREDITOS, curso.getCreditos());

        result =  db.insert(TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return result;
    }

    public Curso retrieve(String id) {
        Curso curso = new Curso();
        Cursor cursor = null;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + "=?";

        try {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            cursor = db.rawQuery(query, new String[]{id + ""});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                curso.setId(cursor.getString(0));
                curso.setDescripcion(cursor.getString(1));
                curso.setCreditos(cursor.getInt(2));
            }
        } finally {
            if (cursor != null)
                cursor.close();

            DatabaseManager.getInstance().closeDatabase();
        }

        return curso;
    }

    public int update(Curso curso) {
        int rows;

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, curso.getId());
        values.put(DESCRIPCION, curso.getDescripcion());
        values.put(CREDITOS, curso.getCreditos());

        rows = db.update(TABLE_NAME, values,ID + "=?",
                new String[]{curso.getId() + ""});
        DatabaseManager.getInstance().closeDatabase();

        return rows;
    }

    public boolean delete(String id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        boolean result = db.delete(TABLE_NAME, ID + "=?", new String[]{id + ""}) > 0;

        DatabaseManager.getInstance().closeDatabase();
        return result;
    }

    public List<Curso> selectAll() {
        List<Curso> cList = new ArrayList<>();
        Curso curso = null;
        Cursor cursor = null;
        String query = "SELECT * FROM " + TABLE_NAME;

        try {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            cursor = db.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    curso = new Curso();
                    curso.setId(cursor.getString(0));
                    curso.setDescripcion(cursor.getString(1));
                    curso.setCreditos(cursor.getInt(2));
                    cList.add(curso);
                }
            }
        } finally {
            if (cursor != null)
                cursor.close();

            DatabaseManager.getInstance().closeDatabase();
        }

        return cList;
    }
}
