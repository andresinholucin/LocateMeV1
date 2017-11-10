package ec.edu.upse.locatemev1.controladores.usuarioTutoriadoControl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.modelo.Usuario;

public class perfilUsuarioTutoreado extends AppCompatActivity {
    Usuario usuarioSeleccionado;

    EditText txt_nombreapellido;
    EditText txt_cedula;
    EditText txt_fechaNacimiento;
    EditText txt_tipoDiscapacidad;
    EditText txt_usuario;
    EditText txt_contrasenia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario_tutoreado);

        anadirElementos();
        llenarPerfil();


        //Toast.makeText(this,"usuario "+ usuarioSeleccionado,Toast.LENGTH_LONG).show();

    }

    public void anadirElementos(){
        txt_nombreapellido=(EditText)findViewById(R.id.txt_nombreApellido);
        txt_cedula=(EditText)findViewById(R.id.txt_cedula);
        txt_fechaNacimiento=(EditText)findViewById(R.id.txt_fechaNacimiento);
        txt_tipoDiscapacidad=(EditText)findViewById(R.id.txt_tipoDiscapacidad);
        txt_usuario=(EditText)findViewById(R.id.txt_usuario);
        txt_contrasenia=(EditText)findViewById(R.id.txt_contrasenia);

        usuarioSeleccionado=getIntent().getParcelableExtra("usuario");
    }

    public void llenarPerfil(){
        System.out.println(usuarioSeleccionado);
        txt_nombreapellido.setText(usuarioSeleccionado.getUsuUNombres()+" "+usuarioSeleccionado.getUsuUApellidos());
        txt_cedula.setText(usuarioSeleccionado.getUsuUCedula());
        txt_fechaNacimiento.setText(usuarioSeleccionado.getUsuUDia()+"/"+usuarioSeleccionado.getUsuUMes()+"/"+usuarioSeleccionado.getUsuUAnio());
        txt_tipoDiscapacidad.setText(usuarioSeleccionado.getTipoDiscapacidad().getUsuTipoDescripcion());
        txt_usuario.setText(usuarioSeleccionado.getUsuUUsuario());
        txt_contrasenia.setText(usuarioSeleccionado.getUsuUClave());



    }

}
