package com.example.final_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.final_project.Adapters.BookDetailAdapter;
import com.example.final_project.DB.*;
import com.example.final_project.Classes.Book;
import com.example.final_project.ForVolleyCall.CustomRequest;
import com.example.final_project.ForVolleyCall.Singleton_Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookDetails extends AppCompatActivity {

    //for bottom navigation
    BottomNavigationView bottomNavigationView ;
    //for favorite
    boolean favorite;
    //url for detailed books
    String url_book_info = "https://api.itbook.store/1.0/books/";
    //initialising  variables
    ArrayList<Book> books;
    ArrayList<Book> cursor_books;
    Book book;
    BookDetailAdapter detail_adapter = null;
    private DB_Book dbHandler;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("============ CHECKING =============" , "bookdetailes class");
        setContentView(R.layout.book_details_layout);

        //setting it as default selected
        BottomNavigationView  bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home_icon);
        //to change on click
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case  R.id.home_icon:
                        return true;
                    case R.id.favorite_icon:
                        startActivity(new Intent(getApplicationContext() , FavoritePage.class));
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

        //creating a dbHandler for our database to pass parameters with
        dbHandler = new DB_Book(BookDetails.this);
        //dbHandler.clearDatabase(DB_Book.BOOKS);
        //getting the btn_favorite from book details xml file for the onClickListener
        RelativeLayout mainLayout =(RelativeLayout)findViewById(R.id.activity_details_main_layout) ;
        LayoutInflater inflater = getLayoutInflater();
        View myLayout = inflater.inflate(R.layout.book_details , mainLayout , false);
        ImageView image_remove = (ImageView) myLayout.findViewById(R.id.heart_add);
        ImageView image_add = (ImageView) myLayout.findViewById(R.id.heart_remove);
        mainLayout.addView(myLayout);

        //filling the info in layout
        ListView book_list_details = (ListView) findViewById(R.id.bookList_details);
        books = new ArrayList<>();
        Log.d("DETAIL PAGES", "===========");
        fetchDataByISBN();
        detail_adapter = new BookDetailAdapter(getApplicationContext(), books);
        book_list_details.setAdapter(detail_adapter);

        //reading db to find favorite status
        dbHandler = new DB_Book(BookDetails.this);
        cursor_books = new ArrayList<>();
        cursor_books = dbHandler.readBooks();


        //adds or removes the book from the favorite list (similar to like in instagram)
        image_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (image_add.getVisibility() == View.VISIBLE) {
                    image_add.setVisibility(View.INVISIBLE);
                    addOne(book);
                    image_remove.setVisibility(View.VISIBLE);
                    favorite = true;
                    Toast.makeText(BookDetails.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //remove from DB
        image_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_remove.setVisibility(View.INVISIBLE);
                image_add.setVisibility(View.VISIBLE);
                removeFromFav();
                favorite = false;
                Toast.makeText(BookDetails.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //adding book to list
    public void addOne(Book book) {

        Log.d("ADD ONE FUNCTION" ,"Entered the functions");
        dbHandler.getWritableDatabase();
        dbHandler.addFavorite(book.getTitle() , book.getSubtitles() , book.getDescription() , book.getImage() , book.getAuthor() ,book.getPublisher() , book.getYear() , book.getPages() , book.getRating() );
        Toast.makeText(BookDetails.this, "Book has been added.", Toast.LENGTH_SHORT).show();
    }

    //removing the book from favorite list using the id
    private void removeFromFav() {

        SQLiteDatabase db = new DB_Book(this).getReadableDatabase();
        String delete_query = "DELETE FROM " + DB_Book.BOOKS + " WHERE " + DB_Book.COLUMN_ID + " = '" + book.getId() + "'";
        db.execSQL(delete_query);
        Toast.makeText(BookDetails.this, "Book has been removed.", Toast.LENGTH_SHORT).show();
    }

    //fetching the detailed information for specific book
    @SuppressLint("LongLogTag")
    public void fetchDataByISBN() {
        //book ISBN
        SharedPreferences sh = getSharedPreferences("Prefs_ISBN", MODE_PRIVATE);
        String book_id = sh.getString("isbn", null);
        //Log.d("---------- in book detail fetch data isbn is", book_id);
        //url
        String url = url_book_info + book_id;

        //custom request allow us to use string in listener instead of JsonArray
        CustomRequest request = new CustomRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(String response) {
                try {
                    //using json attributes to access the data
                    JSONObject object = new JSONObject(response);


                    //setting the details to the book
                    book = new Book();
                    book.setTitle(object.getString("title"));
                    book.setId(object.getString("isbn13"));
                    book.setSubtitles(object.getString("subtitle"));
                    book.setPrice(object.getString("price"));
                    book.setImage(object.getString("image"));
                    book.setAuthor(object.getString("authors"));
                    book.setPublisher(object.getString("publisher"));
                    book.setLanguage(object.getString("language"));
                    book.setYear(object.getString("year"));
                    book.setPages(object.getString("pages"));
                    book.setRating(object.getString("rating"));
                    book.setDescription(object.getString("desc"));
                    //book.setUrl_pdf(object.getString("pdf"));

                    books.add(book);
                    detail_adapter.notifyDataSetChanged();



                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        try {

            //to get the queue
            Singleton_Volley singleton = Singleton_Volley.getInstance(this.getApplicationContext());
            RequestQueue queue = singleton.getRequestQueue();
            queue.add(request);

        } catch (Exception e) {
            Log.e("Volley", e.getMessage());
        }
    }

}
