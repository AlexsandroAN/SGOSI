package br.com.dae.sgosi.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.fragments.ClienteFragment;
import br.com.dae.sgosi.fragments.PrincipalFragment;
import br.com.dae.sgosi.fragments.OrdemServicoFragment;
import br.com.dae.sgosi.fragments.TipoServicoFragment;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final int PERMISSION_CONTACT = 100;

    FragmentManager fragmentManager;
    private String tela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // <editor-fold defaultstate="collapsed" desc=">>> Pedir permissão para acessar os contatos">
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        PERMISSION_CONTACT);
            }
        }
        // </editor-fold>


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        Intent intent = getIntent();
        tela = intent.getStringExtra("tela");

        // Configurar objeto para o Fragmento
        if (tela == null) {
            setTitle("Ordens de Serviços");
            fragmentManager.beginTransaction().replace(R.id.content_fragment, new OrdemServicoFragment()).commit();
        } else if (tela.equals("cadastroOrdemServicoActivity")) {
            setTitle("Ordens de Serviços");
            fragmentManager.beginTransaction().replace(R.id.content_fragment, new OrdemServicoFragment()).commit();
        } else if (tela.equals("cadastroTipoServicoActivity")) {
            setTitle("Tipos de Serviços");
            fragmentManager.beginTransaction().replace(R.id.content_fragment, new TipoServicoFragment()).commit();
        } else if (tela.equals("cadastroClienteActivity")) {
            setTitle("Clientes");
            fragmentManager.beginTransaction().replace(R.id.content_fragment, new ClienteFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.nav_principal) {
//            setTitle("Principal");
//            fragmentManager.beginTransaction().replace(R.id.content_fragment, new PrincipalFragment()).commit();
//        } else
        if (id == R.id.nav_ordem_servico) {
            setTitle("Ordens de Serviços");
            fragmentManager.beginTransaction().replace(R.id.content_fragment, new OrdemServicoFragment()).commit();
        } else if (id == R.id.nav_tipo_servico) {
            setTitle("Tipos de Serviços");
            fragmentManager.beginTransaction().replace(R.id.content_fragment, new TipoServicoFragment()).commit();
        } else if (id == R.id.nav_cliente) {
            setTitle("Clientes");
            fragmentManager.beginTransaction().replace(R.id.content_fragment, new ClienteFragment()).commit();
        }
//        else if (id == R.id.nav_compartilhar) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_sair) {
//            // Sair do aplicativo
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_search, menu);
//        return true;
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_search, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView)item.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//
//
//                return false;
//            }
//        });
//        return true;
//    }
}
