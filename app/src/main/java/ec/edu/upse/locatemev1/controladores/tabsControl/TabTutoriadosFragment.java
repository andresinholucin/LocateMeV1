package ec.edu.upse.locatemev1.controladores.tabsControl;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.configuracion.MetodosGenerales;
import ec.edu.upse.locatemev1.configuracion.ParametrosConexion;
import ec.edu.upse.locatemev1.configuracion.VariablesGenerales;
import ec.edu.upse.locatemev1.controladores.usuarioTutoriadoControl.configuracionUsuario;
import ec.edu.upse.locatemev1.controladores.usuarioTutoriadoControl.nombreapellido;
import ec.edu.upse.locatemev1.controladores.usuarioTutoriadoControl.perfilUsuarioTutoreado;
import ec.edu.upse.locatemev1.modelo.Perimetro;
import ec.edu.upse.locatemev1.modelo.TiempoSensado;
import ec.edu.upse.locatemev1.modelo.TipoDiscapacidad;
import ec.edu.upse.locatemev1.modelo.Usuario;

public class TabTutoriadosFragment extends Fragment {
    ParametrosConexion conexion = new ParametrosConexion();
    List<Usuario> listaUsuarios= new ArrayList<Usuario>();
    List<String> lst_Usuario= new ArrayList<String>();
    //List<String> lst_UbicacionUsuario = new ArrayList<String>();
    //Long usuarioSelecionado;
    ListView lista;
    ArrayAdapter<String> adaptador=null ;
    Usuario usuarioSeleccionado;
    FloatingActionButton floatingActionButton;
    //ArrayAdapter<Usuario> adaptador ;

    private SwipeRefreshLayout swipeRefreshLayout;

    VariablesGenerales variablesGenerales;
    List<Usuario> usuarioportutor;

