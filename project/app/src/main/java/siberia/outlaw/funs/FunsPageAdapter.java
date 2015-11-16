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

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by asmodeus on 01.10.15.
 */
public class FunsPageAdapter extends PagerAdapter {
    protected LocalDate startDate;
    protected LocalDate endDate;


    public FunsPageAdapter() {
        startDate = Funs.getInstance().getSemesterBeginning();
        endDate = Funs.getInstance().getSemesterEnd();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.layout_page, container, false);

        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(Funs.getInstance().getDayString(position));

        FunsDayAdapter adapter = new FunsDayAdapter(position);
        LinearLayoutManager llm = new LinearLayoutManager(container.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(llm);

        container.addView(view);

        return view;
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
