package ec.edu.upse.locatemev1.controladores.tabsControl;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import ec.edu.upse.locatemev1.configuracion.ParametrosConexion;
import ec.edu.upse.locatemev1.configuracion.VariablesGenerales;
import ec.edu.upse.locatemev1.controladores.usuarioTutoriadoControl.configuracionUsuario;
import ec.edu.upse.locatemev1.controladores.usuarioTutoriadoControl.nombreapellido;
import ec.edu.upse.locatemev1.controladores.usuarioTutoriadoControl.perfilUsuarioTutoreado;
import ec.edu.upse.locatemev1.modelo.Usuario;

public class TabTutoriadosFragment extends Fragment {
    ParametrosConexion conexion = new ParametrosConexion();
    List<Usuario> listaUsuarios= new ArrayList<Usuario>();
    List<String> lst_Usuario= new ArrayList<String>();
    //List<String> lst_UbicacionUsuario = new ArrayList<String>();
    //Long usuarioSelecionado;
    ListView lista;
    ArrayAdapter<String> adaptador ;
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

    /*    if(VariablesGenerales.getIntPeticionTutoriado()==0) {
            new HttpListaTutoreado().execute();

            adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lst_Usuario);
            adaptador.notifyDataSetChanged();
            lista.setAdapter(adaptador);
            VariablesGenerales.setIntPeticionTutoriado(1);
        }
        else
        {

            adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lst_Usuario);
            lista.setAdapter(adaptador);
        }
*/
        lista.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
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
                new HttpListaTutoreado().execute();
            }else{
                for(int i=0 ; i<listaUsuarios.size();i++)
                {
                    System.out.println(listaUsuarios.get(i).getIdusuario()+" "+listaUsuarios.get(i).getUsuUNombres()+" "+listaUsuarios.get(i).getUsuUApellidos());
                    lst_Usuario.add(listaUsuarios.get(i).getUsuUNombres()+" "+listaUsuarios.get(i).getUsuUApellidos());
                }

                adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lst_Usuario);
                adaptador.notifyDataSetChanged();
                lista.setAdapter(adaptador);
            }
    }


    private class HttpListaTutoreado extends AsyncTask<Void, Void, Void > {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                final String url = conexion.urlcompeta("usuario","udt/"+ VariablesGenerales.getLonIdTutor());
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




    @Override
    public void onResume() {
        super.onResume();

        Toast.makeText(getActivity(), "onResume", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getActivity(), "onDestroy", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(getActivity(), "onDestroy", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();

        Toast.makeText(getActivity(), "onStart", Toast.LENGTH_LONG).show();
    }

    /*
    HttpClient recuperaLista = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if(status.isSuccess()){

                    Gson gson = new GsonBuilder().create();
                    try {

                        System.out.println("lista : "+status.getResult());

                        //JSONObject jsono = new JSONObject(status.getResult());
                        //JSONArray jsonarray = jsono.getJSONArray("Usuario");
                        //TypeToken<List<Usuario>> token = new TypeToken<List<Usuario>>() {
                        //};
                        //List<Usuario> usuarios = gson.fromJson(json);
                       // for(int i = 0; i < jsonarray.length(); i++) {
                        //    String strUsu = jsonarray.getString(i);
                        //    Usuario usu = gson.fromJson(strUsu,Usuario.class);
                        //    usuarios.add(usu);
                        //}

                    }catch (Exception e){
                        System.out.println("Fallo! "+e.toString());
                        e.printStackTrace();
                    }

                }
            }
        });
   */
}

