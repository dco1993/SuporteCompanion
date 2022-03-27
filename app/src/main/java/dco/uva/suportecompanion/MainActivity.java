package dco.uva.suportecompanion;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dco.uva.suportecompanion.data_management.Chamado_DAO;
import dco.uva.suportecompanion.data_management.CustomAdapter;
import dco.uva.suportecompanion.model.ChamadoModel;

public class MainActivity extends AppCompatActivity {

    private List<ChamadoModel> chamadosList;
    private Chamado_DAO dao;
    private RecyclerView viewChamados;
    private FloatingActionButton addChamado;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDataAndUpdateList();

        addChamado = findViewById(R.id.floatingAddChamado);

        addChamado.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(), "Adicionar Chamado", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, ChamadoManager.class);
                startActivity(intent);
            }

        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        getDataAndUpdateList();
    }

    void getDataAndUpdateList(){
        viewChamados = findViewById(R.id.recyclerChamados);
        dao = new Chamado_DAO(this);

        dao.open();
        chamadosList = dao.getAll();
        dao.close();

        customAdapter = new CustomAdapter(MainActivity.this, chamadosList);
        viewChamados.setAdapter(customAdapter);
        viewChamados.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }
}

