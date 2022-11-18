package techng.baggold.requisicoeshttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button buttonRecuperar;
    private TextView text_cep, text_logradouro, text_bairro, text_localidade, text_uf, text_ddd;
    private EditText edit_cep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarComponentes();

        buttonRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String meuCep = edit_cep.getText().toString();

                MyTask task = new MyTask();

                String moeda = "USD";
                String urlApi = "https://blockchain.info/tobtc?currency="+moeda+"&value=500";

                String cep = meuCep;
                String urlCep = "https://viacep.com.br/ws/" + cep + "/json/";
                task.execute(urlCep);

            }
        });
    }

    class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader =  null;
            StringBuffer buffer = null;

            try {
                URL url = new URL( stringUrl );
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                // Recuper os dados em Bytes
                inputStream = conexao.getInputStream();

                //inputStreamReader le os dados em Bytes e decodifica para caracteres
                inputStreamReader = new InputStreamReader(inputStream);

                //Objeto utilizado para a leitura dos caracteres do inputStreamReader
                BufferedReader reader = new BufferedReader(inputStreamReader);
                buffer = new StringBuffer();
                String linha = "";

                while ((linha = reader.readLine()) != null) {
                    buffer.append( linha );
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);

            String cep = null;
            String logradouro = null;
            String bairro = null;
            String localidade = null;
            String uf = null;
            String ddd = null;

            try {
                JSONObject jsonObject = new JSONObject( resultado );

                cep = jsonObject.getString("cep");
                logradouro = jsonObject.getString("logradouro");
                bairro = jsonObject.getString("bairro");
                localidade = jsonObject.getString("localidade");
                uf = jsonObject.getString("uf");
                ddd = jsonObject.getString("ddd");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            text_cep.setText( "CEP: " + cep );
            text_logradouro.setText( "Rua: " + logradouro );
            text_bairro.setText( "Bairro: " + bairro );
            text_localidade.setText("Cidade: " + localidade );
            text_uf.setText( "Estado: " + uf );
            text_ddd.setText("DDD: " + ddd );
        }
    }

    public void iniciarComponentes() {

        //iniciar componentes
        buttonRecuperar = findViewById(R.id.buttonRecuperar);

        text_cep = findViewById(R.id.text_cep);
        text_logradouro = findViewById(R.id.text_logradouro);
        text_bairro = findViewById(R.id.text_bairro);
        text_localidade = findViewById(R.id.text_localidade);
        text_uf = findViewById(R.id.text_uf);
        text_ddd = findViewById(R.id.text_ddd);
        edit_cep = findViewById(R.id.edit_cep);
    }
}