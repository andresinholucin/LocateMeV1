package ec.edu.upse.locatemev1.controladores.usuarioTutoriadoControl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.modelo.TipoDiscapacidad;
import ec.edu.upse.locatemev1.modelo.Usuario;

public class usuariocontrasenia extends AppCompatActivity {
    Button btnsiguiente;
    EditText txt_usuario;
    EditText txt_contraseña;
    EditText txt_repitecontraseña;
    Usuario usuario;
    TipoDiscapacidad tipoDiscapacidadSeleccionada;

    String nombre;
    String apellido;
    Integer discapacidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuariocontrasenia);
        anadirElementos();
    }

    public void anadirElementos(){
        btnsiguiente=(Button)findViewById(R.id.btn_siguiente);
        txt_usuario=(EditText)findViewById(R.id.editTextusuario);
        txt_contraseña=(EditText)findViewById(R.id.editTextcontrasenia);
        txt_repitecontraseña=(EditText)findViewById(R.id.editTextrepitecontrasenia);
        usuario=getIntent().getParcelableExtra("usuario");
        tipoDiscapacidadSeleccionada=getIntent().getParcelableExtra("tipoDiscapacidad");
    }

    public void btn_siguiente(View v){
        if (validaciones()){
            usuario.setUsuUUsuario(txt_usuario.getText().toString());
            usuario.setUsuUClave(txt_contraseña.getText().toString());

            Intent intent=new Intent(usuariocontrasenia.this, datospersonales1.class);
            intent.putExtra("usuario", usuario);
            intent.putExtra("tipoDiscapacidad", tipoDiscapacidadSeleccionada);
            startActivity(intent);
        }
    }


    public Boolean validaciones(){

        String usuario= txt_usuario.getText().toString();
        String contrasenia=txt_contraseña.getText().toString();
        String repite=txt_repitecontraseña.getText().toString();

        if(usuario.isEmpty()){
            txt_usuario.setError("Ingrese Usuario");
        }else if(contrasenia.isEmpty()){
            txt_contraseña.setError("Ingrese Conteraseña");
        }else if(repite.isEmpty()){
            txt_repitecontraseña.setError("RepitaContraseña");
        }

        if(txt_contraseña.getText().toString().equals(txt_repitecontraseña.getText().toString())){
            return true;
        }else{
            Toast toast= Toast.makeText(getApplicationContext(),"Contraseñas Incorrectas", Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
    }



}
