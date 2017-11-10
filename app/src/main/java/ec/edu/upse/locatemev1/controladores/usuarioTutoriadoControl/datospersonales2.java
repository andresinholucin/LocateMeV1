package ec.edu.upse.locatemev1.controladores.usuarioTutoriadoControl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.modelo.TipoDiscapacidad;
import ec.edu.upse.locatemev1.modelo.Usuario;

public class datospersonales2 extends AppCompatActivity {
    EditText txt_correo;
    EditText txt_direccion;
    Button btnaceptar;
    Usuario usuario;
    TipoDiscapacidad tipoDiscapacidadSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datospersonales2);
        anadirElementos();
    }

    public void anadirElementos(){
        txt_correo=(EditText)findViewById(R.id.editTextcorreo);
        txt_direccion=(EditText)findViewById(R.id.editTextdireccion);
        btnaceptar=(Button)findViewById(R.id.btn_aceptar);
        usuario=getIntent().getParcelableExtra("usuario");
        tipoDiscapacidadSeleccionada=getIntent().getParcelableExtra("tipoDiscapacidad");
    }

    public void btn_aceptar(View view){
        if(validaciones()){
            usuario.setUsuUCorreo(txt_correo.getText().toString());
            usuario.setUsuUDireccion(txt_direccion.getText().toString());

            Intent intent=new Intent(datospersonales2.this, configuracionUsuario.class);
            intent.putExtra("usuario", usuario);
            intent.putExtra("tipoDiscapacidad", tipoDiscapacidadSeleccionada);
            startActivity(intent);
        }
    }

    public boolean validaciones(){
        return true;
    }
}