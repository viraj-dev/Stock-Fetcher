package com.example.stock_fetcher;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class MainActivity extends AppCompatActivity {
    TextView stockPriceTextView, exchangeTextView, companyText, percentChange;
    EditText symbol;
    Button updateButton;
    ProgressBar loadingIndicator;
    String symbolString;
    private RequestQueue requestQueue;
    double previousPrice = 0.0;
    private static final String API_KEY = "b7b09ec191msh8c3053e84355717p126327jsn8a01ede94674"; // API key here
    private final Handler handler = new Handler(Looper.getMainLooper());
    private long lastRequestTime = 0; // Timestamp of the last request
    private static final long REQUEST_DELAY = 1000; // 1 second

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);
        stockPriceTextView = findViewById(R.id.stockPriceTextView);
        exchangeTextView = findViewById(R.id.exchangeTextView);
        companyText = findViewById(R.id.companyTextView);
        percentChange = findViewById(R.id.percentageChangeTextView);
        updateButton = findViewById(R.id.updateButton);
        loadingIndicator = findViewById(R.id.loadingIndicator);
        symbol = findViewById(R.id.symbolName);
        requestQueue = Volley.newRequestQueue(this);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                symbolString = symbol.getText().toString().trim();
                if (!symbolString.isEmpty()) {
                    fetchStockPrice();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a valid symbol", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchStockPrice() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastRequestTime < REQUEST_DELAY) {
            long delay = REQUEST_DELAY - (currentTime - lastRequestTime);
            handler.postDelayed(() -> {
                showLoadingIndicator();
                makeApiRequest();
            }, delay);
        } else {
            showLoadingIndicator();
            makeApiRequest();
        }
    }

    private void makeApiRequest() {
        lastRequestTime = System.currentTimeMillis();

        String apiUrl = "https://indian-stock-exchange-api2.p.rapidapi.com/stock?name=" + symbol.getText().toString().trim();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("API Response", response.toString());

                            if (response.has("companyName")) {
                                String companyName = response.getString("companyName");
                                String industry = response.optString("industry", "Unknown Industry");

                                if (response.has("currentPrice")) {
                                    JSONObject currentPrice = response.getJSONObject("currentPrice");
                                    double bsePrice = currentPrice.optDouble("BSE", -1);
                                    double percentageChangeValue = response.optDouble("percentChange", 0.0); // Example key for percentage change

                                    updateStockPrice(roundPrice(bsePrice), "Industry: " + industry, "Company: " + companyName, percentageChangeValue);
                                } else {
                                    Log.e("API_ERROR", "'currentPrice' key not found.");
                                }
                            } else {
                                Log.e("API_ERROR", "'companyName' key not found.");
                                Toast.makeText(MainActivity.this, "Company name not found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("ERROR", "JSON parsing error: " + e.getMessage());
                        }finally {
                            hideLoadingIndicator();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            if (error.networkResponse != null) {
                                int statusCode = error.networkResponse.statusCode;
                                if (statusCode == 429) {
                                    Log.e("API_ERROR", "Too many requests. Retrying in 2 seconds...");
                                    handler.postDelayed(() -> fetchStockPrice(), 2000);
                                } else if (statusCode == 401) {
                                    Log.e("API_ERROR", "Unauthorized. Check API Key or Authentication");
                                } else {
                                    String responseBody = new String(error.networkResponse.data);
                                    Log.e("API_ERROR", "Error Response: " + responseBody);
                                }
                            } else {
                                Log.e("API_ERROR", "Error fetching stock price: " + error.getMessage());
                            }
                        } finally {
                            hideLoadingIndicator();
                        }
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("x-rapidapi-key", API_KEY);
                headers.put("x-rapidapi-host", "indian-stock-exchange-api2.p.rapidapi.com");
                return headers;
            }
        };
        requestQueue.add(request);
    }
    private double roundPrice(double price) {
        return Math.round(price * 100.0) / 100.0;
    }
    private void updateStockPrice(double currentPrice, String exchange, String company, double percentageChangeValue) {
        double instantaneousChange = currentPrice - previousPrice;
        previousPrice = currentPrice;
        handler.post(new Runnable() {
            @Override
            public void run() {
                startBlinkAnimation(stockPriceTextView);
                String formattedPrice = String.format("%.2f", currentPrice);
                String formattedPercentageChange = String.format("%.2f%%", percentageChangeValue);
                stockPriceTextView.setText(formattedPrice);
                percentChange.setText(" " + formattedPercentageChange);
                int textColor = (instantaneousChange >= 0.00) ? Color.GREEN : Color.RED;
                stockPriceTextView.setTextColor(textColor);
                percentChange.setTextColor(textColor);
                exchangeTextView.setText(exchange);
                companyText.setText(company);
            }
        });
    }
    private void startBlinkAnimation(final TextView textView) {
        ObjectAnimator blink = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f, 1f);
        blink.setDuration(1000);
        blink.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void showLoadingIndicator() {
        loadingIndicator.setVisibility(View.VISIBLE);
        updateButton.setText("Searching...");
        updateButton.setEnabled(false);
    }

    private void hideLoadingIndicator() {
        loadingIndicator.setVisibility(View.GONE);
        updateButton.setText("Search Stock");
        updateButton.setEnabled(true);
    }

}