    public TabTutoriadosFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_tutoriados, container, false);
        //System.out.println("llega aki 1");
        lista = (ListView) view.findViewById(R.id.listaTutoriado);
        floatingActionButton=(FloatingActionButton)view.findViewById(R.id.flotingaction);
        System.out.println("llega con: "+ VariablesGenerales.getIntPeticionTutoriado());
        variablesGenerales = ((VariablesGenerales)getActivity().getApplicationContext());

        validacionesIniciales();

        //boton flotante para añadir agregar nuevos usuarios
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getContext(),nombreapellido.class);
                startActivity(i);
            }
        });


       /* lista.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });*/
        //seleccionar un usuario y llamar a menu emergente
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(parent.getItemAtPosition(position));

                String usu;
                for(Usuario u:listaUsuarios ){
                    usu=u.getUsuUNombres()+" "+u.getUsuUApellidos();
                    //System.out.println(usu);
                    if(usu.equals(parent.getItemAtPosition(position))){
                        usuarioSeleccionado=u;
                        System.out.println("ENCONTRASTE IGUAL");
                    }
                }
            }
        });

        registerForContextMenu(lista);

        return  view;
    }


    public void validacionesIniciales(){
            usuarioportutor= variablesGenerales.getListaUsuariosPorTutor();

            if(usuarioportutor==null){
                Toast.makeText(getActivity(),"llenaste lista por primera vez", Toast.LENGTH_SHORT).show();
                new HttpListaTutoreado().execute();
                new HttpUsuarioTutor().execute();
                new HttpListaTipoDiscapacidad().execute();
                new HttpListaTiempos().execute();
                new HttpListaPerimetros().execute();
            }else{
                Toast.makeText(getActivity(),"llenar de variables generales", Toast.LENGTH_SHORT).show();
                for(int i=0 ; i<usuarioportutor.size();i++)
                {
                    System.out.println(usuarioportutor.get(i).getIdusuario()+" "+usuarioportutor.get(i).getUsuUNombres()+" "+usuarioportutor.get(i).getUsuUApellidos());
                    lst_Usuario.add(usuarioportutor.get(i).getUsuUNombres()+" "+usuarioportutor.get(i).getUsuUApellidos());
                }
                adaptador.clear();
                adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lst_Usuario);
                adaptador.notifyDataSetChanged();
                lista.setAdapter(adaptador);
            }
    }

    private class HttpListaTutoreado extends AsyncTask<Void, Void, Void > {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                final String url = conexion.urlcompeta("usuariotutoreado","listausuarioasignado/"+ VariablesGenerales.getLonIdTutor());
                Usuario usuario = new Usuario();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<Usuario[]> response= restTemplate.getForEntity(url, Usuario[].class);
                listaUsuarios = Arrays.asList(response.getBody());
                VariablesGenerales.setListUsuario(listaUsuarios);
                for(int i=0 ; i<listaUsuarios.size();i++)
                {
                    System.out.println(listaUsuarios.get(i).getIdusuario()+" "+listaUsuarios.get(i).getUsuUNombres()+" "+listaUsuarios.get(i).getUsuUApellidos());
                    lst_Usuario.add(listaUsuarios.get(i).getUsuUNombres()+" "+listaUsuarios.get(i).getUsuUApellidos());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            variablesGenerales.setListaUsuariosPorTutor(listaUsuarios);
            adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lst_Usuario);
            adaptador.notifyDataSetChanged();
            lista.setAdapter(adaptador);
        }
    }

    private class HttpUsuarioTutor extends AsyncTask<Void,Void,Usuario>{
        @Override
        protected Usuario doInBackground(Void... params) {
            try {
                Usuario usuarioTutor;
                final String url=conexion.urlcompeta("usuariotutoreado","/"+ VariablesGenerales.getLonIdTutor());
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<Usuario> response= restTemplate.getForEntity(url, Usuario.class);
                usuarioTutor = response.getBody();
                System.out.println(usuarioTutor);
                return usuarioTutor;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                return null;
            }

        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            super.onPostExecute(usuario);
            VariablesGenerales.setUsuarioTutor(usuario);
        }
    }

    private class HttpListaTipoDiscapacidad extends  AsyncTask<Void,Void,Void>{
        List<TipoDiscapacidad> listaTipoDiscapacidad;
        @Override
        protected Void doInBackground(Void... params) {
            try {
                final String url=conexion.urlcompeta("usuariotutoreado","tiposdiscapacidad/");
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<TipoDiscapacidad[]> response= restTemplate.getForEntity(url, TipoDiscapacidad[].class);
                listaTipoDiscapacidad = Arrays.asList(response.getBody());
                return null;
            }catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                return null;
            }
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            variablesGenerales.setListaTipoDiscapacidad(listaTipoDiscapacidad);
        }
    }

    private class HttpListaTiempos extends AsyncTask<Void,Void,Void>{
        List<TiempoSensado> listaTiempoSensado;
        @Override
        protected Void doInBackground(Void... params) {
            try {
                final String url=conexion.urlcompeta("usuariotutoreado","tiempos/");
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<TiempoSensado[]> response= restTemplate.getForEntity(url, TiempoSensado[].class);
                listaTiempoSensado = Arrays.asList(response.getBody());
                return null;
            }catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            variablesGenerales.setListaTiempoSensado(listaTiempoSensado);
        }
    }

    private class HttpListaPerimetros extends AsyncTask<Void, Void, Void>{
        List<Perimetro> listaPerimetro;
        @Override
        protected Void doInBackground(Void... params) {
            try {
                final String url= conexion.urlcompeta("usuariotutoreado","perimetros/");
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<Perimetro[]> response= restTemplate.getForEntity(url, Perimetro[].class);
                listaPerimetro = Arrays.asList(response.getBody());
                return null;
            } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
            return null;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            variablesGenerales.setListaPerimetro(listaPerimetro);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,v.getId(),0,"Perfil");
        menu.add(1,v.getId(),1,"Configuraciones");
        menu.add(2,v.getId(),2,"Ubicar en el Mapa");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        //seleccionar un usuario para enviar a otra accion
        String usu;
        for(Usuario u:listaUsuarios ){
            usu=u.getUsuUNombres()+" "+u.getUsuUApellidos();
            //System.out.println(usu);
            if(usu.equals(lista.getItemAtPosition(info.position))){
                usuarioSeleccionado=u;
            }
        }

         if(item.getTitle()=="Perfil"){
             Intent intent=new Intent(getContext(), perfilUsuarioTutoreado.class);
             intent.putExtra("usuario", usuarioSeleccionado);
             startActivity(intent);
            //Toast.makeText(getActivity(),"Selecciono Perfil",Toast.LENGTH_SHORT).show();
        }else if (item.getTitle()=="Configuraciones") {
             Intent intentConfiguraciones=new Intent(getContext(), configuracionUsuario.class);
             intentConfiguraciones.putExtra("usuario", usuarioSeleccionado);
             intentConfiguraciones.putExtra("accion", "menuconfigurar");
             startActivity(intentConfiguraciones);
            //Toast.makeText(getActivity(), "Selecciono Configuraciones", Toast.LENGTH_SHORT).show();
        }else if (item.getTitle()=="Ubicar en el Mapa"){
             Toast.makeText(getActivity(), "Selecciono Ubicar en el Mapa", Toast.LENGTH_SHORT).show();
             //VariablesGenerales.setLonIdTutoriadosLista(listaUsuarios.get(i).getIdusuario());
        }

        return true;
    }

}

