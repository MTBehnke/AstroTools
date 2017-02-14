package absolute.beginners.astrotools;

import android.app.Activity;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Sun  {
    Calendar sunDate;       // current date/time (not 'now') of Sun, used for determining location
    double eclLong;         // current ecliptic longitude of Sun, in degrees
    double eclLat;          // current ecliptic latitude of Sun, always = 0 degrees
    double sunRA;           // current right ascension of Sun
    double sunDec;          // current declination of Sun
    double sunAlt;
    double sunAz;
    double obliqEclipt;
    double sunVar;


    // static orbital variables for Sun
    static double eclLongEpoch = 279.557208;    // ecliptic longitude at epoch 2010.0, epsilon_g
    static double eclLongPeri = 283.112438;     // ecliptic longitude of perigee at epoch 2010.0, omega_bar_g
    static double eccentricity = 0.016705;      // eccentricity of orbit at epoch 2010.0, e
    static double semiMajorAxis = 1.495985E8;   // semi major axis in kilometers, r_0
    static double angularDiam = 0.533128;       // angular diameter at r = r0, theta_0

    //Sun contructor
    public Sun () {
        eclLat = 0.0;
    }

    public void updateSunLoc (Calendar date) {
        Calendar sunDate = new GregorianCalendar();
        sunDate.setTimeZone((TimeZone.getTimeZone("GMT")));
        sunDate.setTime(date.getTime());
        Calendar sunEpoch = new GregorianCalendar();
        sunEpoch.setTimeZone((TimeZone.getTimeZone("GMT")));
        sunEpoch.set(2009, 11, 31, 0, 0, 0);
        Calendar epochJ2000 = new GregorianCalendar();
        epochJ2000.setTimeZone((TimeZone.getTimeZone("GMT")));
        epochJ2000.set(2000,0,1,12,0,0);
        // calculate number of days since epoch (D)
        long sunOnDateMillis = date.getTimeInMillis();
        long sunEpochMillis = sunEpoch.getTimeInMillis();
        long epochJ2000Millis = epochJ2000.getTimeInMillis();
        long millisSinceEpoch = sunOnDateMillis - sunEpochMillis;
        double daysSinceEpoch = millisSinceEpoch / (1000*60*60*24);
        // calculate how many degrees sun has moved since epoch (N) (between 0-360°)
        double degreesSinceEpoch = (360 / 365.242191) * daysSinceEpoch;
        degreesSinceEpoch = degreesSinceEpoch % 360;
        if (degreesSinceEpoch < 0) degreesSinceEpoch = degreesSinceEpoch + 360;
        // calculate mean anomoly (M0), if negative add 360
        double meanAnomoly = degreesSinceEpoch + eclLongEpoch - eclLongPeri;
        if(meanAnomoly < 0) meanAnomoly = meanAnomoly +360;
        // calculate Geocentric Ecliptic Longitude (lambda0)
        eclLong = degreesSinceEpoch + (360/Math.PI)*eccentricity*Math.sin(Math.toRadians(meanAnomoly)) + eclLongEpoch;
        if(eclLong > 360)eclLong = eclLong - 360;
        long EpochJ2000Millis = epochJ2000.getTimeInMillis();
        double tCent = (sunOnDateMillis-epochJ2000Millis) / (1000*60*60*24*365.25*100);
        obliqEclipt = 23+(26.0/60)+(21.45/3600)-(46.815/3600)*tCent-(0.0006/3600)*Math.pow(tCent,2)+(0.00181/3600)*Math.pow(tCent,3);
        obliqEclipt = Math.toRadians(obliqEclipt);
        // calculate right ascension and declination
        Double lambda = Math.toRadians(eclLong);
        Double beta = Math.toRadians(eclLat);
        double ratemp = (Math.sin(lambda)*Math.cos(obliqEclipt)-Math.tan(beta)*Math.sin(obliqEclipt))/Math.cos(lambda);
        sunRA = Math.toDegrees(Math.atan(ratemp));
        if (sunRA<0)sunRA = sunRA+180;
        double dectemp = Math.sin(beta)*Math.cos(obliqEclipt)+Math.cos(beta)*Math.sin(obliqEclipt)*Math.sin(lambda);
        sunDec = Math.toDegrees(Math.asin(dectemp));
        sunVar = sunRA;
    }

    public double getSunRA () {
        return sunRA;
    }

    public double getSunDec () {
        return sunDec;
    }

    //  need to figure out how to handle local sidereal time (not just offset)
    public double getSunAlt (double lst) {
        return sunAlt;
    }

    public double getSunAz () {
        return sunAz;
    }

    public double getSunVar () {
        return sunVar;
    }


}
