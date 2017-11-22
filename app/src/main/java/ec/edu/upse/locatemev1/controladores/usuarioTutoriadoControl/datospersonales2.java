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
    EditText txtCorreo;
    EditText txtDireccion;
    Button btnAceptar;
    Usuario usuario;
    TipoDiscapacidad tipoDiscapacidadSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datospersonales2);
        anadirElementos();
    }

    public void anadirElementos(){
        txtCorreo =(EditText)findViewById(R.id.txt_email);
        txtDireccion =(EditText)findViewById(R.id.txt_direccion);
        btnAceptar=(Button)findViewById(R.id.btn_siguiente);
        usuario=getIntent().getParcelableExtra("usuario");
        tipoDiscapacidadSeleccionada=getIntent().getParcelableExtra("tipoDiscapacidad");
    }

    public void btn_siguiente(View view){
        if(validaciones()){
            usuario.setUsuUCorreo(txtCorreo.getText().toString());
            usuario.setUsuUDireccion(txtDireccion.getText().toString());

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