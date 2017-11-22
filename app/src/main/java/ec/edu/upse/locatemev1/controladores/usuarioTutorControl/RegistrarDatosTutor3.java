package ec.edu.upse.locatemev1.controladores.usuarioTutorControl;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.modelo.MetodosGenerales;
import ec.edu.upse.locatemev1.modelo.Usuario;

public class RegistrarDatosTutor3 extends AppCompatActivity {
    MetodosGenerales metodosGenerales = new MetodosGenerales();
    EditText txtDireccion,txtEmail;
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_datos_tutor3);

        txtDireccion=(EditText)findViewById(R.id.txt_direccion);
        txtEmail=(EditText)findViewById(R.id.txt_email);


        usuario=getIntent().getParcelableExtra("Usuario");
    }

    public void irInterfaz(View view){
        txtDireccion.setText("Santa Elena");
        //txtEmail.setText("d@hotmail.com");
        if (txtDireccion.length()!=0) {
            Toast.makeText(getApplicationContext(), "Dirección",
                    Toast.LENGTH_SHORT).show();

            if (txtEmail.length()!=0) {
                Toast.makeText(getApplicationContext(), "Email",
                        Toast.LENGTH_SHORT).show();
                if (metodosGenerales.validarEmail(txtEmail.getText().toString()) == true) {
                    Toast.makeText(getApplicationContext(), "Email",
                            Toast.LENGTH_SHORT).show();

                    //ejecuto el metodo post
                    new HttpCorreoRepetido().execute();

                } else {
                    txtEmail.setError("Ingrese un correo válido");
                    onRestart();
                }
            } else {
                txtEmail.setError("Ingrese su correo");
                onRestart();
            }
        } else {
            txtDireccion.setError("Ingrese su dirección"); // show error
            onRestart();

        }
    }

    private class HttpCorreoRepetido extends AsyncTask<Void,Void,List<String>> {
        String strDireccion=txtDireccion.getText().toString();
        String strEmail=txtEmail.getText().toString();

        String cambiarFormato =strEmail.replaceAll(" ","&");
        String formato = cambiarFormato.replace(".","*");


        @Override
        protected List<String> doInBackground(Void... voids) {
            try
            {
                String url = "http://192.168.1.100:8080/WebServiceAlertasSpring/api/usuario/validarCorreoRepetido/"+formato;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<Boolean> response= restTemplate.getForEntity(url,Boolean.class);
                //List<String> correo = Arrays.asList(response.getBody());

                    usuario.setUsuUDireccion(strDireccion);
                    usuario.setUsuUCorreo(strEmail);
                    Intent intent = new Intent(RegistrarDatosTutor3.this, RegistrarDatosTutor4.class);
                    intent.putExtra("Usuario", usuario);
                    startActivity(intent);

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

    }

}
