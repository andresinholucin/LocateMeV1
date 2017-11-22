package ec.edu.upse.locatemev1.controladores.usuarioTutorControl;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.modelo.Usuario;

public class RegistrarDatosTutor2 extends AppCompatActivity {
    EditText txtTelefono;
    EditText txtFechaNacimiento;
    int anio,mes,dia;
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_datos_tutor2);

        txtFechaNacimiento=(EditText) findViewById(R.id.txt_fechaNacimiento);
        txtTelefono=(EditText)findViewById(R.id.txt_telefono);
        usuario=getIntent().getParcelableExtra("Usuario");
        calendario();
    }

    public void onStart(){
        super.onStart();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    //pasar de una pantalla a otra
    public void irInterface2(View v) {

        if ((txtFechaNacimiento.length()!=0 && (txtTelefono.length()!=0)) ){
            usuario.setUsuUAnio(String.valueOf(anio));
            usuario.setUsuUMes(String.valueOf(mes));
            usuario.setUsuUDia(String.valueOf(dia));
            usuario.setUsuUTelefono(txtTelefono.getText().toString());

            Toast toast = Toast.makeText(this, usuario.getUsuUNombres()+" "+usuario.getUsuUApellidos()+" "+usuario.getUsuUCedula()+" "+usuario.getUsuUDia()+" "+usuario.getUsuUMes()+" "+usuario.getUsuUAnio()+" "+usuario.getUsuUTelefono(), Toast.LENGTH_LONG);
            toast.show();
            Intent intent=new Intent(RegistrarDatosTutor2.this, RegistrarDatosTutor3.class);
            intent.putExtra("Usuario", usuario);
            startActivity(intent);
        }
        else if (txtFechaNacimiento.length()==0){
            Toast.makeText(getApplicationContext(), "Seleccione la fecha de nacimiento" , Toast.LENGTH_SHORT).show();
            onRestart();
        }
        else if (txtTelefono.length()==0){
            Toast.makeText(getApplicationContext(), "Escriba su número de teléfono" , Toast.LENGTH_SHORT).show();
            onRestart();
        }
    }

    public void irInterfaz(View view){
        txtFechaNacimiento.setText("21/12/2016");
        txtTelefono.setText("0991614693");
        if (txtFechaNacimiento.length()!=0) {
            Toast.makeText(getApplicationContext(), "Fecha de nacimiento",
                    Toast.LENGTH_SHORT).show();

            if (txtTelefono.length()!=0) {
                Toast.makeText(getApplicationContext(), "Teléfono",
                        Toast.LENGTH_SHORT).show();

                usuario.setUsuUAnio(String.valueOf(anio));
                usuario.setUsuUMes(String.valueOf(mes));
                usuario.setUsuUDia(String.valueOf(dia));
                usuario.setUsuUTelefono(txtTelefono.getText().toString());

                Toast toast = Toast.makeText(this, usuario.getUsuUNombres()+" "+usuario.getUsuUApellidos()+" "+usuario.getUsuUCedula()+" "+usuario.getUsuUDia()+" "+usuario.getUsuUMes()+" "+usuario.getUsuUAnio()+" "+usuario.getUsuUTelefono(), Toast.LENGTH_LONG);
                toast.show();
                Intent intent=new Intent(RegistrarDatosTutor2.this, RegistrarDatosTutor3.class);
                intent.putExtra("Usuario", usuario);
                startActivity(intent);

            } else {
                txtTelefono.setError("Ingrese su número de teléfono ");
                onRestart();
            }
        } else {
            txtFechaNacimiento.setError("Seleccione su fecha de nacimiento"); // show error
            onRestart();
        }
    }

    Calendar objCalendario = Calendar.getInstance();   //una vez que selecciona aceptar en la fecha, se guarda en el objeto calendario

    DatePickerDialog.OnDateSetListener fecha = new DatePickerDialog.OnDateSetListener() {    //DatePickerDialog la imagen del calendario
        @Override
        public void onDateSet(DatePicker view, int year, int mesDelAnio, int diaDelMes) {
            // TODO Auto-generated method stub
            objCalendario.set(Calendar.YEAR, year);
            objCalendario.set(Calendar.MONTH, mesDelAnio);
            objCalendario.set(Calendar.DAY_OF_MONTH, diaDelMes);
            anio=year;
            mes=mesDelAnio+1;
            dia=diaDelMes;
            actualizarFormato();
        }

    };

    public void calendario(){//metodo para obtener los datos de la fecha en la clase actual al dar clic
            txtFechaNacimiento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(RegistrarDatosTutor2.this, fecha, objCalendario.get(Calendar.YEAR), objCalendario.get(Calendar.MONTH),
                            objCalendario.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
    }

    private void actualizarFormato() {
        String formatoFecha = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha, Locale.ENGLISH);
        //Asignar Formato simple de la fecha que se obtiene
        txtFechaNacimiento.setText(sdf.format(objCalendario.getTime()));
    }
}