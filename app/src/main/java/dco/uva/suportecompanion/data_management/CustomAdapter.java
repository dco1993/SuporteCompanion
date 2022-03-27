package dco.uva.suportecompanion.data_management;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dco.uva.suportecompanion.ChamadoDetails;
import dco.uva.suportecompanion.R;
import dco.uva.suportecompanion.model.ChamadoModel;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ChamadosViewHolder> {

    private Context context;
    private List<ChamadoModel> chamados;

    public CustomAdapter(Context _context, List<ChamadoModel> _chamados){
        this.context = _context;
        this.chamados = _chamados;
    }

    @NonNull
    @Override
    public ChamadosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chamado_row, parent, false);
        return new ChamadosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChamadosViewHolder holder, int position) {

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");

        if(chamados.get(position).getResolvido() == false){
            holder.imgRowStatus.setImageResource(R.drawable.ic_baseline_error_outline_40);
            holder.imgRowStatus.setColorFilter(ContextCompat.getColor(context, R.color.red), PorterDuff.Mode.SRC_IN);
        }

        holder.txvRowSolicitante.setText("Chamado de " + chamados.get(position).getSolicitante());
        holder.txvRowData.setText("Em " + chamados.get(position).getInicio().format(formatterDate));
        holder.txvRowDuracao.setText(Integer.toString(chamados.get(position).getDuracao()) + " min");

        holder.itemLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChamadoDetails.class);
                intent.putExtra("id", chamados.get(holder.getAdapterPosition()).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chamados.size();
    }

    public class ChamadosViewHolder extends RecyclerView.ViewHolder {

        ImageView imgRowStatus;
        TextView txvRowSolicitante, txvRowData, txvRowDuracao;
        LinearLayout itemLayout;

        public ChamadosViewHolder(@NonNull View itemView) {
            super(itemView);

            imgRowStatus = itemView.findViewById(R.id.imgRowStatus);
            txvRowSolicitante = itemView.findViewById(R.id.txvRowSolicitante);
            txvRowData = itemView.findViewById(R.id.txvRowData);
            txvRowDuracao = itemView.findViewById(R.id.txvRowDuracao);
            itemLayout = itemView.findViewById(R.id.itemLayout);

        }
    }
}
