package ec.edu.upse.locatemev1.controladores.usuarioTutoriadoControl;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import ec.edu.upse.locatemev1.modelo.Perimetro;
import ec.edu.upse.locatemev1.modelo.TiempoSensado;
import ec.edu.upse.locatemev1.modelo.TipoDiscapacidad;
import ec.edu.upse.locatemev1.modelo.Usuario;
import ec.edu.upse.locatemev1.modelo.UsuarioAsignado;

public class configuracionUsuario extends AppCompatActivity {
    CheckBox chk_sms;
    Button btn_aceptar;

    Spinner sp_tiemposensado;
    Spinner sp_perimetro;

    ParametrosConexion con =new ParametrosConexion();

    List<TiempoSensado> listaTiempoSensado;
    List<Perimetro> listaPerimetro;
    VariablesGenerales variablesGenerales;
    ArrayAdapter<String> adaptador;
    List<String> str_ListaTiempoSensado=new ArrayList<String>();
    List<String> str_ListaPerimetro=new ArrayList<String>();
    Perimetro perimetroSeleccionado;
    TiempoSensado tiempoSensadoSeleccionado;
    Usuario usuario;
    UsuarioAsignado ua=new UsuarioAsignado();
    //TipoDiscapacidad tipoDiscapacidadSeleccionada;
    String mensaje="";

