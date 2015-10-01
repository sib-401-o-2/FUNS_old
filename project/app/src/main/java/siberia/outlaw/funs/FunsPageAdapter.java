package siberia.outlaw.funs;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by asmodeus on 01.10.15.
 */
public class FunsPageAdapter extends PagerAdapter {


    //private List<LocalDate> mItems = new ArrayList<>();
    LocalDate startDate;
    LocalDate endDate;

    public FunsPageAdapter() {
        startDate = new LocalDate()
                .withMonthOfYear(DateTimeConstants.SEPTEMBER)
                .withDayOfMonth(1);
        endDate = new LocalDate()
                .withMonthOfYear(DateTimeConstants.DECEMBER)
                .withDayOfMonth(31);
//        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1))
//        {
//            mItems.add(date);
//        }
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        System.out.println("page " + position);
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.layout_page, container, false);

        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText("Page: " + position);


        List<Subject> rec = new ArrayList<Subject>();
        populateRecords(rec);
        FunsDayAdapter adapter = new FunsDayAdapter(rec);
        LinearLayoutManager llm = new LinearLayoutManager(container.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(llm);

        container.addView(view);

        return view;
    }

    private void populateRecords(List<Subject> records){
        for (int i = 0; i<50; i++){
            Subject record = new Subject();
            record.setName("Item №" + i);
            record.setImportance(Subject.Importance.values()[i%4]);
            records.add(record);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return Days.daysBetween(startDate, endDate).getDays();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public String getPageTitle(int position) {
        return startDate.plusDays(position).dayOfWeek().getAsText(Locale.forLanguageTag("RU_ru"));
    }

//    public void addAll(List<String> items) {
//        mItems = new ArrayList<>(items);
//    }
}
