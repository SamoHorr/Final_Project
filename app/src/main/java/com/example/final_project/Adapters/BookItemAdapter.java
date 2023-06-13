package com.example.final_project.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.final_project.Classes.Book;
import com.example.final_project.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookItemAdapter extends BaseAdapter {

    //initialisation
    Context mcontext;
    ArrayList<Book> books = null;

    public BookItemAdapter(Context context, ArrayList<Book> books) {
        this.mcontext = context;
        this.books = books;
    }

    @SuppressLint("LongLogTag")
    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        //inflating the view
        LayoutInflater inflater = (LayoutInflater) this.mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
        View book_view = view;

        //getting the book position from it's index in the array
        Book book = books.get(pos);

        //when we get the first book position it will have a different design calling a different layout
        if (pos == 0) {

            book_view = inflater.inflate(R.layout.book_list_home_1, null);

            //setting a different design
            TextView title_first = (TextView) book_view.findViewById(R.id.book_title_first);
            ImageView image_first = (ImageView) book_view.findViewById(R.id.book_image_first);
            title_first.setText(book.getTitle());
            Picasso.get().load(book.getImage()).into(image_first);
        } else {

            book_view = inflater.inflate(R.layout.book_list_home, null);

            //setting the view info depending on the book
            TextView title = (TextView) book_view.findViewById(R.id.book_title);
            ImageView book_image = (ImageView) book_view.findViewById(R.id.book_image);
            TextView subtitle = (TextView) book_view.findViewById(R.id.book_subtitle);
            TextView price = (TextView) book_view.findViewById(R.id.book_price);
            title.setText(book.getTitle());
            subtitle.setText(book.getSubtitles());
            price.setText(book.getPrice());
            Picasso.get().load(book.getImage()).into(book_image);
        }

        return book_view;
    }


    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
