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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;


public class MainActivity extends AppCompatActivity {

    TextView sunOnDateTVO;
    TextView sunRATVO;
    TextView sunDecTVO;
    TextView sunVarTVO;
    TextView sunVarTVO2;
    TextView sunVarTVO3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create Joda DateTime instance and set date to desired time
        DateTime dateCal = new DateTime(2017,2,16,3,47,39,0, DateTimeZone.UTC);

        // set user location
        double userLat = 45 + (13 + 59.88/60)/60;
        double userLong = -93 + (17 + 28.84/60)/60;

        // set DSO Right Ascension (set to Betelguese)
        double dsoRA = (5 + (56 + 6.42/60)/60) * (360/24);
        double dsoDec = 7 + (24 + 24.2/60)/60;

        // Get days since J2000
        double daysSinceJ2000 = AstroCalc.daysSinceJ2000((dateCal.getMillis()));
        double greenwichST = AstroCalc.greenwichST(daysSinceJ2000);
        double localST = AstroCalc.localST(greenwichST, userLong);
        double hourAngle = AstroCalc.hourAngle(localST, dsoRA);
        double dsoAlt = AstroCalc.dsoAlt(dsoDec, userLat, hourAngle);
        double dsoAz = AstroCalc.dsoAz(dsoDec, userLat, hourAngle, dsoAlt);

        //lstOffset =  userLong / 15.0;

        /*
        // create Sun object and determine location
        Sun theSun = new Sun();
        theSun.updateSunLoc (sunOnDateCal);
        theSunRA = theSun.getSunRA();
        theSunRAHMS = AstroCalc.convertDDToHMS(theSunRA);
        theSunDec = theSun.getSunDec();
        theSunDecDMS = AstroCalc.convertDDToDMS(theSunDec);
        theSunVar = theSun.getSunVar();
        */

        // Display result
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        DateTimeFormatter fmt = DateTimeFormat.longDateTime().withZoneUTC();

        sunOnDateTVO = (TextView)findViewById(R.id.sunOnDateTVId);
        String sunOnDateString = Double.toString(daysSinceJ2000);
        sunOnDateTVO.setText(sunOnDateString);

        sunRATVO = (TextView)findViewById(R.id.sunRATVId);
        String sunRAString = Double.toString(greenwichST);
        //String sunRAString = theSunRAHMS;
        sunRATVO.setText(sunRAString);

        sunDecTVO = (TextView)findViewById(R.id.sunDecTVId);
        String sunDecString = Double.toString(localST);
        //String sunDecString = theSunDecDMS;
        sunDecTVO.setText(sunDecString);

        sunVarTVO = (TextView)findViewById(R.id.sunVarTVId);
        //String sunVarString = dateCal.toString(fmt);
        String sunVarString = Double.toString(hourAngle);
        sunVarTVO.setText(sunVarString);

        sunVarTVO2 = (TextView)findViewById(R.id.sunVarTVId2);
        //String sunVarString = dateCal.toString(fmt);
        String sunVarString2 = Double.toString(dsoAlt);
        sunVarTVO2.setText(sunVarString2);

        sunVarTVO3 = (TextView)findViewById(R.id.sunVarTVId3);
        //String sunVarString = dateCal.toString(fmt);
        String sunVarString3 = Double.toString(dsoAz);
        sunVarTVO3.setText(sunVarString3);


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
