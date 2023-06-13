package com.example.final_project.ForVolleyCall;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Singleton_Volley {

    //since we have several activites singleton for one queue

    private static Singleton_Volley self = null;

    private RequestQueue queue = null;
    private Context context;

    private Singleton_Volley(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(this.context);
    }

    public static synchronized Singleton_Volley getInstance(Context context) {
        if (self == null) {
            self = new Singleton_Volley(context);
        }
        return self;
    }

    public RequestQueue getRequestQueue() {
        return queue;
    }
}
