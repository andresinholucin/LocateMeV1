package ec.edu.upse.locatemev1.controladores.principal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Timer;
import java.util.TimerTask;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.configuracion.AuxDevolucion;
import ec.edu.upse.locatemev1.configuracion.ParametrosConexion;
import ec.edu.upse.locatemev1.configuracion.VariablesGenerales;
import ec.edu.upse.locatemev1.controladores.tabsControl.MenuActivity;
import ec.edu.upse.locatemev1.controladores.usuarioTutorControl.PerfilUsuarioTutor;
import ec.edu.upse.locatemev1.controladores.usuarioTutorControl.RegistrarDatosTutor;
import ec.edu.upse.locatemev1.modelo.MetodosGenerales;

public class MainActivity extends AppCompatActivity {
    VariablesGenerales variablesGenerales;
    ParametrosConexion conexion = new ParametrosConexion();
    public static final String MyPREFERENCES = "MyPrefs" ;
    EditText txtUsuario,txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Cargar la preferencia de inicio de sesion
        SharedPreferences miPreferencia = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String StrSesion = miPreferencia.getString("sesion","");

        // verificar si hay preferencia
        if(StrSesion !=""){
            new HttpRecuperarId().execute();
            iniciarvariables();
            System.out.println("Aki llega XD");
        }else
        {
            setContentView(R.layout.activity_main);
            iniciarvariables();
        }

    }
    //metodo para guardar preferencias del usuario
    public void GuardarPreferenciasId(Long tipo, Long id ){
        SharedPreferences miPreferencia = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = miPreferencia.edit();
        String StrTipo  = String.valueOf(tipo);
        String StrUsuario  = String.valueOf(id);
        editor.putString("tipo", StrTipo);
        editor.putString("id", StrUsuario);
        editor.commit();
    }
    //metodo para guardar preferencias de la sesion
    public void GuardarPreferenciaSesion(){
        SharedPreferences miPreferencia = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = miPreferencia.edit();
        String StrSesion  =  "InicioSesion";
        editor.putString("sesion", StrSesion);
        editor.commit();
    }
    //metodo para iniciar variables de la actividad
    public void iniciarvariables(){
        txtUsuario= (EditText) findViewById(R.id.txt_usuario);
        txtPassword= (EditText) findViewById(R.id.txt_password);
        variablesGenerales = ((VariablesGenerales)getApplicationContext());
    }

    public void inicioSesion(View view){
        try {
            if(txtUsuario.length()==0 &&  txtPassword.length()==0)
            {
                Toast.makeText(MainActivity.this,"Ingrese Usuario y Clave", Toast.LENGTH_LONG).show();
            }
            else {
                final ProgressDialog dialogo = new ProgressDialog(MainActivity.this, R.style.AppTheme_Dark_Dialog);

                dialogo.setIndeterminate(true);
                dialogo.setMessage("Autentificando...");
                dialogo.show();
                long delayInMillis = 5000;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        dialogo.dismiss();
                        new HttpRecuperarId().execute();
                    }
                }, delayInMillis);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private class HttpRecuperarId extends AsyncTask<Void, Void, Void > {

        //Cargar las preferencias del tipo y el id del usuario cuando inicia sesion
        SharedPreferences miPreferencia = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String strTipo = miPreferencia.getString("tipo","");
        String strUsuario = miPreferencia.getString("id","");

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //verificar si hay datos en las preferencias
                if(strTipo ==""){
                    // parametros de la url + nombre de la WS+ el metodo
                    String url = conexion.urlcompeta("usuario","login/"+txtUsuario.getText().toString()+"/"+ MetodosGenerales.cryptMD5(txtPassword.getText().toString()));
                    System.out.println(url);
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    ResponseEntity<AuxDevolucion> response=restTemplate.getForEntity(url,AuxDevolucion.class);

                    //verificar si no biene null
                    if (response.getBody().getIdUsuario()!=null)
                    {
                        // guardar preferencias para la sesion
                        GuardarPreferenciaSesion();
                        //verificar quien es mi tutor o tutoriado por el tipo
                        if(response.getBody().getTipoUsuario()==1)
                        {
                            //obtener el id del usuario
                            Long id = response.getBody().getIdUsuario();
                            //guardar en las preferencias el tipo y el id del usuario
                            GuardarPreferenciasId(response.getBody().getTipoUsuario(),id);
                            // obtener el token
                            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                            System.out.println("TOKEN:   "+refreshedToken);

                            //guardar el id del tutor en variables generales para utilizar en otras actividades
                            VariablesGenerales.setLonIdTutor(id);
                            // finalizar la actividad actual
                            finish();

                            //VariablesGenerales.setIntPeticionTutoriado(0);
                            VariablesGenerales.setIntPeticionAlerta(0);
                            // llamar a la siguiente actividad
                            Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                            startActivity(intent);
                        }
                        else{
                            //obtener el id del usuario
                            Long id = response.getBody().getIdUsuario();
                            //guardar en las preferencias el tipo y el id del usuario
                            GuardarPreferenciasId(response.getBody().getTipoUsuario(),id);
                            // finalizar la actividad actual
                            finish();
                            // llamar a la siguiente actividad
                            Intent intent = new Intent(MainActivity.this,MainTutoreadoActivity.class);
                            startActivity(intent);
                        }

                    }

                }
                else{
                    //verificar quien es mi tutor o tutoriado por el tipo
                    if(Long.valueOf(strTipo)==1){
                        // finalizar la actividad actual
                        finish();

                        VariablesGenerales.setLonIdTutor(Long.valueOf(strUsuario));

                        //VariablesGenerales.setIntPeticionTutoriado(0);

                        VariablesGenerales.setIntPeticionAlerta(0);

                        // llamar a la siguiente actividad
                        Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                        startActivity(intent);
                    }
                    else{
                        // finalizar la actividad actual
                        finish();
                        // llamar a la siguiente actividad
                        Intent intent = new Intent(MainActivity.this,MainTutoreadoActivity.class);
                        startActivity(intent);
                    }

                }



            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }

    /*
        HttpClient recuperarId = new HttpClient(new OnHttpRequestComplete() {

            @Override
            public void onComplete(Response status) {

                if(status.isSuccess()){
                    try {
                        if(status.getResult()!="")
                        {
                            String id = status.getResult();
                            Toast.makeText(MainActivity.this,"id: "+Long.parseLong(id), Toast.LENGTH_LONG).show();
                            //System.out.println("id: "+Long.parseLong(id));
                            GuardarPreferencias();
                            VariablesGenerales.setLonIdTutor(Long.parseLong(id));
                            finishAffinity();
                            //VariablesGenerales.setLonIdUsuario(Long.parseLong(id));
                            Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "Usuario o Clave incorrecta", Toast.LENGTH_LONG).show();

                        }

                    }catch (Exception e){
                        System.out.println("Fallo! "+e.toString());
                        e.printStackTrace();
                    }

                }
                else
                {
                    //Toast.makeText(MainActivity.this, "No hay Datos", Toast.LENGTH_LONG).show();
                }
            }
        });
    */
    public void irInterfaz(View view)
    {
        Intent intent = null;
        switch (view.getId())
        {
            /*case R.id.btnConectar:
                String usuario=txtUsuario.getText().toString();
                String password=txtPassword.getText().toString();

                if (validarusuario(usuario,password)) {
                    intent = new Intent(this, Ingresar.class);
                    intent.putExtra(password,usuario);
                    GuardarPreferencias();
                    Toast.makeText(getApplicationContext(), "Redireccionando...", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectas",Toast.LENGTH_SHORT).show();
                    limpiarvariables();
                    break;
                }
                break;*/

            case R.id.btn_registrar:
                intent = new Intent(this,RegistrarDatosTutor.class);
                break;
            case R.id.btn_editar:
                intent = new Intent(this,PerfilUsuarioTutor.class);
                break;

        }

        if (intent != null) {
            startActivity(intent);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(MainActivity.this, "onResume", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(MainActivity.this, "onStop", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(MainActivity.this, "onRestart", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(MainActivity.this, "onDestroy", Toast.LENGTH_LONG).show();

    }
}
