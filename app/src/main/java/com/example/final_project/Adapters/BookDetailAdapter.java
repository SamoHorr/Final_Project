package com.example.final_project.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.final_project.Classes.Book;
import com.example.final_project.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BookDetailAdapter extends BaseAdapter {
    //initialisation
    Context mcontext;
    ArrayList<Book> books = null;

    public BookDetailAdapter(Context context, ArrayList<Book> books) {
        this.mcontext = context;
        this.books = books;
    }


    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        //inflating the view
        LayoutInflater inflater = (LayoutInflater) this.mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
        View book_view = view;

        Book book = books.get(pos);

        book_view = inflater.inflate(R.layout.book_details, null);

        //setting the view depending on the book selected
        TextView title = (TextView) book_view.findViewById(R.id.book_title);
        ImageView image = (ImageView) book_view.findViewById(R.id.book_image);
        TextView subtitle = (TextView) book_view.findViewById(R.id.book_subtitle);
        TextView authors = (TextView) book_view.findViewById(R.id.book_authors);
        TextView publisher = (TextView) book_view.findViewById(R.id.book_publisher);
        TextView language = (TextView) book_view.findViewById(R.id.book_language);
        TextView year = (TextView) book_view.findViewById(R.id.book_year);
        TextView desc = (TextView) book_view.findViewById(R.id.book_description);
        TextView pages = (TextView) book_view.findViewById(R.id.book_pages);
        TextView price = (TextView) book_view.findViewById(R.id.book_price);
        TextView pdf = (TextView) book_view.findViewById(R.id.book_pdf) ;
        RatingBar rating_bar = (RatingBar) book_view.findViewById(R.id.item_book_ratingbar);

        //
        title.setText(book.getTitle());
        subtitle.setText(book.getSubtitles());
        authors.setText(book.getAuthor());
        publisher.setText(book.getPublisher());
        language.setText(book.getLanguage());
        year.setText(book.getYear());
        desc.setText(book.getDescription());
        pages.setText(book.getPages());
        price.setText(book.getPrice());
        rating_bar.setRating(Float.parseFloat(book.getRating()));
        pdf.setText(book.getUrl_pdf());
        Picasso.get().load(book.getImage()).into(image);

        //returning filled out view
        return book_view;

    }


    @Override
    public int getCount() {
        //if empty getView will not be called
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
