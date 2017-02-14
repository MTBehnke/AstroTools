package absolute.beginners.astrotools;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {

    //  User Location Variables - initial values
    //  misc note - in Excel to get Julian date add 2415018.50 to Excel date value
    public double userLat = 45.2258;
    public double userLong = -93.28105;
    //double userElev = 274;      // meters
    public Double lstOffset;
    TextView sunOnDateTVO;
    TextView sunRATVO;
    TextView sunDecTVO;
    TextView sunVarTVO;
    double theSunRA;                // Right Ascension
    double theSunDec;               // Declination
    double theSunVar;               // Temp variable for debugging
    String theSunDecDMS;
    String theSunRAHMS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create sunOnDate calendar objects and set dates to desired time
        Calendar sunOnDateCal = new GregorianCalendar();
        sunOnDateCal.setTimeZone((TimeZone.getTimeZone("GMT")));
        sunOnDateCal.set(2003,6,27,0,0,0);
        lstOffset =  userLong / 15.0;

        // create Sun object and determine location
        Sun theSun = new Sun();
        theSun.updateSunLoc (sunOnDateCal);
        theSunRA = theSun.getSunRA();
        theSunRAHMS = convertDDToHMS(theSunRA);
        theSunDec = theSun.getSunDec();
        theSunDecDMS = convertDDToDMS(theSunDec);
        theSunVar = theSun.getSunVar();

        // Display result
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sunOnDateTVO = (TextView)findViewById(R.id.sunOnDateTVId);
        String sunOnDateString = sdf.format(sunOnDateCal.getTime());
        sunOnDateTVO.setText(sunOnDateString);

        sunRATVO = (TextView)findViewById(R.id.sunRATVId);
        //String sunRAString = Double.toString(theSunRA);
        String sunRAString = theSunRAHMS;
        sunRATVO.setText(sunRAString);

        sunDecTVO = (TextView)findViewById(R.id.sunDecTVId);
        //String sunDecString = Double.toString(theSunDec);
        String sunDecString = theSunDecDMS;
        sunDecTVO.setText(sunDecString);

        sunVarTVO = (TextView)findViewById(R.id.sunVarTVId);
        //String sunVarString = sdf.format(lstOnDateCal.getTime());
        String sunVarString = Double.toString(lstOffset);
        sunVarTVO.setText(sunVarString);
    }

    public String convertDDToDMS(double dd) {
        int deg = (int) dd;
        int min = (int) ((dd-deg)*60.0);
        long sec =  Math.round ((dd-deg-(min/60.0))*3600);
        String dms = deg + "° " + min + "' " + sec + "\"";
        return dms;
    }

    public String convertDDToHMS (double dd) {
        dd = 24.0 * dd / 360.0;
        int hour = (int) dd;
        int min = (int) ((dd-hour)*60.0);
        long sec =  Math.round ((dd-hour-(min/60.0))*3600);
        String hms = hour + "h " + min + "m " + sec + "s";
        return hms;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


        /*  Temp disable action button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });    */
}
