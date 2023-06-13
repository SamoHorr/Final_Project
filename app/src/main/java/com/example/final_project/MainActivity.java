package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.final_project.Adapters.BookDetailAdapter;
import com.example.final_project.Adapters.BookItemAdapter;
import com.example.final_project.Classes.Book;
import com.example.final_project.DB.DB_Book;
import com.example.final_project.ForVolleyCall.CustomRequest;
import com.example.final_project.ForVolleyCall.Singleton_Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //url for new books
    String url_books = "https://api.itbook.store/1.0/new";
    //url for detailed books
    String url_book_info = "https://api.itbook.store/1.0/books/";

    //initialising  variables
    ArrayList<Book> books;
    Book book;
    BookItemAdapter adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //filling the list depending on the new books info
        ListView book_list = (ListView) findViewById(R.id.bookList);
        books = new ArrayList<>();
        this.fetchData();
        adapter = new BookItemAdapter(getApplicationContext(), books);
        book_list.setAdapter(adapter);

        //on click redirects to more detailed page about book
        book_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //shared prefs for isbn for detail api
                SharedPreferences sh = getSharedPreferences("Prefs_ISBN", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                Book book = books.get(i);
                myEdit.putString("isbn", book.getId());
                //saving it
                myEdit.apply();
                //intent to redirect to another activity
                Intent myIntent = new Intent(MainActivity.this, BookDetails.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }

    //fetching the information from the new books api
    public void fetchData() {

        //custom request allow us to use string in listener instead of JsonArray
        CustomRequest request = new CustomRequest(Request.Method.GET, url_books, new Response.Listener<String>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(String response) {
                try {
                    //using json attributes to access the data
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("books"); //name of the array

                    //for each book in the array
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        //setting the details for the book
                        book = new Book();
                        book.setTitle(jsonObject.getString("title"));
                        book.setId(jsonObject.getString("isbn13"));
                        book.setSubtitles(jsonObject.getString("subtitle"));
                        book.setPrice(jsonObject.getString("price"));
                        book.setImage(jsonObject.getString("image"));

                        //getting the detailed information of each book for later usage
                        books.add(book);

                        // notifying list adapter about data changes
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error: " + error.getCause(), Toast.LENGTH_LONG).show();
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