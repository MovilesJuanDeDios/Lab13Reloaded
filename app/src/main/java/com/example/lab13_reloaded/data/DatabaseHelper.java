package com.example.lab13_reloaded.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lab13_reloaded.data.dao.CursoDAO;
import com.example.lab13_reloaded.data.dao.CursoEstudianteDAO;
import com.example.lab13_reloaded.data.dao.EstudianteDAO;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Lab13.db";

    public DatabaseHelper() {
        super(App.getContext(), DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CursoDAO.createTable());
        db.execSQL(EstudianteDAO.createTable());
        db.execSQL(CursoEstudianteDAO.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + EstudianteDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CursoDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CursoEstudianteDAO.TABLE_NAME);
        onCreate(db);
    }
}