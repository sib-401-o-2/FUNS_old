package siberia.outlaw.funs;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;

import com.nshmura.recyclertablayout.RecyclerTabLayout;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.chrono.ISOChronology;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(this);

        Funs.getInstance().setContext(getApplicationContext());
        
        setContentView(R.layout.activity_main);

        FunsPageAdapter adapter = new FunsPageAdapter();

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(Funs.getInstance().getViewPagerCurrentItem());

        RecyclerTabLayout recyclerTabLayout = (RecyclerTabLayout)
                findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}