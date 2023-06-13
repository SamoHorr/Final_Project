package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.final_project.Adapters.BookDetailAdapter;
import com.example.final_project.Adapters.BookItemAdapter;
import com.example.final_project.Adapters.SearchAdapter;
import com.example.final_project.Classes.Book;
import com.example.final_project.ForVolleyCall.CustomRequest;
import com.example.final_project.ForVolleyCall.Singleton_Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;


public class SearchPage extends AppCompatActivity {

    //url for detailed books
    String url_books = "https://api.itbook.store/1.0/new";

    //initialising  variables
    public static ArrayList<Book> bookList = new ArrayList<>();
    Book book;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        //setting it as default selected
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.search_icon);
        //to change on click
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.search_icon:
                        return true;
                    case  R.id.home_icon:
                        startActivity(new Intent(getApplicationContext() , MainActivity.class));
                        overridePendingTransition(0 , 0);
                        return true;
                    case R.id.favorite_icon:
                        startActivity(new Intent(getApplicationContext() , FavoritePage.class));
                        overridePendingTransition(0 , 0);
                        return true;
                }
                return false;
            }
        });


        //filling the grid view with all the books
        GridView grid = (GridView) findViewById(R.id.book_search_gallery);
        this.setUpData();
        SearchAdapter adapter = new SearchAdapter(getApplicationContext() , bookList);
        grid.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //calling methode to search
        initSearchWidgets();

        //on click of any item of the grid redirects to more detailed page about book
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //with intent on click redirects
                Intent myIntent = new Intent(SearchPage.this, BookDetails.class);

                SharedPreferences sh = getSharedPreferences("Prefs_ISBN", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                Book book = bookList.get(i);
                Log.d("THE BOOK  tag =========", book.getId());
                myEdit.putString("isbn", book.getId());
                myEdit.apply();

                SearchPage.this.startActivity(myIntent);
            }
        });
    }


    //the methode that will allow us to use searchView and its functionnality
    private void initSearchWidgets()
    {
        searchView = findViewById(R.id.book_search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            //on when a text is sent (enter button clicked)
            public boolean onQueryTextSubmit(String query) {
                //new array to contain the filtered books by the search
                ArrayList<Book> filteredBooks = new ArrayList<Book>();

                //Log.d("Author search" , book.getAuthor());

                for (Book book : bookList){
//                    if (book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
//                        filteredBooks.add(book);
//                    }
                    if (book.getTitle().toLowerCase().contains(query.toLowerCase())) {
                        filteredBooks.add(book);
                    }
                }
                //calling the adapter to modify the gridview
                SearchAdapter adapter = new SearchAdapter(getApplicationContext() , filteredBooks);
                GridView grid = (GridView) findViewById(R.id.book_search_gallery);
                grid.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            //on any changes called  (backspace , delete , new letter ..etc)
            public boolean onQueryTextChange(String newText) {
                ArrayList<Book> filteredBooks = new ArrayList<Book>();

                for (Book book : bookList){
                    //if ((book.getTitle().toLowerCase().contains(newText.toLowerCase())) || (book.getId().toLowerCase().contains(newText.toLowerCase()) || (book.getAuthor().toLowerCase().contains(newText.toLowerCase())))){
                    if (book.getTitle().toLowerCase().contains(newText.toLowerCase())){
                        filteredBooks.add(book);
                    }

                }
                SearchAdapter adapter = new SearchAdapter(getApplicationContext() , filteredBooks);
                GridView grid = (GridView) findViewById(R.id.book_search_gallery);
                grid.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    //fetching the information from the new books api
    public void setUpData() {

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

                        //Log.d("VERIFICATION" , book.getPrice());

                        //adding the books to the array list to be able to display them with base adapter
                        bookList.add(book);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchPage.this, "error: " + error.getCause(), Toast.LENGTH_LONG).show();
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