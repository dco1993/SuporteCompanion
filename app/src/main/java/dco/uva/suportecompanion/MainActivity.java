package dco.uva.suportecompanion;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dco.uva.suportecompanion.data_management.Chamado_DAO;

public class MainActivity extends AppCompatActivity {

    private Chamado_DAO dao;
    RecyclerView listChamados;
    FloatingActionButton addChamado;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new Chamado_DAO(this);

        listChamados = findViewById(R.id.recyclerChamados);
        addChamado = findViewById(R.id.floatingAddChamado);

        addChamado.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(), "Adicionar Chamado", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, ChamadoManager.class);
                startActivity(intent);
            }

        });

        dao.open();
    }
}