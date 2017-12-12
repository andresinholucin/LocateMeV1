package ec.edu.upse.locatemev1.controladores.usuarioTutoriadoControl;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.configuracion.MetodosGenerales;
import ec.edu.upse.locatemev1.configuracion.ParametrosConexion;
import ec.edu.upse.locatemev1.modelo.TipoDiscapacidad;
import ec.edu.upse.locatemev1.modelo.Usuario;

public class usuariocontrasenia extends AppCompatActivity {
    Button btnsiguiente;
    EditText txtUsuario;
    EditText txtContraseña;
    EditText txtRepiteContraseña;
    Usuario usuario;

    String accion;

    ParametrosConexion con =new ParametrosConexion();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuariocontrasenia);
        anadirElementos();

        try {
            validacionesIniciales();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void anadirElementos(){
        btnsiguiente=(Button)findViewById(R.id.btn_siguiente);
        txtUsuario =(EditText)findViewById(R.id.txt_usuario);
        txtContraseña =(EditText)findViewById(R.id.txt_pass);
        txtRepiteContraseña =(EditText)findViewById(R.id.txt_passRepetir);
        usuario=getIntent().getParcelableExtra("usuario");
        accion=getIntent().getStringExtra("accion");

    }

    public void btn_siguiente(View v){
        if (validaciones()){

            if(accion==null){
                usuario.setUsuUUsuario(txtUsuario.getText().toString());
                usuario.setUsuUClave(txtContraseña.getText().toString());

                Intent intent=new Intent(usuariocontrasenia.this, datospersonales1.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
            else{
                usuario.setUsuUUsuario(txtUsuario.getText().toString());
                String clave=MetodosGenerales.cryptMD5(txtContraseña.getText().toString());
                usuario.setUsuUClave(clave);
                new HttpEnviaPostUsuario().execute();
            }
        }
    }

    public void validacionesIniciales() throws Exception {
        if(accion==null){
            Toast.makeText(this,"llegaste desde crear usuario",Toast.LENGTH_SHORT).show();
        }else if(accion.equals("perfil")){
            Toast.makeText(this,"llegaste de perfil",Toast.LENGTH_SHORT).show();
            btnsiguiente.setText("Actualizar Datos");
            txtUsuario.setText(usuario.getUsuUUsuario());

            //String contra=usuario.getUsuUClave();
            //txtContraseña.setText(contra);
        }
    }

    public Boolean validaciones(){

        String usuario= txtUsuario.getText().toString();
        String contrasenia= txtContraseña.getText().toString();
        String repite= txtRepiteContraseña.getText().toString();

        if(usuario.isEmpty()){
            txtUsuario.setError("Ingrese Usuario");
            return false;
        }else if(contrasenia.isEmpty()){
            txtContraseña.setError("Ingrese Conteraseña");
            return false;
        }else if(repite.isEmpty()){
            txtRepiteContraseña.setError("RepitaContraseña");
            return false;
        } else if(!txtContraseña.getText().toString().equals(txtRepiteContraseña.getText().toString())){
            Toast toast= Toast.makeText(getApplicationContext(),"Contraseñas Incorrectas", Toast.LENGTH_LONG);
            toast.show();
            return false;
        }else{
            return true;
        }
    }

    private class HttpEnviaPostUsuario extends AsyncTask<Void, Void, Usuario > {
        AlertDialog.Builder builder;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            builder = new AlertDialog.Builder(usuariocontrasenia.this);
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
                builder.setMessage("Datos Actualizados");
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
                builder.setMessage("Usuario No Pudo Ser Actualizado");
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
