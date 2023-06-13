package com.example.final_project.ForVolleyCall;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CustomRequest extends StringRequest {
    //custom request to use string instead of jsonArray on Listener
    //initialisation
    private Map<String, String> header;
    private String isbn = "";


    public CustomRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        this.setToken(isbn);

    }

    private void setToken(String token) {

        this.isbn = isbn;
    }


    //redefining the method getHeaders to pass the authentication token as the value of authorization
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (this.header == null) {
            this.header = new HashMap<String, String>();
        }
        this.header.put("x-rapidapi-key", isbn);
        return this.header;
    }
}
