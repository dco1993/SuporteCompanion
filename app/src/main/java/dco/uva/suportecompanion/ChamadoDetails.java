package dco.uva.suportecompanion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.format.DateTimeFormatter;

import dco.uva.suportecompanion.data_management.Chamado_DAO;
import dco.uva.suportecompanion.model.ChamadoModel;
import androidx.appcompat.app.AlertDialog;

public class ChamadoDetails extends AppCompatActivity {

    static final int ACTIVITY_UPDATE = 2;
    private Chamado_DAO dao;
    private TextView detailsSolicitante, detailsData, detailsTime,
            detailsDuracao, detailsObservacoes, detailsResolvido;
    private ImageView detailsStatus;
    private Button editarChamado, excluirChamado;
    private ChamadoModel chamado;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamado_details);

        dao = new Chamado_DAO(this);
        dao.open();

        detailsSolicitante = findViewById(R.id.txvDetailsSolicitante);
        detailsData = findViewById(R.id.txvDetailsData);
        detailsTime = findViewById(R.id.txvDetailsTime);
        detailsDuracao = findViewById(R.id.txvDetailsDuracao);
        detailsObservacoes = findViewById(R.id.txvDetailsObservacoes);
        detailsResolvido = findViewById(R.id.txvDetailsResolvido);
        detailsStatus = findViewById(R.id.imgDetailsBadge);
        editarChamado = findViewById(R.id.btnDetailsEditar);
        excluirChamado = findViewById(R.id.btnDetailsExcluir);

        id = getIntent().getLongExtra("id", 0);

        getAndSetData(id);

        editarChamado.setOnClickListener((view) -> {
            Intent intent = new Intent(ChamadoDetails.this, ChamadoManager.class);
            intent.putExtra("id", chamado.getId());
            startActivity(intent);
        });

        excluirChamado.setOnClickListener((view) -> {

            AlertDialog.Builder adialog = new AlertDialog.Builder(this);

            adialog.setTitle("Exclusão de chamado!");

            adialog.setMessage("Confirma a exclusão do chamado?\n"
                              + "Não será possível desfazer essa ação!");

            adialog.setPositiveButton("Excluir", (dialog, which) -> {
                try{
                    dao.delete(chamado.getId());
                    Toast.makeText(ChamadoDetails.this, "Chamado excluido!", Toast.LENGTH_LONG).show();
                    finish();

                } catch (Exception e){
                    Toast.makeText(ChamadoDetails.this, "Houve um erro, chamado não excluido!", Toast.LENGTH_LONG).show();
                }
            });

            /*adialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    deleteChamado();
                }
            });*/

            adialog.setNegativeButton("Cancelar", (dialog, which) -> {

            });

            adialog.create();
            adialog.show();
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        getAndSetData(id);
    }

    private void getAndSetData(long id){

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

        chamado = dao.buscar(id);

        detailsSolicitante.setText("Chamado de " + chamado.getSolicitante());
        detailsData.setText("Em " + chamado.getInicio().format(formatterDate));
        detailsTime.setText("As " + chamado.getInicio().format(formatterTime) + " horas");
        detailsDuracao.setText(Integer.toString(chamado.getDuracao()) + " minutos");
        detailsObservacoes.setText(chamado.getObservacoes());

        if(chamado.getResolvido() == false){
            detailsResolvido.setText("Chamado sem solução!");
            detailsStatus.setImageResource(R.drawable.ic_baseline_error_outline_80);
            detailsStatus.setColorFilter(ContextCompat.getColor(this, R.color.transparent_red), PorterDuff.Mode.SRC_IN);
        } else {
            detailsResolvido.setText("Chamado Resolvido!");
            detailsStatus.setImageResource(R.drawable.ic_baseline_check_circle_outline_80);
            detailsStatus.setColorFilter(ContextCompat.getColor(this, R.color.transparent_green), PorterDuff.Mode.SRC_IN);
        }

    }
}