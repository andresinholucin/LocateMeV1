package ec.edu.upse.locatemev1.controladores.usuarioTutoriadoControl;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.configuracion.MetodosGenerales;
import ec.edu.upse.locatemev1.configuracion.ParametrosConexion;
import ec.edu.upse.locatemev1.configuracion.VariablesGenerales;
import ec.edu.upse.locatemev1.modelo.TipoDiscapacidad;
import ec.edu.upse.locatemev1.modelo.Usuario;

public class datospersonales1 extends AppCompatActivity {

    Button buttonfecha;

    private int dia,mes,anio;
    EditText txtFecha;
    EditText txtCedula;
    Button btnsiguiente;
    Usuario usuario;
    TipoDiscapacidad tipoDiscapacidadSeleccionada;
    ParametrosConexion con =new ParametrosConexion();
    String errorCedula;
    MetodosGenerales metodosGenerales=new MetodosGenerales();
    Boolean ced=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datospersonales1);
        anadirElementos();

        txtCedula.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    System.out.println("perdiste el foco");
                    validaCedula();
                }
            }
        });

        calendario();
    }


    public void anadirElementos(){
        txtFecha =(EditText)findViewById(R.id.txt_fechaNacimiento);
        txtCedula =(EditText)findViewById(R.id.txt_cedula);
        //txt_telefono=(EditText)findViewById(R.id.editTexttelefono);
        btnsiguiente=(Button)findViewById(R.id.btn_siguiente);
        usuario=getIntent().getParcelableExtra("usuario");
        tipoDiscapacidadSeleccionada=getIntent().getParcelableExtra("tipoDiscapacidad");

    }

    public void calendario(){//metodo para obtener los datos de la fecha en la clase actual al dar clic
        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HttpValidacionCedula().execute();
                llamaCalendario();
            }
        });
    }

    public void llamaCalendario(){
        final Calendar c= Calendar.getInstance();

        Calendar minCal = Calendar.getInstance();
        minCal.set(Calendar.YEAR, minCal.get(Calendar.YEAR) - 100);
        Calendar maxCal = Calendar.getInstance();
        maxCal.set(Calendar.YEAR, maxCal.get(Calendar.YEAR));

        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH);
        anio=c.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txtFecha.setText(dayOfMonth + " / " + (month +1 ) + " / " + year);
                dia=dayOfMonth;
                mes=month+1;
                anio=year;
            }
        }
                ,dia,mes,anio);

        datePickerDialog.getDatePicker().setMaxDate(maxCal.getTimeInMillis());
        datePickerDialog.getDatePicker().setMinDate(minCal.getTimeInMillis());
        datePickerDialog.show();

        //Toast.makeText(getApplicationContext(), dia +" "+mes+" "+ anio, Toast.LENGTH_SHORT).show();
    }

    public void validaCedula(){
        String cedula= txtCedula.getText().toString();
        if (cedula.isEmpty()){
            txtCedula.setError("Ingrese Cedula");
        }else if(!metodosGenerales.validadorDeCedula(cedula)){
            txtCedula.setError("Cedula Incorrecto");
        }
        new HttpValidacionCedula().execute();
    }

    public void btn_siguiente(View view){

        if (validaciones()){
            usuario.setUsuUCedula(txtCedula.getText().toString());
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
        String cedula= txtCedula.getText().toString();
        String fecha= txtFecha.getText().toString();

        if(cedula.isEmpty()){
            txtCedula.setError("Ingrese Cedula");
            return false;
        }else if(fecha.isEmpty()){
            txtFecha.setError("Seleccione una Fecha");
            return false;
        }else if(!metodosGenerales.validadorDeCedula(cedula)){
            txtCedula.setError("Cedula Incorrecto");
            return false;
        }

        if(ced){
            txtCedula.setError("Este Usuario ya fue Registrado");
            return false;
        }

        return true;
    }

    public void existecedula(){
        try {
            String parametro;
            parametro= "validacedula/"+txtCedula.getText().toString()+"/";
            final String url=con.urlcompeta("usuariotutoreado",parametro);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<Boolean> response= restTemplate.getForEntity(url, Boolean.class);
            //listaTiempoSensado = Arrays.asList(response.getBody());
            //System.out.println(response);
            ced=response.getBody();
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+ced);
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);

        }
    }

    private class HttpValidacionCedula extends AsyncTask<Void, Void, Void > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String parametro,id;
                id=txtCedula.getText().toString();
                if(!id.isEmpty()){
                    parametro= "validacedula/"+id+"/";
                    final String url=con.urlcompeta("usuariotutoreado",parametro);
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    ResponseEntity<Boolean> response= restTemplate.getForEntity(url, Boolean.class);
                    //listaTiempoSensado = Arrays.asList(response.getBody());
                    //System.out.println(response);
                    ced=response.getBody();
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+ced);
                }else{
                    ced=false;
                }

            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(ced){
                txtCedula.setError("Este Usuario ya fue Registrado");
            }
        }
    }
}