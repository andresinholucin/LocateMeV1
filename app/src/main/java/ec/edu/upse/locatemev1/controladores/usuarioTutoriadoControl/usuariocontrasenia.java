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
    EditText txtUsuario;
    EditText txtContraseña;
    EditText txtRepiteContraseña;
    Usuario usuario;
    TipoDiscapacidad tipoDiscapacidadSeleccionada;

    String accion;
    String apellido;
    Integer discapacidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuariocontrasenia);
        anadirElementos();
        validacionesIniciales();
    }

    public void anadirElementos(){
        btnsiguiente=(Button)findViewById(R.id.btn_siguiente);
        txtUsuario =(EditText)findViewById(R.id.txt_usuario);
        txtContraseña =(EditText)findViewById(R.id.txt_pass);
        txtRepiteContraseña =(EditText)findViewById(R.id.txt_passRepetir);
        usuario=getIntent().getParcelableExtra("usuario");
        accion=getIntent().getStringExtra("accion");
        //Toast.makeText(this,accion,Toast.LENGTH_SHORT).show();
        tipoDiscapacidadSeleccionada=getIntent().getParcelableExtra("tipoDiscapacidad");
    }

    public void btn_siguiente(View v){
        if (validaciones()){
            usuario.setUsuUUsuario(txtUsuario.getText().toString());
            usuario.setUsuUClave(txtContraseña.getText().toString());

            Intent intent=new Intent(usuariocontrasenia.this, datospersonales1.class);
            intent.putExtra("usuario", usuario);
            intent.putExtra("tipoDiscapacidad", tipoDiscapacidadSeleccionada);
            startActivity(intent);
        }
    }

    public void validacionesIniciales(){
        if(accion.equals("perfil")){

        }
    }


    public Boolean validaciones(){

        String usuario= txtUsuario.getText().toString();
        String contrasenia= txtContraseña.getText().toString();
        String repite= txtRepiteContraseña.getText().toString();

        if(usuario.isEmpty()){
            txtUsuario.setError("Ingrese Usuario");
            return false;
        }else if(contrasenia.isEmpty()){
            txtContraseña.setError("Ingrese Conteraseña");
            return false;
        }else if(repite.isEmpty()){
            txtRepiteContraseña.setError("RepitaContraseña");
            return false;
        } else if(!txtContraseña.getText().toString().equals(txtRepiteContraseña.getText().toString())){
            Toast toast= Toast.makeText(getApplicationContext(),"Contraseñas Incorrectas", Toast.LENGTH_LONG);
            toast.show();
            return false;
        }else{
            return true;
        }
    }

}
