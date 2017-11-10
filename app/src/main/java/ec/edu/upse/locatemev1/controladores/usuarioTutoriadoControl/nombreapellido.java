package ec.edu.upse.locatemev1.controladores.usuarioTutoriadoControl;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.configuracion.ParametrosConexion;
import ec.edu.upse.locatemev1.configuracion.VariablesGenerales;
import ec.edu.upse.locatemev1.modelo.TipoDiscapacidad;
import ec.edu.upse.locatemev1.modelo.Usuario;

public class nombreapellido extends AppCompatActivity {
    Button btnsiguiente;
    Spinner sp_listadiscapacidad;
    EditText txt_nombre;
    EditText txt_apellido;

    Usuario usuario= new Usuario();
    VariablesGenerales variablesGenerales;
    List<TipoDiscapacidad> listaTipoDiscapacidad;
    List<String> str_Lista=new ArrayList<String>();
    ParametrosConexion con =new ParametrosConexion();
    ArrayAdapter<String> adaptador;
    TipoDiscapacidad tipoDiscapacidadSeleccionada= new TipoDiscapacidad();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nombreapellido);
        anadirElementos();
        validacionesIniciales();

        //sobreescritura del spiner en el evento seleccionar un item... este metodo es para seleccionar el tipo de discapacidad
        //
        sp_listadiscapacidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //System.out.println(sp_listadiscapacidad.getSelectedItem());
                for(TipoDiscapacidad td: listaTipoDiscapacidad){
                    if(td.getUsuTipoDescripcion()==sp_listadiscapacidad.getSelectedItem()){
                        tipoDiscapacidadSeleccionada=td;
                    }
                }
                //Toast toast1 = Toast.makeText(getApplicationContext(),tipoDiscapacidadSeleccionada.toString(), Toast.LENGTH_SHORT);
                //toast1.show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void anadirElementos(){
        btnsiguiente =(Button)findViewById(R.id.btn_siguiente);
        sp_listadiscapacidad=(Spinner)findViewById(R.id.sp_discapacidades);
        txt_nombre=(EditText)findViewById(R.id.editTextnombre);
        txt_apellido=(EditText)findViewById(R.id.editTextapellido);
        variablesGenerales = ((VariablesGenerales)getApplicationContext());
    }

    public void validacionesIniciales() {

                listaTipoDiscapacidad=variablesGenerales.getListaTipoDiscapacidad();

                if (listaTipoDiscapacidad==null){
                    new HttpListaTipoDiscapacidad().execute();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"llenaste lista por primera vez", Toast.LENGTH_LONG);
                    toast1.show();

                }else{
                    for(TipoDiscapacidad tipo: listaTipoDiscapacidad){
                        str_Lista.add(tipo.getUsuTipoDescripcion());
                    }
                    adaptador = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,str_Lista);
                    sp_listadiscapacidad.setAdapter(adaptador);
                    adaptador.notifyDataSetChanged();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"llenaste lista por defecto", Toast.LENGTH_LONG);
                    toast1.show();
                }

    }

    public  void btn_siguiente(View view){
        Usuario usuario= new Usuario();
        usuario.setUsuUNombres(txt_nombre.getText().toString());
        usuario.setUsuUApellidos(txt_apellido.getText().toString());

        Intent intent = new Intent(this, usuariocontrasenia.class);
        intent.putExtra("usuario", usuario);
        intent.putExtra("tipoDiscapacidad", tipoDiscapacidadSeleccionada);
        startActivity(intent);
    }

    //llamada al web service para llenar el combo con los tipos de discapacidades
    private class HttpListaTipoDiscapacidad extends AsyncTask<Void, Void, Void > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                final String url=con.urlcompeta("usuariotutoreado","tiposdiscapacidad/");
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<TipoDiscapacidad[]> response= restTemplate.getForEntity(url, TipoDiscapacidad[].class);
                listaTipoDiscapacidad = Arrays.asList(response.getBody());
                for(TipoDiscapacidad tipo: listaTipoDiscapacidad){
                    str_Lista.add(tipo.getUsuTipoDescripcion());
                }
                System.out.println("llamaste la funcioj");
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            variablesGenerales.setListaTipoDiscapacidad(listaTipoDiscapacidad);
            adaptador = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,str_Lista);
            sp_listadiscapacidad.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        }
    }
}