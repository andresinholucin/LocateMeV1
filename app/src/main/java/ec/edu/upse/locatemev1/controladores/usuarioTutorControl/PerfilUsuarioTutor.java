package ec.edu.upse.locatemev1.controladores.usuarioTutorControl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.configuracion.ParametrosConexion;
import ec.edu.upse.locatemev1.configuracion.VariablesGenerales;
import ec.edu.upse.locatemev1.modelo.Usuario;

public class PerfilUsuarioTutor extends AppCompatActivity {
    Usuario perfilTutor;
    List<Usuario> listaTutor= new ArrayList<Usuario>();
    ParametrosConexion conexion;

    EditText txt_nombre;
    EditText txt_apellido;
    EditText txt_cedula;
    EditText txt_fechaNacimiento;
    EditText txt_telefono;
    EditText txt_direccion;
    EditText txt_correo;
    EditText txt_usuario;
    EditText txt_contrasenia;
    Button btnEditarTutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario_turor);

        inicializar();
        //desabilitar();
        llenarPerfil();
    }
    public void desabilitar(){
        txt_nombre.setEnabled(false);
        txt_nombre.setTextColor(getResources().getColor(R.color.colorAccent));
        txt_apellido.setEnabled(false);
        txt_apellido.setTextColor(getResources().getColor(R.color.colorAccent));
        txt_cedula.setEnabled(false);
        txt_cedula.setTextColor(getResources().getColor(R.color.colorAccent));
        txt_fechaNacimiento.setEnabled(false);
        txt_fechaNacimiento.setTextColor(getResources().getColor(R.color.colorAccent));
        txt_telefono.setEnabled(false);
        txt_telefono.setTextColor(getResources().getColor(R.color.colorAccent));
        txt_direccion.setEnabled(false);
        txt_direccion.setTextColor(getResources().getColor(R.color.colorAccent));
        txt_correo.setEnabled(false);
        txt_correo.setTextColor(getResources().getColor(R.color.colorAccent));
        txt_usuario.setEnabled(false);
        txt_usuario.setTextColor(getResources().getColor(R.color.colorAccent));
        txt_contrasenia.setEnabled(false);
        txt_contrasenia.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    public void inicializar(){
        btnEditarTutor= (Button) findViewById(R.id.btn_editar);
        txt_nombre=(EditText)findViewById(R.id.txt_nombresPerfilTutor);
        txt_apellido=(EditText)findViewById(R.id.txt_apellidosPerfilTutor);
        txt_cedula=(EditText)findViewById(R.id.txt_cedula);
        txt_fechaNacimiento=(EditText)findViewById(R.id.txt_fechaNacimientoPerfil);
        txt_telefono=(EditText)findViewById(R.id.txt_telefonoPerfilTutor);
        txt_direccion=(EditText)findViewById(R.id.txt_direccionPerfilTutor);
        txt_correo=(EditText)findViewById(R.id.txt_emailPerfilTutor);
        txt_usuario=(EditText)findViewById(R.id.txt_usuarioPerfilTutor);
        txt_contrasenia=(EditText)findViewById(R.id.txt_passPerfilTutor);

        perfilTutor=getIntent().getParcelableExtra("usuario");
    }

    public void llenarPerfil() {
        System.out.println("Tutor seleccionado");
        txt_nombre.setText("Denny");
        txt_apellido.setText("Baquerizo Santiana");
        txt_cedula.setText("2400162141");
        txt_fechaNacimiento.setText("16"+ "/" + "11" + "/" + "2017");
        txt_telefono.setText("0991614693");
        txt_direccion.setText("Comuna Barcelona");
        txt_correo.setText("dennybaquerizo@hotmail.com");
        txt_usuario.setText("dengi");
        txt_contrasenia.setText("dengi");
    }


    public void editarPerfilTutor(View v){
        btnEditarTutor.setText("Guardar");
        txt_nombre.setEnabled(true);
        txt_apellido.setEnabled(true);
        txt_cedula.setEnabled(true);
        txt_fechaNacimiento.setEnabled(true);
        txt_telefono.setEnabled(true);
        txt_direccion.setEnabled(true);
        txt_correo.setEnabled(true);
        txt_usuario.setEnabled(true);
        txt_contrasenia.setEnabled(true);

    }


    private class HttpPerfilTutor extends AsyncTask<Void, Void, Void > {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                final String url = conexion.urlcompeta("usuario","udt/"+ VariablesGenerales.getLonIdTutor());
                Usuario usuario = new Usuario();
                RestTemplate restTemplate = new RestTemplate();

                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                ResponseEntity<Usuario> response= restTemplate.getForEntity(url, Usuario.class);

                listaTutor = Arrays.asList(response.getBody());
                VariablesGenerales.setListTutor(listaTutor);
                System.out.println(usuario.getIdusuario()+" "+usuario.getUsuUNombres()+" "+usuario.getUsuUApellidos());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }
    /*
    public void llenarPerfil() {
        System.out.println(perfilTutor);
        txt_nombre.setText(perfilTutor.getUsuUNombres());
        txt_nombre.setText(perfilTutor.getUsuUApellidos());
        txt_cedula.setText(perfilTutor.getUsuUCedula());
        txt_fechaNacimiento.setText(perfilTutor.getUsuUDia() + "/" + perfilTutor.getUsuUMes() + "/" + perfilTutor.getUsuUAnio());
        txt_telefono.setText(perfilTutor.getUsuUTelefono());
        txt_direccion.setText(perfilTutor.getUsuUDireccion());
        txt_correo.setText(perfilTutor.getUsuUCorreo());
        txt_usuario.setText(perfilTutor.getUsuUUsuario());
        txt_contrasenia.setText(perfilTutor.getUsuUClave());
    }*/

    public void onStart(){
        super.onStart();
    }
     /*Button btnsiguiente = (Button) findViewById(R.id.btnsiguiente);
        btnsiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrarDatosTutor.this, RegistrarDatosTutor2.class);
                startActivity(intent);
            }
        });*/

}
