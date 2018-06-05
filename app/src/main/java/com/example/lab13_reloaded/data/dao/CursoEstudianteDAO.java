package com.example.lab13_reloaded.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab13_reloaded.data.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class CursoEstudianteDAO {

    public static final String TABLE_NAME = "CursoEstudiante";

    public static final String ID_ESTUDIANTE = "ID_ESTUDIANTE";
    public static final String ID_CURSO = "ID_CURSO";

    public CursoEstudianteDAO() {
    }

    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                ID_ESTUDIANTE + " TEXT, " +
                ID_CURSO + " TEXT, " +
                "PRIMARY KEY(" + ID_ESTUDIANTE + "," + ID_CURSO + "), " +
                "FOREIGN KEY(" + ID_ESTUDIANTE + ") REFERENCES Estudiantes(ID)" +
                "FOREIGN KEY(" + ID_CURSO + ") REFERENCES Cursos(ID)" +
                ")";
    }

    public long insert(String ide, String idc) {
        long result;

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_ESTUDIANTE, ide);
        values.put(ID_CURSO, idc);

        result =  db.insert(TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return result;
    }

    public boolean delete(String ide, String idc) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        boolean result = db.delete(TABLE_NAME, ID_ESTUDIANTE + "=? " +
                "and " +  ID_CURSO + "=?", new String[]{ide + "", idc + ""}) > 0;

        DatabaseManager.getInstance().closeDatabase();
        return result;
    }

    public List<String> selectAll(String idEstudiante) {
        List<String> eList = new ArrayList<String>();
        Cursor cursor = null;
        String query = "SELECT " + ID_CURSO + " FROM " + TABLE_NAME + " WHERE " +
                ID_ESTUDIANTE + "=?";

        try {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            cursor = db.rawQuery(query, new String[]{idEstudiante + ""});
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    eList.add(cursor.getString(0));
                }
            }
        } finally {
            if (cursor != null)
                cursor.close();

            DatabaseManager.getInstance().closeDatabase();
        }

        return eList;
    }
}
