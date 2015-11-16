package siberia.outlaw.funs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * Created by asmodeus on 01.10.15.
 */
public class FunsDayAdapter extends RecyclerView.Adapter<FunsDayAdapter.ViewHolder> {
    private List<Subject> mDataset;

    public FunsDayAdapter(int position) {
        this.mDataset = Funs.getInstance().getSubjectsList(position);

        System.out.println("records created " + mDataset.size());
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView subject_name;
        private TextView subject_cabinetAndTeacher;
        private TextView subject_time;
        private Switch switch1;

        public ViewHolder(final View itemView) {
            super(itemView);
            subject_name = (TextView) itemView.findViewById(R.id.subject_name);
            subject_time = (TextView) itemView.findViewById(R.id.subject_time);
            subject_cabinetAndTeacher = (TextView) itemView.findViewById(R.id.subject_cab_and_teacher);
            switch1 = (Switch) itemView.findViewById(R.id.switch1);
            switch1.setChecked(true);
            switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    System.out.println("checked");
                    if(isChecked){
                        subject_name.setEnabled(true);
                        subject_time.setEnabled(true);
                        subject_cabinetAndTeacher.setEnabled(true);
                    }else{
                        subject_name.setEnabled(false);
                        subject_time.setEnabled(false);
                        subject_cabinetAndTeacher.setEnabled(false);
                    }
                }
            });
//            icon = (ImageView) itemView.findViewById(R.id.recyclerViewItemIcon);
//            deleteButton = (Button) itemView.findViewById(R.id.recyclerViewItemDeleteButton);
//            copyButton = (Button) itemView.findViewById(R.id.recyclerViewItemCopyButton);
//            deleteButtonListener = new DeleteButtonListener();
//            copyButtonListener = new CopyButtonListener();
//            deleteButton.setOnClickListener(deleteButtonListener);
//            copyButton.setOnClickListener(copyButtonListener);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FunsDayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_subject, parent, false);
        parent.addView(v);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //System.out.println("vh bind " + position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);
        Subject record = mDataset.get(position);
//        int iconResourceId = 0;
//        switch (record.getType()) {
//            case GREEN:
//                iconResourceId = R.drawable.green_circle;
//                break;
//            case RED:
//                iconResourceId = R.drawable.red_circle;
//                break;
//            case YELLOW:
//                iconResourceId = R.drawable.yellow_circle;
//                break;
//        }
//        viewHolder.icon.setImageResource(iconResourceId);
        holder.subject_name.setText(record.getName());
        holder.subject_time.setText("8:00\n13:00");
        holder.subject_cabinetAndTeacher.setText(record.getCabinet() + record.getTeacher());
//        viewHolder.deleteButtonListener.setRecord(record);
//        viewHolder.copyButtonListener.setRecord(record);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}