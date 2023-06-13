package com.example.final_project.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.final_project.Classes.Book;
import com.example.final_project.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {

    //initializing
    Context mContext;
    LayoutInflater inflater;
    ArrayList<Book> books;

    public SearchAdapter(Context mContext, ArrayList<Book> books) {
        this.mContext = mContext;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        //inflating the view

        //inflating the view
        LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View book_view = view;

        book_view = inflater.inflate(R.layout.book_search, null);

        //getting movie from it's position in the array
        Book book = books.get(pos);

        //setting the view depending on the book from the grid
        ImageView book_image = (ImageView) book_view.findViewById(R.id.book_search_image);
        TextView book_title = (TextView) book_view.findViewById(R.id.book_search_title);
        TextView book_subtitle = (TextView) book_view.findViewById(R.id.book_search_subtitle);
        TextView book_authors = (TextView) book_view.findViewById(R.id.book_search_authors);
        TextView book_publisher = (TextView) book_view.findViewById(R.id.book_search_publisher);
        TextView book_language = (TextView) book_view.findViewById(R.id.book_search_language);
        TextView book_pages = (TextView) book_view.findViewById(R.id.book_search_pages);
        TextView book_year = (TextView) book_view.findViewById(R.id.book_search_year);
        TextView book_rating = (TextView) book_view.findViewById(R.id.book_search_rating);
        TextView book_description = (TextView) book_view.findViewById(R.id.book_search_description);
        TextView book_price = (TextView) book_view.findViewById(R.id.book_search_price);
        //
        Picasso.get().load(book.getImage()).into(book_image);
        book_title.setText(book.getTitle());
        book_subtitle.setText(book.getSubtitles());
        book_authors.setText(book.getAuthor());
        book_publisher.setText(book.getPublisher());
        book_language.setText(book.getLanguage());
        book_pages.setText(book.getPages());
        book_year.setText(book.getYear());
        book_rating.setText(book.getRating());
        book_description.setText(book.getDescription());
        book_price.setText(book.getPrice());

        return book_view;
    }
}
