package ec.edu.upse.locatemev1.controladores.usuarioTutorControl;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.modelo.Usuario;

public class RegistrarDatosTutor4 extends AppCompatActivity {
    EditText txtUsuario, txtPass, txtPassRepetir;
    Usuario usuario;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_datos_tutor4);


        txtUsuario = (EditText) findViewById(R.id.txt_usuario);
        txtPass = (EditText) findViewById(R.id.txt_pass);
        txtPassRepetir = (EditText) findViewById(R.id.txt_passRepetir);
        usuario = getIntent().getParcelableExtra("Usuario");

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void guardar(View view) {
        //String json, json1, json2 = null;
        String nuevaClave = txtPassRepetir.getText().toString();
        //txtUsuario.setText("ffff");
        //txtPass.setText("dengig");
        //txtPassRepetir.setText("dengig");
        //Gson gson = new Gson();
        System.out.println("äki llega");
        if (txtUsuario.length() != 0) {
            if ((txtPass.length() != 0) && (txtPass.getText().length() >= 6)) {
                if ((txtPassRepetir.length() != 0) && (txtPass.getText().length() >= 6)) {
                    if (txtPass.getText().toString().equals(nuevaClave)) {

                        System.out.println("äki llega");
                        new HttpUsuarioRepetido().execute();
                        new HttpRegistrarTutor().execute();
                        //Toast toast = Toast.makeText(this, usuario.getUsuUNombres() + " " + usuario.getUsuUApellidos() + " " + usuario.getUsuUCedula() + " " + usuario.getUsuUDia() + " " + usuario.getUsuUMes() + " " + usuario.getUsuUAnio() + " " + usuario.getUsuUTelefono() + " " + usuario.getUsuUDireccion() + " " + usuario.getUsuUCorreo() + " " + usuario.getUsuUUsuario() + " " + usuario.getUsuUClave(), Toast.LENGTH_LONG);
                        //toast.show();
                        //System.out.println("äki llega 2");
                        //Intent intent = new Intent(this, RegistrarDatosTutor4.class);
                        //intent.putExtra("Usuario", usuario);
                        //startActivity(intent);
                        //System.out.println("äki llega 3: "+usuario.toString());
                        /*String jsoncompleto = gson.toJson(usuario, Usuario.class);
                        JSONObject jsonObject = new JSONObject();

                        System.out.println("Aqui");
                        System.out.println(jsoncompleto);
                        json1 = jsoncompleto.toString().replaceAll(" ", "&");
                        json2 = json1.replace(".", "*");*/

                        System.out.println(usuario.getUsuUApellidos());

                    } else {
                        txtPassRepetir.setError("Contrasenia no coinciden");
                        onRestart();
                    }
                } else {
                    txtPassRepetir.setError("La contrasenia debe contener al menos seis caracteres"); // show error
                    onRestart();
                }
            } else {
                txtPass.setError("La contrasenia debe contener al menos seis caracteres");
                onRestart();
            }
        } else {
            txtUsuario.setError("Ingrese su usuario"); // show error
            onRestart();
        }

        //return json2;

    }


   /* public void guardar(View view) {
        //listaTutor.excecute("http://192.168.1.109:8080/WebServiceAlertasSpring/api/usuario/presenta/"+llenar());
//172.19.12.175
        //registrarTutor.excecute("http://192.168.1.106:8080/WebServiceAlertasSpring/api/usuario/insertarUsuario/"+llenar());

        //registrarTutor.excecute("http://192.168.1.104:8080/WebServiceAlertasSpring/api/usuario/insertarUsuario/"+llenar());

        new HttpUsuarioRepetido().execute();
        new HttpRegistrarTutor().execute();
    }*/


    private class HttpRegistrarTutor extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                final String url = "http://192.168.101.12:8080/WebServiceAlertasSpring/api/usuario/insertarTutor1/";
                RestTemplate restTemplate = new RestTemplate();
                System.out.print("Dentro guardar");
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                System.out.print("Dentro guardar");
                restTemplate.postForObject(url, usuario, Usuario.class);
               // System.out.println(cadena.toString());
                //return listaUsuarios;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            System.out.print("Fue creado con éxito");
            super.onPostExecute(aVoid);
        }
    }

    private class HttpUsuarioRepetido extends AsyncTask<Void,Void,List<String>> {
        String strUsuario=txtUsuario.getText().toString();
        String strPass=txtPass.getText().toString();
        @Override
        protected List<String> doInBackground(Void... voids) {
            try
            {
                String url = "http://192.168.101.12:8080/WebServiceAlertasSpring/api/usuario/validarUsuarioRepetido/"+strUsuario;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<Boolean> response= restTemplate.getForEntity(url,Boolean.class);
                //List<String> usuarioRepetido = Arrays.asList(response.getBody());
                if(response.getBody()==true){
                    System.out.print("Usuario ya existe");
                    //strCedula.setError("Cédula no válida ");
                }
                else{
                    usuario.setUsuUUsuario(strUsuario);
                    usuario.setUsuUClave(strPass);

                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

    }

    // Botón de salida (final de la aplicación)
    public void cancelar(View view) {
        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

}
