package ec.edu.upse.locatemev1.controladores.usuarioTutoriadoControl;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.configuracion.ParametrosConexion;
import ec.edu.upse.locatemev1.modelo.TipoDiscapacidad;
import ec.edu.upse.locatemev1.modelo.Usuario;

public class datospersonales1 extends AppCompatActivity {

    Button buttonfecha;
    EditText textfecha;
    private int dia,mes,anio;

    EditText txt_cedula;
    EditText txt_telefono;

    Button btnsiguiente;
    Usuario usuario;
    TipoDiscapacidad tipoDiscapacidadSeleccionada;
    ParametrosConexion con =new ParametrosConexion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datospersonales1);
        anadirElementos();

        txt_cedula.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    System.out.println("perdiste el foco");
                    valida();
                }
            }
        });

    }

    public void valida(){
    new HttpValidacionCedula().execute();
    }

    public void anadirElementos(){
        buttonfecha= (Button)findViewById(R.id.btn_fecha);
        textfecha=(EditText)findViewById(R.id.editTextfechanacimiento);
        txt_cedula=(EditText)findViewById(R.id.editTextcedula);
        //txt_telefono=(EditText)findViewById(R.id.editTexttelefono);
        btnsiguiente=(Button)findViewById(R.id.btn_siguiente);
        usuario=getIntent().getParcelableExtra("usuario");
        tipoDiscapacidadSeleccionada=getIntent().getParcelableExtra("tipoDiscapacidad");
    }

    public void btn_siguiente(View view){
        if (validaciones()){
            usuario.setUsuUCedula(txt_cedula.getText().toString());
            //usuario.setUsuUTelefono(txt_telefono.getText().toString());
            usuario.setUsuUAnio(String.valueOf(anio));
            usuario.setUsuUMes(String.valueOf(mes));
            usuario.setUsuUDia(String.valueOf(dia));

            Intent intent=new Intent(datospersonales1.this, configuracionUsuario.class);
            intent.putExtra("usuario", usuario);
            intent.putExtra("tipoDiscapacidad", tipoDiscapacidadSeleccionada);
            startActivity(intent);

        }

    }

    public Boolean validaciones(){
        return true;
    }

    public void btn_fecha(View view){
        final Calendar c= Calendar.getInstance();
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH);
        anio=c.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                textfecha.setText(dayOfMonth + " / " + (month +1 ) + " / " + year);
                dia=dayOfMonth;
                mes=month+1;
                anio=year;
                Toast.makeText(getApplicationContext(), dia +" "+mes+" "+ anio, Toast.LENGTH_SHORT).show();
            }
        }
                ,dia,mes,anio);

        datePickerDialog.show();
        Toast.makeText(getApplicationContext(), dia +" "+mes+" "+ anio, Toast.LENGTH_SHORT).show();
    }


    private class HttpValidacionCedula extends AsyncTask<Void, Void, Void > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String parametro;
                parametro= "validacedula/"+txt_cedula.getText().toString()+"/";
                final String url=con.urlcompeta("usuariotutoreado",parametro);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<Boolean> response= restTemplate.getForEntity(url, Boolean.class);
                //listaTiempoSensado = Arrays.asList(response.getBody());
                System.out.println(response);
                if(response.toString()=="true"){
                    System.out.println("la cedula ya fue registrada");
                }else{
                    System.out.println("la cedula no ha sido registrada");
                }
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
}
