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
    EditText txtNombre;
    EditText txtApellido;

    Usuario usuarioSeleccionado;
    VariablesGenerales variablesGenerales;
    List<TipoDiscapacidad> listaTipoDiscapacidad;
    List<String> str_Lista=new ArrayList<String>();
    ParametrosConexion conexion =new ParametrosConexion();
    ArrayAdapter<String> adaptador;
    TipoDiscapacidad tipoDiscapacidadSeleccionada= new TipoDiscapacidad();

    String accion;

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
        txtNombre=(EditText)findViewById(R.id.txt_nombresUsuario);
        txtApellido=(EditText)findViewById(R.id.txt_apellidosUsuario);

        variablesGenerales = ((VariablesGenerales)getApplicationContext());

        usuarioSeleccionado=getIntent().getParcelableExtra("usuario");
        accion=getIntent().getStringExtra("accion");
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
                    //Toast toast1 = Toast.makeText(getApplicationContext(),"llenaste lista por defecto", Toast.LENGTH_LONG);
                    //toast1.show();
                }

                if(accion==null){
                    Toast.makeText(this,"llegaste desde crear usuario",Toast.LENGTH_SHORT).show();
                }else if(accion.equals("perfil")){

                    Toast.makeText(this,"llegaste de perfil",Toast.LENGTH_SHORT).show();
                    //btnsiguiente.setText("SIGUIENTE");
                    txtNombre.setText(usuarioSeleccionado.getUsuUNombres());
                    txtApellido.setText(usuarioSeleccionado.getUsuUApellidos());

                    String inicializarItem = usuarioSeleccionado.getTipoDiscapacidad().getUsuTipoDescripcion().toString();
                    sp_listadiscapacidad.setSelection(obtenerPosicionItem(sp_listadiscapacidad,inicializarItem));
                    /*
                    txtApellido.setEnabled(false);
                    txtNombre.setEnabled(false);
                    sp_listadiscapacidad.setEnabled(false);*/
                }

    }

    public static int obtenerPosicionItem(Spinner spinner, String tipo) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida conexion el parametro
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida conexion la búsqueda
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(tipo)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
    }


    public  void btn_siguiente(View view){
        if (validaciones()){

            if(accion==null){
                //Toast.makeText(this,"llegaste desde crear usuario",Toast.LENGTH_SHORT).show();
                Usuario usuario= new Usuario();
                usuario.setUsuUNombres(txtNombre.getText().toString());
                usuario.setUsuUApellidos(txtApellido.getText().toString());
                usuario.setTipoDiscapacidad(tipoDiscapacidadSeleccionada);

                Intent intent = new Intent(this, usuariocontrasenia.class);
                intent.putExtra("usuario", usuario);
                //intent.putExtra("tipoDiscapacidad", tipoDiscapacidadSeleccionada);
                startActivity(intent);

            }else if(accion.equals("perfil")){
                //Toast.makeText(this,"llegaste de perfil",Toast.LENGTH_SHORT).show();
                usuarioSeleccionado.setUsuUNombres(txtNombre.getText().toString());
                usuarioSeleccionado.setUsuUApellidos(txtApellido.getText().toString());
                usuarioSeleccionado.setTipoDiscapacidad(tipoDiscapacidadSeleccionada);

                Intent intent = new Intent(this, datospersonales1.class);
                intent.putExtra("usuario", usuarioSeleccionado);
                intent.putExtra("accion","perfil");
                startActivity(intent);
            }



        }
    }

    public boolean validaciones(){
        String nombre= txtNombre.getText().toString();
        String apellido= txtApellido.getText().toString();
        if (nombre.isEmpty() ||nombre.equals("") ){
            txtNombre.setError("Ingrese Nombre");
            return false;

        }else if(apellido.isEmpty()){
            txtApellido.setError("Ingrese Apellido");
            return false;
        }
    return true;
    }

    //llamada al web service para llenar el combo conexion los tipos de discapacidades
    private class HttpListaTipoDiscapacidad extends AsyncTask<Void, Void, Void > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                final String url= conexion.urlcompeta("usuariotutoreado","tiposdiscapacidad/");
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
            if(accion==("perfil")){
                String inicializarItem = usuarioSeleccionado.getTipoDiscapacidad().getUsuTipoDescripcion().toString();
                sp_listadiscapacidad.setSelection(obtenerPosicionItem(sp_listadiscapacidad,inicializarItem));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "backpressed", Toast.LENGTH_LONG).show();
    }
}