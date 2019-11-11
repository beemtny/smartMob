package com.example.smartmob;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {

    private static DBManager instance;

    private static final String DB_NAME = "announcementLog";
    private static final int DB_VERSION = 2;
    private static final String TABLE_NAME = "announcement";
    private static final String COL_PIN = "pin";
    private static final String COL_TITLE = "title";
    private static final String COL_MESSAGE = "message";
    private static final String COL_MESSAGE_ID = "messageId";

    private DBManager() {
        super(AppApplication.getInstance(), DB_NAME, null, DB_VERSION);
    }

    static DBManager getInstance(){
        if(instance == null){
            instance = new DBManager();
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_PIN + " TEXT, "
                + COL_TITLE + " TEXT, "
                + COL_MESSAGE + " TEXT, "
                + COL_MESSAGE_ID + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    boolean findMessage(ChatMessage message){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query
                (TABLE_NAME, null,  COL_MESSAGE_ID+" =?", new String[]{message.getId().toString()}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!(cursor != null && cursor.isAfterLast())) {
            String pin = cursor.getString(1);

            if (pin.equals(message.getPin().toString())){
                sqLiteDatabase.close();
                return true;
            }
            cursor.moveToNext();
        }

        sqLiteDatabase.close();
        return false;
    }

    public boolean addMessage(ChatMessage chatMessage){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_PIN, chatMessage.getPin());
        values.put(COL_TITLE, chatMessage.getTitle());
        values.put(COL_MESSAGE, chatMessage.getText());
        values.put(COL_MESSAGE_ID, chatMessage.getId());

        sqLiteDatabase.insert(TABLE_NAME, null, values);
        sqLiteDatabase.close();
        return true;
    }

    public void clearDB(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,null,null);
        sqLiteDatabase.close();
    }

    List<ChatMessage> fetchChatMessageList(){
        List<ChatMessage> messages = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!(cursor != null && cursor.isAfterLast())) {

            ChatMessage chatMessage = new ChatMessage(
                    cursor.getString(4),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );

            messages.add(chatMessage);
            cursor.moveToNext();
        }

        sqLiteDatabase.close();
        return messages;
    }

}