    String accion;
    AlertDialog alert = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_usuario);

        anadirElementos();


        //selecciona un elemento del spinner perimetros
        sp_perimetro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for(Perimetro perimetro: listaPerimetro){
                    if(perimetro.getUsuPerimetroDescripcion()==sp_perimetro.getSelectedItem()){
                        perimetroSeleccionado=perimetro;
                    }
                }
                //System.out.println(perimetroSeleccionado.getUsuPerimetroDescripcion());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //selecciona un elemento del spinner tiempos sensados
        sp_tiemposensado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for(TiempoSensado tiempoSensado: listaTiempoSensado){
                    if(String.valueOf(tiempoSensado.getUsuTiempoDescripcion())==sp_tiemposensado.getSelectedItem()){
                        tiempoSensadoSeleccionado=tiempoSensado;
                    }
                }
                //System.out.println(tiempoSensadoSeleccionado.getUsuTiempoDescripcion());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        validacionesIniciales();
        //Toast.makeText(this,"usuario "+ usuario,Toast.LENGTH_LONG).show();
        //Toast.makeText(this, accion,Toast.LENGTH_LONG).show();
    }

    public void anadirElementos(){
        sp_perimetro=(Spinner)findViewById(R.id.sp_perimetro);
        sp_tiemposensado=(Spinner)findViewById(R.id.sp_tiempoSensado);
        chk_sms=(CheckBox)findViewById(R.id.chk_sms);
        btn_aceptar=(Button)findViewById(R.id.btn_aceptar);

        variablesGenerales = ((VariablesGenerales)getApplicationContext());
        usuario=getIntent().getParcelableExtra("usuario");
        accion=getIntent().getStringExtra("accion");
        //tipoDiscapacidadSeleccionada=getIntent().getParcelableExtra("tipoDiscapacidad");
    }

    public void validacionesIniciales(){
        listaTiempoSensado= variablesGenerales.getListaTiempoSensado();
        listaPerimetro=variablesGenerales.getListaPerimetro();

        //validacion para no hacer peticion mas de dos veces una vez abierta la aplicacion para tiempos sensados,
        // estos solo se llenan en caso de que la peticion ya fue hecha
        if (listaTiempoSensado==null){
            new HttpListaTiempoSensado().execute();
        }else{
            for(TiempoSensado tiempo: listaTiempoSensado){
                str_ListaTiempoSensado.add(String.valueOf(tiempo.getUsuTiempoDescripcion()));
            }
            adaptador = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,str_ListaTiempoSensado);
            sp_tiemposensado.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        }

        //validacion para no hacer peticion mas de dos veces una vez abierta la aplicacion para los perimetros de configuracion,
        // estos solo se llenan en caso de que la peticion ya fue hecha
        if(listaPerimetro==null){
            new HttpListaPerimetros().execute();
        }else{
            for(Perimetro perimetro:listaPerimetro){
                str_ListaPerimetro.add(String.valueOf(perimetro.getUsuPerimetroDescripcion()));
                adaptador = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,str_ListaPerimetro);
                sp_perimetro.setAdapter(adaptador);
                adaptador.notifyDataSetChanged();
            }
        }

        if(accion==null){
            //esta seccion de codigo se ejecuta cuando se guardara un usuario por primera vez
            //Toast.makeText(this, "accion nulo", Toast.LENGTH_SHORT).show();
            btn_aceptar.setText("Aceptar");

        }else if(accion.equals("menuconfigurar")){
            //esta seccion se ejecutara cuando se edite la configuracion de un usuario
            //este codigo se ejecuta cuando de la lista de tutoreados nos movemos a la configuracion del tutoreado
            btn_aceptar.setText("Editar");
            Toast.makeText(this, "estas listo", Toast.LENGTH_SHORT).show();
            //desactivar combos
            sp_tiemposensado.setEnabled(false);
            sp_perimetro.setEnabled(false);
            chk_sms.setEnabled(false);

            //llenar con predeterminados
            String inicializarItemtiempo = String.valueOf(usuario.getTiempoSensado().getUsuTiempoDescripcion());
            sp_tiemposensado.setSelection(obtenerPosicionItem(sp_tiemposensado,inicializarItemtiempo));

            String inicializarItemperimetro = usuario.getPerimetroSensado().getUsuPerimetroDescripcion();
            sp_perimetro.setSelection(obtenerPosicionItem(sp_perimetro,inicializarItemperimetro));

            if(usuario.getUsuUSms().equals("A")){
                chk_sms.setChecked(true);
            }else{
                chk_sms.setChecked(false);
            }

        }

    }

    public static int obtenerPosicionItem(Spinner spinner, String str) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(str)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
    }

    public void aceptar(View view){

            if(accion==null){
                //guardar un usuario nuevo
                mensaje="Usuario Tutoreado Creado";
                usuario.setPerimetroSensado(perimetroSeleccionado);
                usuario.setTiempoSensado(tiempoSensadoSeleccionado);//System.out.println(usuario);
                if(chk_sms.isChecked()){
                    usuario.setUsuUSms("A");
                }else{
                    usuario.setUsuUSms("X");
                }
                //new HttpEnviaPostUsuario().execute();
                ua.setEstado("A");
                ua.setUsuario1(VariablesGenerales.getUsuarioTutor());
                ua.setUsuario2(usuario);
                new HttpEnviaPostUsuarioAsignado().execute();

            }else if(btn_aceptar.getText().equals("Editar")){
                //Habilita la edicion de los controles
                Toast.makeText(this,"modifica",Toast.LENGTH_SHORT).show();
                sp_tiemposensado.setEnabled(true);
                sp_perimetro.setEnabled(true);
                chk_sms.setEnabled(true);
                btn_aceptar.setText("Actualizar");

            }else if(btn_aceptar.getText().equals("Actualizar")){
                Toast.makeText(this,"Actualizar informacion",Toast.LENGTH_SHORT).show();
                //guardar un usuario nuevo
                //usuario.setTipoDiscapacidad(tipoDiscapacidadSeleccionada);
                mensaje="Usuario Tutoreado Actualizado";
                usuario.setPerimetroSensado(perimetroSeleccionado);
                usuario.setTiempoSensado(tiempoSensadoSeleccionado);

                if(chk_sms.isChecked()){
                    usuario.setUsuUSms("A");
                }else{
                    usuario.setUsuUSms("X");
                }

                new HttpEnviaPostUsuario().execute();
                //System.out.println(usuario);
            }
    }

    //llama web service y devuelve la lista de tiempos para llenar en el spinner
    private class HttpListaTiempoSensado extends AsyncTask<Void, Void, Void > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                final String url=con.urlcompeta("usuariotutoreado","tiempos/");
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<TiempoSensado[]> response= restTemplate.getForEntity(url, TiempoSensado[].class);
                listaTiempoSensado = Arrays.asList(response.getBody());
                for(TiempoSensado tiempo: listaTiempoSensado){
                    str_ListaTiempoSensado.add(String.valueOf(tiempo.getUsuTiempoDescripcion()));
                }
                //System.out.println("llamaste la funcioj");
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            variablesGenerales.setListaTiempoSensado(listaTiempoSensado);
            adaptador = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,str_ListaTiempoSensado);
            sp_tiemposensado.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
            if(accion==("menuconfigurar")){
                String inicializarItemtiempo = String.valueOf(usuario.getTiempoSensado().getUsuTiempoDescripcion());
                sp_tiemposensado.setSelection(obtenerPosicionItem(sp_tiemposensado,inicializarItemtiempo));
            }
        }
    }

    //llama web service y devuelve la lista de Perimetros para llenar en el spinner
    private class HttpListaPerimetros extends AsyncTask<Void, Void, Void > {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                final String url=con.urlcompeta("usuariotutoreado","perimetros/");
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<Perimetro[]> response= restTemplate.getForEntity(url, Perimetro[].class);
                listaPerimetro = Arrays.asList(response.getBody());
                for(Perimetro perimetro: listaPerimetro){
                    str_ListaPerimetro.add(String.valueOf(perimetro.getUsuPerimetroDescripcion()));
                }
                //System.out.println("llamaste la funcioj");
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            variablesGenerales.setListaPerimetro(listaPerimetro);
            adaptador = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,str_ListaPerimetro);
            sp_perimetro.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
            if(accion==("menuconfigurar")){
                String inicializarItemperimetro = usuario.getPerimetroSensado().getUsuPerimetroDescripcion();
                sp_perimetro.setSelection(obtenerPosicionItem(sp_perimetro,inicializarItemperimetro));
            }
        }
    }

    //envia al web service el usuario
    private class HttpEnviaPostUsuario extends AsyncTask<Void, Void, Usuario > {
        AlertDialog.Builder builder;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            builder = new AlertDialog.Builder(configuracionUsuario.this);
        }

        @Override
        protected Usuario doInBackground(Void... params) {
            try {
                //final String url = "http://172.19.11.195:8084/WebServiceAlertasSpring/api/usuariotutoreado/pruebapost/";
                final String url=con.urlcompeta("usuariotutoreado","registraUsuarioTutoreado/");
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                final Usuario usu= restTemplate.postForObject(url,usuario,Usuario.class);
                System.out.println(usu.toString());

                return usu;
                //mensajeConfirmacion(usu);
                //return listaUsuarios;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(final Usuario usu) {
            super.onPostExecute(usuario);
            if(usu!=null){
                builder.setTitle("Confirmacion!");
                builder.setMessage(mensaje);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent =new Intent(getApplication(),perfilUsuarioTutoreado.class);
                        intent.putExtra("usuario", usu);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.show();
            }else{
                builder.setTitle("Confirmacion!");
                builder.setMessage("Usuario No Pudo Ser Creado");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                builder.show();
            }

        }

    }

    /*envia al web service para guardar un usuario Asignado, se guarda en la tabla usuario asignado y
    en la tabla usuario*/
    private class HttpEnviaPostUsuarioAsignado extends AsyncTask<Void,Void,UsuarioAsignado>{
        AlertDialog.Builder builder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            builder = new AlertDialog.Builder(configuracionUsuario.this);
        }

        @Override
        protected UsuarioAsignado doInBackground(Void... params) {
            try {
                final String url=con.urlcompeta("usuariotutoreado","registraUsuarioAsignado/");
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                final UsuarioAsignado usuasignado= restTemplate.postForObject(url,ua,UsuarioAsignado.class);
                System.out.println(usuasignado.toString());

                return usuasignado;
                //mensajeConfirmacion(usu);
                //return listaUsuarios;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(final UsuarioAsignado usuarioAsignado) {
            super.onPostExecute(usuarioAsignado);
            if(usuarioAsignado!=null){
                builder.setTitle("Confirmacion!");
                builder.setMessage(mensaje);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent =new Intent(getApplication(),perfilUsuarioTutoreado.class);
                        intent.putExtra("usuario", usuarioAsignado);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.show();
            }else{
                builder.setTitle("Confirmacion!");
                builder.setMessage("Usuario No Pudo Ser Creado");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                builder.show();
            }
        }
    }

}
