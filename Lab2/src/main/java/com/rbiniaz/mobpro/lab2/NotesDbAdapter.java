package com.rbiniaz.mobpro.lab2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by rachel on 9/18/13.
 */
public class NotesDbAdapter {

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "notes";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENTS = "contents";

    private final Context context;
    private NotesDbHelper dbHelper;
    private SQLiteDatabase db;

    public NotesDbAdapter(Context context) {
        this.context = context;
    }

    public NotesDbAdapter open(){
        dbHelper = new NotesDbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }


    public Note createNote(String name, String contents) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, name);
        values.put(COLUMN_CONTENTS, contents);
        long id = db.insert(TABLE_NAME, null, values);

        return new Note(id, name, contents);
    }


    public boolean deleteNote(Note note) {
        return db.delete(TABLE_NAME, COLUMN_ID + "=" + note.getId(), null) > 0;
    }

    public Note getNote(long id){
        Cursor cursor = db.query(true, TABLE_NAME, new String[] {COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENTS}, COLUMN_ID + "=" + id, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return noteFromCursor(cursor);

    }

    public Cursor getAllNotes(){
        return db.query(true, TABLE_NAME, new String[] {COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENTS}, null, null, null, null, null, null);
    }

    public static Note noteFromCursor(Cursor cursor){
        return new Note(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CONTENTS)));
    }


    private class NotesDbHelper extends SQLiteOpenHelper {

        private static final String CREATE_DATABASE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_CONTENTS + " TEXT)";

        public NotesDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DATABASE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
