package ec.edu.upse.locatemev1.controladores.usuarioTutorControl;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.configuracion.ParametrosConexion;
import ec.edu.upse.locatemev1.configuracion.VariablesGenerales;
import ec.edu.upse.locatemev1.configuracion.MetodosGenerales;
import ec.edu.upse.locatemev1.modelo.Usuario;


public class RegistrarDatosTutor extends AppCompatActivity {

    MetodosGenerales metodosGenerales = new MetodosGenerales();
    Boolean var_repetido;
    Usuario usuario = new Usuario();
    EditText txtNombres, txtApellidos, txtCedula;
    ParametrosConexion con =new ParametrosConexion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_datos_tutor);
        //Inicializa componentes de la interfaz
        iniciarlizar();

    }

    //inicializa variables del layout
    public void iniciarlizar(){
        txtNombres=(EditText)findViewById(R.id.txt_nombresTutor);
        txtApellidos=(EditText)findViewById(R.id.txt_apellidosTutor);
        txtCedula=(EditText)findViewById(R.id.txt_cedula);
    }


    private class HttpCedulaRepetida extends AsyncTask<Void,Void,List<String>> {
        //obtener los datos del formulario
        String strCedula=txtCedula.getText().toString();
        String strNombre=txtNombres.getText().toString();
        String strApellido=txtApellidos.getText().toString();
        @Override
        protected List<String> doInBackground(Void... voids) {
            try
            {
                //String url = "http://192.168.101.12:8080/WebServiceAlertasSpring/api/usuario/validarCedulaRepetida/"+strCedula;
                String parametro= "validacedula/"+txtCedula.getText().toString()+"/";
                final String url=con.urlcompeta("usuariotutoreado",parametro);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<Boolean> response= restTemplate.getForEntity(url,Boolean.class);
                 //List<String> cedula = Arrays.asList(response.getBody());
                if(response.getBody()==true){

                    VariablesGenerales.setIntBandera(1);
                }
                else{
                    VariablesGenerales.setIntBandera(0);

                    usuario.setUsuUNombres(strNombre);
                    usuario.setUsuUApellidos(strApellido);
                    usuario.setUsuUCedula(strCedula);

                    //instanciar para pasar el objeto
                    Intent intent=new Intent(RegistrarDatosTutor.this, RegistrarDatosTutor2.class);
                    //pasar objeto tutor a otro activity
                    intent.putExtra("Usuario", usuario);
                    startActivity(intent);

                    //System.out.println("cedula no existe");
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);

            if (VariablesGenerales.getIntBandera().equals(1)){
                txtCedula.setError("Este Usuario ya fue Registrado");
            }
        }
    }

    public void siguiente(View view){
        //txtNombres.setText("Denny");
        //txtApellidos.setText("ba");
        //txtCedula.setText("2400162141");

        //valida que no no este vacío el campo
        if(txtNombres.length()!=0){
            Toast.makeText(getApplicationContext(), "Nombres",
                    Toast.LENGTH_SHORT).show();

        }else
        {
            Toast toast = Toast.makeText(this, "Escriba su nombre" , Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,5,5);
            toast.show();
            //muestra una alerta de campo vacío
            txtNombres.setError("Ingrese nombre"); // show error
            onRestart();
        }

        if (txtApellidos.length()!=0) {
            Toast.makeText(getApplicationContext(), "Apellido",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast toast = Toast.makeText(this, "Escriba su apellido" , Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,5,5);
            toast.show();
            //muestra una alerta de campo vacío
            txtApellidos.setError("Ingrese su apellido ");
            onRestart();
        }

        if (txtCedula.length()!=0){

              if (metodosGenerales.validadorDeCedula(txtCedula.getText().toString()) == true )
              {
                  //ejecuto el metodo post de cedula repetida
                   //new HttpCedulaRepetida().execute();
                       /* if (VariablesGenerales.getIntBandera()==1) {
                            txtCedula.setError("Cédula es repetida");
                            onRestart();
                        }*/
                       /*if (VariablesGenerales.getIntBandera()==0){
                            //Toast toast = Toast.makeText(this, "Nueva cédula ingresada" , Toast.LENGTH_SHORT);
                            //toast.setGravity(Gravity.CENTER,5,5);
                            //toast.show();

                           //muestra una alerta de campo vacío
                            txtCedula.setError("Nueva cédula ingresada");
                        }*/

                    } else {
                        //muestra una alerta de cédula no es válida
                        txtCedula.setError("Cédula no válida ");
                        onRestart();
                    }
                } else {
                    //muestra una alerta de campo vacío
                    txtCedula.setError("Ingrese cédula ");
                    onRestart();
                }

    }
//0928387265

    public void onStart(){
        super.onStart();
    }
     /*Button btnsiguiente = (Button) findViewById(R.id.btnsiguiente);
        btnsiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrarDatosTutor.this, RegistrarDatosTutor2.class);
                startActivity(intent);
            }
        });*/

}
