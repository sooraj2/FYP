package com.student.mess10;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ApiCall {
    private static ApiCall instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private ApiCall(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized ApiCall getInstance(Context context) {
        if (instance == null)
            instance = new ApiCall(context);
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}

