package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.Adapters.FavoriteAdapter;
import com.example.final_project.Classes.Book;
import com.example.final_project.DB.DB_Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class FavoritePage extends AppCompatActivity {

    ArrayList<Book> books;
    FavoriteAdapter fav_adapter;

    //for the if empty show an image
    boolean empty_list;
    DB_Book dbHandler;
    ArrayList<Book> cursor_books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_page);

        //getting the btn clear id
        Button button_clear = (Button) findViewById(R.id.btnClear) ;


        //setting it as default selected
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.favorite_icon);
        //to change on click
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.favorite_icon:
                        return true;
                    case  R.id.home_icon:
                        startActivity(new Intent(getApplicationContext() , MainActivity.class));
                        overridePendingTransition(0 , 0);
                        return true;
                    case R.id.search_icon:
                        startActivity(new Intent(getApplicationContext() , SearchPage.class));
                        overridePendingTransition(0 , 0);
                        return true;
                }
                return false;
            }
        });

        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view ) {
//                SQLiteDatabase db = new DB_Book(FavoritePage.this).getReadableDatabase();
//                String drop_query = "DELETE FROM  " + DB_Book.BOOKS + "WHERE"+ DB_Book.COLUMN_TITLE + " = " + c;
//                db.execSQL(drop_query);
//                fav_adapter.notifyDataSetChanged();

            }
        });

        //reading db to find favorite status
        dbHandler = new DB_Book(FavoritePage.this);
        cursor_books = new ArrayList<>();
        cursor_books = dbHandler.readBooks();

        if(empty_list){
            //sets imageview to display no favorites yet
            ImageView background = (ImageView) findViewById(R.id.list_empty);
            background.setVisibility(View.VISIBLE);
            background.setImageResource(R.drawable.no_favorites);
        }

        Log.d("IN FAVORITE " , String.valueOf(cursor_books));
        //filling the list depending on the new books info
        GridView book_list = (GridView) findViewById(R.id.favorite_book);
        fav_adapter = new FavoriteAdapter(getApplicationContext(), cursor_books);
        book_list.setAdapter(fav_adapter);

        book_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //with intent on click redirects
                Intent myIntent = new Intent(FavoritePage.this, BookDetails.class);

                SharedPreferences sh = getSharedPreferences("Prefs_ISBN", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
//                Book book = cursor_books.get(i);
//                //Log.d("THE BOOK  tag =========", book.getId());
//                myEdit.putString("isbn", book.getId());
//                myEdit.apply();

                FavoritePage.this.startActivity(myIntent);
            }
        });
    }


//    //methode to read db and find favorite books
//    @SuppressLint("Range")
//    private void retrieveFavoriteBooks(){
//        SQLiteDatabase db = new DB_Book(this).getReadableDatabase();
//
//        //DB columns needed
//        String[] projection={
//                DB_Book.COLUMN_TITLE,
//                DB_Book.COLUMN_PUBLISHER,
//                DB_Book.COLUMN_IMAGE,
//
//        };
//
//        Cursor cursor = db.query(DB_Book.BOOKS, projection, null, null, null, null, null);
//        //if there is a response
//        if(cursor.getCount()>0) {
//
//            //the list is not empty
//            empty_list = false;
//
//            //go to the first response
//            if (cursor.moveToFirst()) {
//                do {
//                    //setting the book details
//                    book = new Book();
//                    book.setTitle(cursor.getString(cursor.getColumnIndex("title")));
//                    book.setImage(cursor.getString(cursor.getColumnIndex("image")));
//                    book.setPublisher(cursor.getString(cursor.getColumnIndex("publisher")));
//
//
//                    books.add(book);
//                } while (cursor.moveToNext()); //while the cursor is always moving to the next
//
//                adapter.notifyDataSetChanged();
//            }
//            //reading db to find favorite status
//            dbHandler = new DB_Book(FavoritePage.this);
//            cursor_books = new ArrayList<>();
//            int i = 0 ;
//            while(i != books.size()){
//                cursor_books = dbHandler.readBooks(book.getId());
//            }
//            // adapter = new FavoriteAdapter(this , cursor_books);
//
//        } else{
//            //if there is no response then the list is empty
//            empty_list = true;
//        }
//
//    }
}