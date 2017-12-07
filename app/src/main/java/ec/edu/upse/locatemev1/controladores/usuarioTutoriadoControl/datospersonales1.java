package ec.edu.upse.locatemev1.controladores.usuarioTutoriadoControl;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
    String accion;

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
                    if (accion==null){
                        validaCedula();
                    }

                }
            }
        });

        calendario();
        validacionesIniciales();
    }

    private void validacionesIniciales() {
        if (accion==null){

        }else if(accion.equals("perfil")){
            btnsiguiente.setText("Actualizar Datos");
            txtCedula.setText(usuario.getUsuUCedula());
            String fecha=usuario.getUsuUDia()+" / "+usuario.getUsuUMes()+" / "+usuario.getUsuUAnio();
            txtFecha.setText(fecha);
        }

    }


    public void anadirElementos(){
        txtFecha =(EditText)findViewById(R.id.txt_fechaNacimiento);
        txtCedula =(EditText)findViewById(R.id.txt_cedula);
        //txt_telefono=(EditText)findViewById(R.id.editTexttelefono);
        btnsiguiente=(Button)findViewById(R.id.btn_siguiente);
        usuario=getIntent().getParcelableExtra("usuario");
        accion=getIntent().getStringExtra("accion");
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


            if(btnsiguiente.getText()=="SIGUIENTE"){
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


            }else if(btnsiguiente.getText()=="ACTUALIZAR DATOS"){
                usuario.setUsuUCedula(txtCedula.getText().toString());
                //usuario.setUsuUTelefono(txt_telefono.getText().toString());
                usuario.setUsuUAnio(String.valueOf(anio));
                usuario.setUsuUMes(String.valueOf(mes));
                usuario.setUsuUDia(String.valueOf(dia));
                new HttpEnviaPostUsuario().execute();

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

    private class HttpEnviaPostUsuario extends AsyncTask<Void, Void, Usuario > {
        AlertDialog.Builder builder;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            builder = new AlertDialog.Builder(datospersonales1.this);
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
                builder.setMessage("Usuario No se Actualizo");
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