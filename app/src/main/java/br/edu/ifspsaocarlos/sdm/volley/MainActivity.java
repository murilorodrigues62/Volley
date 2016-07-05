package br.edu.ifspsaocarlos.sdm.volley;

import android.app.Activity;
import android.os.Bundle;
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

import org.json.JSONObject;

public class    MainActivity extends Activity implements View.OnClickListener {
    private Button btAcessarWs;
    private ProgressBar mProgress;
    private EditText edUrl;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btAcessarWs = (Button) findViewById(R.id.bt_acessar_ws);
        btAcessarWs.setOnClickListener(this);
        mProgress = (ProgressBar) findViewById(R.id.pb_carregando);
        edUrl = (EditText) findViewById(R.id.ed_url);
    }
    public void onClick(View v) {
        if (v == btAcessarWs) {
            buscarJson(edUrl.getText().toString());
        }
    }
    private void buscarJson(String url) {

        mProgress.setVisibility(View.VISIBLE);

        // Cria uma lista de requisições usando Volley
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        try {
            // Cria requisição  GET de objeto Json para a URL informada pelo usuário
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject s) {
                            // Simplesmente exibe o conteúdo do json para o usuário
                            ((TextView) findViewById(R.id.tv_texto)).setText(s.toString());

                            mProgress.setVisibility(View.GONE);
                        }
                    }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError volleyError) {
                    // apresenta mensagem em caso de erro
                    mProgress.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, getString(br.edu.ifspsaocarlos.sdm.volley.R.string.msg_erro) + "\n" + volleyError.getMessage().toString(),
                            Toast.LENGTH_LONG).show();
                }
            });

            // adiciona a requisição na fila
            queue.add(jsonObjectRequest);

        } catch (Exception e) {
            Log.e("SDM", getString(br.edu.ifspsaocarlos.sdm.volley.R.string.msg_erro_json));
        }
    }
}