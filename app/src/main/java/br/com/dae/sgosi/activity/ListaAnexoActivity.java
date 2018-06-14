package br.com.dae.sgosi.activity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;
import br.com.dae.sgosi.R;
import br.com.dae.sgosi.adapter.AdapterAnexo;
import br.com.dae.sgosi.adapter.RecyclerItemClickListener;
import br.com.dae.sgosi.dao.AnexoDAO;
import br.com.dae.sgosi.entidade.Anexo;
import br.com.dae.sgosi.entidade.OrdemServico;
import br.com.dae.sgosi.helper.Permissao;

public class ListaAnexoActivity extends AppCompatActivity {

    private ArrayList<Anexo> listViewAnexo;
    private AnexoDAO anexoDAO;
    private Anexo anexo;
    private String imagePath;
    private RecyclerView recyclerView;
    private ListView listaViewAnexo;
    private OrdemServico ordemServico;
    private Uri localImagemSelecionada;
    private AdapterAnexo adapterAnexo;
    private static final int SELECAO_CAMERA = 100;
    private static final int SELECAO_GALERIA = 200;
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_adapter_os);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Lista de Anexos");

        //Validar permissões
        Permissao.validarPermissoes(permissoesNecessarias, this, 1);

        Intent intent = getIntent();
        ordemServico = (OrdemServico) intent.getSerializableExtra("os");
        anexoDAO = new AnexoDAO(ListaAnexoActivity.this);

        listaViewAnexo = (ListView) findViewById(R.id.listViewAnexo);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewOS);

        carregarAnexo();

        //Configurar Recycleview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        //evento de click
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                        getApplicationContext(), recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //  Toast.makeText(getApplicationContext(), "Clik curto.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                anexoDAO = new AnexoDAO(getApplicationContext());
                                Anexo anexoExcluir = listViewAnexo.get(position);

                                anexoDAO.deletarAnexo(anexoExcluir);
                                Toast.makeText(getApplicationContext(), "Anexo deletado com sucesso!", Toast.LENGTH_LONG).show();
                                carregarAnexo();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            }
                        }
                )
        );
    }

    public void carregarAnexo() {
        listViewAnexo = anexoDAO.consultarAnexoPorOrdemServico(ordemServico.getId());

        if (listViewAnexo != null) {
            adapterAnexo = new AdapterAnexo(listViewAnexo);
            recyclerView.setAdapter(adapterAnexo);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bitmap imagem = null;
            try {
                switch (requestCode) {
                    case SELECAO_CAMERA:
                        imagem = (Bitmap) data.getExtras().get("data");
                        break;
                    case SELECAO_GALERIA:
                        localImagemSelecionada = data.getData();
                        imagePath = getImagePath(localImagemSelecionada);
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);
                        break;
                }

                if (imagem != null) {
                    salvarAnexo();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getImagePath(Uri contentUri) {
        String[] campos = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, campos, null, null, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }


    public void salvarAnexo() {
        anexo = new Anexo();

        anexo.setOs(ordemServico);
        anexo.setData(new Date());
        anexo.setUriFoto(imagePath);

        anexoDAO.salvarAnexo(anexo);
        carregarAnexo();
        Toast.makeText(this, "Anexo adicionado com sucesso!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_anexo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                intent = new Intent(ListaAnexoActivity.this, CadastroOrdemServicoActivity.class);
                intent.putExtra("ordem_servico-escolhido", ordemServico);
                startActivity(intent);
                return true;
            case R.id.menu_anexo:
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(i, SELECAO_GALERIA);
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}