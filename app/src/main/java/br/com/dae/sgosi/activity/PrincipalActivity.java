package br.com.dae.sgosi.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.config.ConfiguracaoFirebase;
import br.com.dae.sgosi.entidade.StatusOrdemServico;
import br.com.dae.sgosi.fragments.RelatorioFragment;
import br.com.dae.sgosi.helper.DbHelper;
import br.com.dae.sgosi.fragments.ClienteFragment;
import br.com.dae.sgosi.fragments.PrincipalFragment;
import br.com.dae.sgosi.fragments.OrdemServicoFragment;
import br.com.dae.sgosi.fragments.TipoServicoFragment;
import br.com.dae.sgosi.helper.UsuarioFirebase;
import de.hdodenhof.circleimageview.CircleImageView;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final int PERMISSION_CONTACT = 100;
    private Toolbar toolbar;
    private FirebaseAuth usuarioFirebase;
    private ViewPager viewPager;
    private String identificadorContato;
    private DatabaseReference firebase;
    private FragmentManager fragmentManager;
    private String tela;
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    private CircleImageView circleImageViewPerfil;
    private TextView textNomeUsuario, textEmailUsuario;
    private ImageView imagemUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();

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

        DbHelper db = new DbHelper(getApplicationContext());
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Mudar cabeçalho do NavigationView
        View header=navigationView.getHeaderView(0);
        textNomeUsuario = (TextView)header.findViewById(R.id.textNomeUsuario);
        textEmailUsuario = (TextView)header.findViewById(R.id.textEmailUsuario);
        circleImageViewPerfil = (CircleImageView) header.findViewById(R.id.circleImageViewFotoPerfil);

        textNomeUsuario.setText(usuarioFirebase.getCurrentUser().getDisplayName());
        textEmailUsuario.setText(usuarioFirebase.getCurrentUser().getEmail());

        usuarioFirebase.getCurrentUser().getPhotoUrl();

        Uri url =  usuarioFirebase.getCurrentUser().getPhotoUrl();

        if ( url != null ){
            Glide.with(PrincipalActivity.this)
                    .load( url )
                    .into( circleImageViewPerfil );
        }else {
            circleImageViewPerfil.setImageResource(R.drawable.padrao);
        }


        fragmentManager = getSupportFragmentManager();
        Intent intent = getIntent();
        tela = intent.getStringExtra("tela");
        //String usuario_email = intent.getStringExtra("usuario_email");

        // Configurar objeto para o Fragmento
/*        if (tela == null) {
            setTitle("Principal");
            fragmentManager.beginTransaction().replace(R.id.content_fragment, new PrincipalFragment()).commit();
        } else if (tela.equals("cadastroOrdemServicoActivity")) {*/

        if (tela == null || tela.equals("cadastroOrdemServicoActivity")) {
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
        } else if (tela.equals("RelatorioFragment")) {
            setTitle("Consulta de OS");
            fragmentManager.beginTransaction().replace(R.id.content_fragment, new RelatorioFragment()).commit();
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
//        } else if (id == R.id.nav_ordem_servico) {
        if (id == R.id.nav_ordem_servico) {
            setTitle("Ordens de Serviços");
            fragmentManager.beginTransaction().replace(R.id.content_fragment, new OrdemServicoFragment()).commit();
        } else if (id == R.id.nav_tipo_servico) {
            setTitle("Tipos de Serviços");
            fragmentManager.beginTransaction().replace(R.id.content_fragment, new TipoServicoFragment()).commit();
        } else if (id == R.id.nav_cliente) {
            setTitle("Clientes");
            fragmentManager.beginTransaction().replace(R.id.content_fragment, new ClienteFragment()).commit();
        } else if (id == R.id.nav_consulta) {
            setTitle("Consulta de OS");
            fragmentManager.beginTransaction().replace(R.id.content_fragment, new RelatorioFragment()).commit();
        }
//        else if (id == R.id.nav_compartilhar) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_logout:
                deslogarUsuario();
                return true;
            case R.id.item_sair:
                finishAffinity();
                return true;
            case R.id.item_configuracoes:
                abrirConfiguracoes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void abrirConfiguracoes() {
        Intent intent = new Intent(PrincipalActivity.this, ConfiguracoesActivity.class);
        startActivity(intent);
    }

    private void deslogarUsuario() {
        usuarioFirebase.signOut();
        Intent intent = new Intent(PrincipalActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
