package ec.edu.upse.locatemev1.controladores.usuarioTutoriadoControl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.configuracion.MetodosGenerales;
import ec.edu.upse.locatemev1.controladores.principal.MainActivity;
import ec.edu.upse.locatemev1.controladores.tabsControl.MenuActivity;
import ec.edu.upse.locatemev1.controladores.tabsControl.MenuFragment;
import ec.edu.upse.locatemev1.modelo.Usuario;

public class perfilUsuarioTutoreado extends AppCompatActivity {
    Usuario usuarioSeleccionado;

    EditText txtNombre;
    EditText txtApellido;
    EditText txtCedula;
    EditText txtFechaNacimiento;
    EditText txtTipoDiscapacidad;
    EditText txtUsuario;
    EditText txtContrasenia;

    Button btnDatosAcceso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario_tutoreado);
        anadirElementos();

        try {
            llenarPerfil();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Toast.makeText(this,"usuario "+ usuarioSeleccionado,Toast.LENGTH_LONG).show();

    }

    public void anadirElementos(){
        txtNombre =(EditText)findViewById(R.id.txt_nombresPerfilUsuario);
        txtApellido =(EditText)findViewById(R.id.txt_apellidosPerfilUsuario);
        txtCedula =(EditText)findViewById(R.id.txt_cedula);
        txtFechaNacimiento =(EditText)findViewById(R.id.txt_fechaNacimientoPerfil);
        txtTipoDiscapacidad =(EditText)findViewById(R.id.txt_tipoDiscapacidad);
        txtUsuario =(EditText)findViewById(R.id.txt_usuarioPerfil);
        txtContrasenia =(EditText)findViewById(R.id.txt_passPerfilUsuario);
        usuarioSeleccionado=getIntent().getParcelableExtra("usuario");

        btnDatosAcceso=(Button)findViewById(R.id.btn_editarDatosAcceso);
    }

    public void llenarPerfil() throws Exception {
        System.out.println(usuarioSeleccionado);
        txtNombre.setText(usuarioSeleccionado.getUsuUNombres());
        txtApellido.setText(usuarioSeleccionado.getUsuUApellidos());
        txtCedula.setText(usuarioSeleccionado.getUsuUCedula());
        txtFechaNacimiento.setText(usuarioSeleccionado.getUsuUDia()+"/"+usuarioSeleccionado.getUsuUMes()+"/"+usuarioSeleccionado.getUsuUAnio());
        txtTipoDiscapacidad.setText(usuarioSeleccionado.getTipoDiscapacidad().getUsuTipoDescripcion());
        txtUsuario.setText(usuarioSeleccionado.getUsuUUsuario());

        String clave= usuarioSeleccionado.getUsuUClave();

        txtContrasenia.setText(clave);
    }

    //Editar los Datos de acceso del tutoreado desde la vista del perfil...
    public void editarDatosAccesoTutoreado(View view){

        //Toast.makeText(this,"vas bien",Toast.LENGTH_SHORT).show();
        Intent intent =new Intent(getApplication(),usuariocontrasenia.class);
        intent.putExtra("usuario", usuarioSeleccionado);
        intent.putExtra("accion","perfil");
        startActivity(intent);
        finish();

    }

    public void editarDatosGeneralesTutoreado(View view){

        //Toast.makeText(this,"vas bien",Toast.LENGTH_SHORT).show();
        Intent intent =new Intent(getApplication(),nombreapellido.class);
        intent.putExtra("usuario", usuarioSeleccionado);
        intent.putExtra("accion","perfil");
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //retornar a la pantalla principal
/*
        Intent intent = new Intent(getApplication(),MenuActivity.class);
        startActivity(intent);
        finish();*/
    }
}
