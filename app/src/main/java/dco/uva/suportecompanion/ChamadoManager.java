package dco.uva.suportecompanion;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import dco.uva.suportecompanion.data_management.Chamado_DAO;
import dco.uva.suportecompanion.model.ChamadoModel;

public class ChamadoManager extends AppCompatActivity {

    private String hora, date, dateTime;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    private Button btnEscolherData, btnEscolherHora, btnSalvar;
    private TextView txvData, txvHora;
    private EditText edtSolicitante, edtDuracao;
    private TextInputLayout inputObservacoes;
    private CheckBox ckbResolvido;
    private DateTimeFormatter formatter, formatterDate, formatterTime, dbDataFormatter;
    private Chamado_DAO dao;
    private ChamadoModel chamado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamado_manager);
        setTitle("Novo Chamado");
        initDatePicker();
        initTimePicker();
        dao = new Chamado_DAO(this);
        dao.open();

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        dbDataFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        edtSolicitante = findViewById(R.id.edtSolicitante);
        btnEscolherData = findViewById(R.id.btnSelectData);
        btnEscolherHora = findViewById(R.id.btnSelectTime);
        edtDuracao = findViewById(R.id.edtDuracao);
        inputObservacoes = findViewById(R.id.layObservacoes);
        ckbResolvido = findViewById(R.id.ckbResolvido);
        btnSalvar = findViewById(R.id.btnSalvarChamado);

        btnEscolherData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { datePicker.show(); }
        });

        btnEscolherHora.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ timePicker.show(); }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ gravarChamado(); }
        });

        if(getIntent().hasExtra("id")){

            setTitle("Atualizar Chamado");
            txvData = findViewById(R.id.txvData);
            txvHora = findViewById(R.id.txvTime);

            chamado = dao.buscar(getIntent().getLongExtra("id", 0));
            edtSolicitante.setText(chamado.getSolicitante());
            txvData.setText(chamado.getInicio().format(formatterDate));
            date = chamado.getInicio().format(dbDataFormatter);
            txvHora.setText(chamado.getInicio().format(formatterTime));
            hora = chamado.getInicio().format(formatterTime) + ":00";
            edtDuracao.setText(Integer.toString(chamado.getDuracao()));
            inputObservacoes.getEditText().setText(chamado.getObservacoes());
            if(chamado.getResolvido() == true){
                ckbResolvido.setChecked(true);
            }
            btnSalvar.setText("Atualizar Chamado");
        }
    }

    private void initDatePicker(){
        txvData = findViewById(R.id.txvData);

        Calendar cal = Calendar.getInstance();
        int ano = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                mes++;
                date = ano + "-" + String.format("%02d", mes) + "-" + String.format("%02d", dia);
                txvData.setText(dia + "/" + String.format("%02d", mes) + "/" + ano);
            }
        };

        mes++;
        date = ano + "-" + String.format("%02d", mes) + "-" + String.format("%02d", dia);
        txvData.setText(dia + "/" + String.format("%02d", mes) + "/" + ano);

        datePicker = new DatePickerDialog(this, dateSetListener, ano, mes, dia);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private void initTimePicker(){
        txvHora = findViewById(R.id.txvTime);

        Calendar cal = Calendar.getInstance();
        int hr = cal.get(Calendar.HOUR);
        int min = cal.get(Calendar.MINUTE);

        int pm = cal.get(Calendar.AM_PM);
        if((pm == 1) && (hr < 12)){
            hr += 12;
        }

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hr, int min) {
                hora = String.format(Locale.getDefault(), "%02d:%02d", hr, min);
                txvHora.setText(hora);
                hora +=  ":00";
            }
        };

        hora = String.format(Locale.getDefault(), "%02d:%02d", hr, min);
        txvHora.setText(hora);
        hora +=  ":00";

        timePicker = new TimePickerDialog(this, onTimeSetListener, hr, min, true);

    }

    private void gravarChamado() {

        dateTime = date + " " + hora;

        chamado = new ChamadoModel();
        chamado.setId(0);
        chamado.setSolicitante(edtSolicitante.getText().toString());
        chamado.setInicio(LocalDateTime.parse(dateTime, formatter));
        chamado.setDuracao(Integer.parseInt(edtDuracao.getText().toString()));
        chamado.setObservacoes(inputObservacoes.getEditText().getText().toString());

        if(ckbResolvido.isChecked()){
            chamado.setResolvido(true);
        } else {
            chamado.setResolvido(false);
        }

        try{
            if(getIntent().hasExtra("id")){
                chamado.setId(getIntent().getLongExtra("id", 0));
                dao.update(chamado);
                Toast.makeText(getApplicationContext(), "Chamado Atualizado", Toast.LENGTH_LONG).show();
            }else{
                dao.inserir(chamado);
                Toast.makeText(getApplicationContext(), "Chamado Incluído", Toast.LENGTH_LONG).show();
            }
            dao.close();
            finish();

        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "Erro ao incluir chamado", Toast.LENGTH_LONG).show();
        }


    }
}