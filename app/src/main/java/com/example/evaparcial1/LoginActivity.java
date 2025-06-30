package com.example.evaparcial1;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private TextView txtResp;
    private RequestQueue queue;
    private static final String URL = "https://uteqia.com/api/choferes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chofer);

        txtResp = findViewById(R.id.txtResp);
        queue   = Volley.newRequestQueue(this);

        fetchChoferes();
    }

    private void fetchChoferes() {
        txtResp.setText("Cargando datos…");

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, URL, null,
                this::handleResponse,
                this::handleError
        );

        queue.add(request);
    }



    private void handleResponse(JSONArray array) {
        SpannableStringBuilder sb = new SpannableStringBuilder();

        if (array.length() == 0) {
            sb.append("No hay choferes disponibles.");
            txtResp.setText(sb);
            return;
        }

        for (int i = 0; i < array.length(); i++) {
            JSONObject c = array.optJSONObject(i);

            // ID
            int start = sb.length();
            sb.append("ID: ");
            int end = sb.length();
            sb.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb.setSpan(new ForegroundColorSpan(Color.BLUE), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb.append(c.optInt("id", -1) + "\n");

            // Nombre
            start = sb.length();
            sb.append("Nombre: ");
            end = sb.length();
            sb.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb.setSpan(new ForegroundColorSpan(Color.BLUE), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb.append(c.optString("nombre","N/D") + "\n");

            // Cédula
            start = sb.length();
            sb.append("Cédula: ");
            end = sb.length();
            sb.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb.setSpan(new ForegroundColorSpan(Color.BLUE), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb.append(c.optString("cedula","N/D") + "\n");

            // Teléfono
            start = sb.length();
            sb.append("Teléfono: ");
            end = sb.length();
            sb.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb.setSpan(new ForegroundColorSpan(Color.BLUE), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb.append(c.optString("telefono","N/D") + "\n");

            // Separador
            sb.append(" \n");
        }

        txtResp.setText(sb);
    }


    private void handleError(VolleyError error) {
        String msg = (error.networkResponse != null)
                ? "Código: " + error.networkResponse.statusCode
                : (error.getMessage() != null ? error.getMessage() : "Error desconocido");
        txtResp.setText("Fallo al cargar: " + msg);
        Toast.makeText(this, "Comprueba conexión y URL", Toast.LENGTH_SHORT).show();
    }
}
