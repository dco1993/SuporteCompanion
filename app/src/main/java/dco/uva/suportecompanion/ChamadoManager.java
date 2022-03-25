package dco.uva.suportecompanion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class ChamadoManager extends AppCompatActivity {

    private String hora, data, dateTime;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    private Button btnEscolherData, btnEscolherHora, btnSalvar;
    private TextView txvData, txvHora;
    private EditText edtSolicitante, edtDuracao;
    private TextInputEditText inputObservacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamado_manager);
        setTitle("Novo Chamado");
        initDatePicker();
        initTimePicker();

        btnEscolherData = findViewById(R.id.btnSelectData);
        btnEscolherHora = findViewById(R.id.btnSelectTime);
        txvData = findViewById(R.id.txvData);
        txvHora = findViewById(R.id.txvTime);

        btnEscolherData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show();
            }
        });

        btnEscolherHora.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                timePicker.show();
            }
        });
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                mes++;
                data = ano + "-" + String.format("%02d", mes) + "-" + String.format("%02d", dia);
                txvData.setText(dia + "/" + mes + "/" + ano);
            }
        };

        Calendar cal = Calendar.getInstance();
        int ano = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        datePicker = new DatePickerDialog(this, dateSetListener, ano, mes, dia);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private void initTimePicker(){

        Calendar cal = Calendar.getInstance();
        int hr = cal.get(Calendar.HOUR);
        int min = cal.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hr, int min) {
                hora = String.format(Locale.getDefault(), "%02d:%02d", hr, min);
                txvHora.setText(hora);
                hora +=  ":00";
            }
        };

        timePicker = new TimePickerDialog(this, onTimeSetListener, hr, min, true);

    }
}