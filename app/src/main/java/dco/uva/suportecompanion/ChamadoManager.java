package dco.uva.suportecompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ChamadoManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamado_manager);
        setTitle("Novo Chamado");
    }
}