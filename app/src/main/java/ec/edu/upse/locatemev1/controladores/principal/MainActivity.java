package ec.edu.upse.locatemev1.controladores.principal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.configuracion.VariablesGenerales;
import ec.edu.upse.locatemev1.controladores.tabsControl.MenuActivity;
import ec.edu.upse.locatemev1.controladores.usuarioTutoriadoControl.nombreapellido;
import ec.edu.upse.locatemev1.modelo.MetodosGenerales;

public class MainActivity extends AppCompatActivity {


    public static final String MyPREFERENCES = "MyPrefs" ;
    EditText Edi_Usuario,Edi_Password;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarvariables();
        CargarPreferencias();

     }

        public void limpiarvariables(){
        Edi_Usuario.setText("");
        Edi_Password.setText("");
    }

    public void CargarPreferencias(){
        SharedPreferences miPreferencia = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Edi_Usuario.setText(miPreferencia.getString("usuario",""));
        Edi_Password.setText(miPreferencia.getString("clave",""));
        if(Edi_Usuario.getText().length()!=0 && Edi_Password.getText().length()!=0)
            new HttpRecuperarId().execute();
    }

    public void GuardarPreferencias(){
        SharedPreferences miPreferencia = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = miPreferencia.edit();
        String Str_usuario  = Edi_Usuario.getText().toString();
        String Str_Clave  = Edi_Password.getText().toString();
        editor.putString("usuario", Str_usuario);
        editor.putString("clave", Str_Clave);
        editor.commit();
    }
    public void iniciarvariables(){
        Edi_Usuario= (EditText) findViewById(R.id.txtUsuario);
        Edi_Password= (EditText) findViewById(R.id.txtPassword);

    }

    public void InicioSesion(View view){
        try {
            if(Edi_Usuario.length()==0 &&  Edi_Password.length()==0)
                Toast.makeText(MainActivity.this,"Ingrese Usuario y Clave", Toast.LENGTH_LONG).show();
            else
                new HttpRecuperarId().execute();
                //recuperarId.excecute(VariablesGenerales.strRuta+"usuario/login/"+Edi_Usuario.getText().toString()+"/"+Edi_Password.getText().toString());

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private class HttpRecuperarId extends AsyncTask<Void, Void, Void > {

        String Str_Usuario  = Edi_Usuario.getText().toString();
        String Str_Clave  = Edi_Password.getText().toString();
        @Override
        protected Void doInBackground(Void... params) {
            try {
                //String url =VariablesGenerales.strRuta+"usuario/login/alberto/12345";
                String url =VariablesGenerales.strRuta+"usuario/login/"+Str_Usuario +"/"+ MetodosGenerales.cryptMD5(Str_Clave);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                ResponseEntity<Long>  response=restTemplate.getForEntity(url,Long.class);
                if (response.getBody()>0)
                {
                    Long Lon_IdTutor = response.getBody();
                    //Toast.makeText(MainActivity.this,"id: "+Long.parseLong(id), Toast.LENGTH_LONG).show();
                    System.out.println("id: "+Lon_IdTutor);
                    GuardarPreferencias();
                    VariablesGenerales.setLonIdTutor(Lon_IdTutor);
                    finish();

                    VariablesGenerales.setIntPeticionTutoriado(0);
                    VariablesGenerales.setIntPeticionAlerta(0);

                    Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                    startActivity(intent);
                }

                //System.out.println("imprime esto : "+response.getBody());
                //listaUsuarios = Arrays.asList(response.getBody());

            } catch (Exception e) {
                e.printStackTrace();
                //Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
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
