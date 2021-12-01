package pe.edu.usat.hmera.laboratorio.serviciosweb;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import pe.edu.usat.hmera.laboratorio.serviciosweb.logica.Motivo;
import pe.edu.usat.hmera.laboratorio.serviciosweb.logica.Sesion;
import pe.edu.usat.hmera.laboratorio.serviciosweb.util.Helper;

public class RegistrarAnticipo extends AppCompatActivity implements View.OnClickListener {
    Spinner spnrMotivoAnticipo,spnrSedeAnticipo;
    EditText txtDescripcionAnticipo,txtFechaInicio,txtFechaFin;
    Button btnRegistrarAnticipo;
    EditText txtAnticipoRubroPasaje,txtAnticipoRubroAlimentacion,txtAnticipoRubroHotel,txtAnticipoRubroMovilidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_anticipo);
        setTitle("Solicitud de Anticipo");

        spnrMotivoAnticipo = findViewById(R.id.spnrMotivoAnticipo);
        spnrSedeAnticipo = findViewById(R.id.spnrSedeAnticipo);


        txtDescripcionAnticipo = findViewById(R.id.txtDescripcionAnticipo);
        txtFechaFin = findViewById(R.id.txtFechaFin);
        txtFechaInicio = findViewById(R.id.txtFechaInicio);

        btnRegistrarAnticipo = findViewById(R.id.btnRegistrarAnticipo);
        btnRegistrarAnticipo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    private class MotivoAnticipoTask extends AsyncTask<Void,Void,Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean respuesta = false;
            try{
                String urlConsulta = Helper.BASE_URL_WS + "/motivo/listar";
                HashMap<String,String> parametros = new HashMap<>();
                parametros.put("token",Sesion.TOKEN);

                String listaMotivosJSON = new Helper().requestHttpPost(urlConsulta,parametros);

                JSONObject objetoJSON = new JSONObject(listaMotivosJSON);

                if(objetoJSON.getBoolean("status")){
                    JSONArray data = objetoJSON.getJSONArray("data");
                    Motivo.listaMotivo.clear();

                    for (int i = 0; i < data.length(); i++){
                        JSONObject motivo = data.getJSONObject(i);
                        Motivo m = new Motivo();
                        m.setId(motivo.getInt("id_motivo"));
                        m.setDescripcion(motivo.getString("descripcion"));
                        Motivo.listaMotivo.add(m);
                    }
                    respuesta = true;
                }

            }catch (Exception e){
                Toast.makeText(RegistrarAnticipo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return respuesta;}

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean){
                String descMotivo[] = new String[Motivo.listaMotivo.size()];
                for (int i = 0; i < Motivo.listaMotivo.size();i++){
                    descMotivo[i] = Motivo.listaMotivo.get(i).getDescripcion();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        getApplicationContext(),
                        R.layout.activity_registrar_anticipo,
                        descMotivo
                );
                spnrMotivoAnticipo.setAdapter(adapter);
            }
        }
    }
}