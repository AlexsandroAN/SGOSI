package br.com.dae.sgosi.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.fragments.ClienteFragment;
import br.com.dae.sgosi.fragments.OrdemServicoFragment;
import br.com.dae.sgosi.fragments.TipoServicoFragment;

public class MainActivity extends AppCompatActivity {

    private Button buttonCliente, buttonOrdemServico, buttonTipoServico;
    private ClienteFragment clienteFragment;
    private OrdemServicoFragment ordemServicoFragment;
    private TipoServicoFragment tipoServicoFragment;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonCliente = findViewById(R.id.btnCliente);
        buttonOrdemServico = findViewById(R.id.btnOrdemServico);
        buttonTipoServico = findViewById(R.id.btnTipoServico);

        // Remover sombra da ActionBar
        getSupportActionBar().setElevation(0);

        // Configurar objeto para o Fragmento
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameConteudo, new TipoServicoFragment());
        transaction.commit();

        buttonCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameConteudo, new ClienteFragment());
                transaction.commit();
            }
        });

        buttonOrdemServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameConteudo, new OrdemServicoFragment());
                transaction.commit();
            }

        });

        buttonTipoServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameConteudo, new TipoServicoFragment());
                transaction.commit();
            }
        });


       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

//    private void configurarViewPager(ViewPager viewPager) {
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//        viewPagerAdapter.addFragment(new ClienteFragment(), "Clientes");
//        viewPagerAdapter.addFragment(new OrdemServicoFragment(), "Produtos");
//        viewPagerAdapter.addFragment(new TipoServicoFragment(), "Perfil");
//        viewPager.setAdapter(viewPagerAdapter);
//    }

}
