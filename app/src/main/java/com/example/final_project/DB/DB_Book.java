package com.example.final_project.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.final_project.Classes.Book;
import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.ArrayList;

public class DB_Book extends SQLiteOpenHelper {
    //initialisation
    private static final int DATABASE_VERSION = 2;
    public static final String BOOKS = "BOOKS";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_SUBTITLE = "subtitle";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_AUTHORS = "authors";
    public static final String COLUMN_PUBLISHER = "publisher";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_PAGES = "pages";
    public static final String COLUMN_RATING = "rating";
    public static final boolean COLUMN_FAVORITE = false;


    public DB_Book(@Nullable Context context) {
        super(context, "BOOKS.db", null, DATABASE_VERSION);
    }

    //the first time on created
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + BOOKS + " (" + COLUMN_ID + " TEXT PRIMARY KEY, " + COLUMN_TITLE + " TEXT, " + COLUMN_SUBTITLE + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_IMAGE + " TEXT, " + COLUMN_RATING + " TEXT, " + COLUMN_YEAR + " TEXT, " + COLUMN_AUTHORS + " TEXT, " + COLUMN_PUBLISHER + " TEXT, " + COLUMN_PAGES + " TEXT); ";
        db.execSQL(query);
    }


    //function to add to the db
    public void addFavorite(String Title, String Subtitle, String Description, String image, String authors, String publisher, String year, String pages, String rating ) {

        //calling a writeable db
        SQLiteDatabase db = this.getWritableDatabase();

        //variable for content values
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(COLUMN_TITLE, Title);
        values.put(COLUMN_SUBTITLE, Subtitle);
        values.put(COLUMN_DESCRIPTION, Description);
        values.put(COLUMN_IMAGE, image);
        values.put(COLUMN_AUTHORS, authors);
        values.put(COLUMN_PAGES, publisher);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_PAGES, pages);
        values.put(COLUMN_RATING, rating);

        //after adding the values we are inserting them in our table in db
        db.insert(BOOKS, null, values);

        //closing the database
        db.close();

    }
    ///method to read all the books
    public ArrayList<Book> readBooks(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor_fav = db.rawQuery("SELECT DISTINCT * FROM " + BOOKS, null);

        ArrayList<Book> book = new ArrayList<>();

        //moving the cursor
        if (cursor_fav.moveToFirst()){
            //adding the data from cursor to arraylist
            do{
               //book.add(new Book());
                book.add(new Book(cursor_fav.getString(1) , cursor_fav.getString(2) , cursor_fav.getString(3) ,cursor_fav.getString(4) ,cursor_fav.getString(5) ,cursor_fav.getString(6) ,cursor_fav.getString(7) ,cursor_fav.getString(8) ,cursor_fav.getString(9) ));
            }while(cursor_fav.moveToNext());
        }
        //closing the cursor
        cursor_fav.close();
        return book;
    }
        //if version changes to modify the db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + BOOKS);
        //db.execSQL("ALTER TABLE " + BOOKS + " DROP COLUMN " + COLUMN_TITLE + "," + COLUMN_IMAGE);
        onCreate(db);
    }

    public void clearAll(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor_fav = db.rawQuery("SELECT DISTINCT * FROM " + BOOKS, null);
        String delete_query = "ALTER TABLE  " + BOOKS +  " DROP COLUMN  " + COLUMN_ID + " , " + COLUMN_TITLE + " TEXT, " + COLUMN_SUBTITLE + " TEXT, " + COLUMN_DESCRIPTION  + COLUMN_IMAGE + " TEXT, " + COLUMN_RATING + " TEXT, " + COLUMN_YEAR + " TEXT, " + COLUMN_AUTHORS + " TEXT, " + COLUMN_PUBLISHER + " TEXT, " + COLUMN_PAGES + " TEXT); ";
        //moving the cursor
        if (cursor_fav.moveToFirst()){
            //adding the data from cursor to arraylist
            do{
                db.execSQL(delete_query);
            }while(cursor_fav.moveToNext());
        }
        //closing the cursor
        cursor_fav.close();

    }

}
