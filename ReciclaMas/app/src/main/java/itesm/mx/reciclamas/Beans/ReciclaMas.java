package itesm.mx.reciclamas.Beans;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Cesar on 6/23/2016.
 */
public class ReciclaMas extends Application {
    public void onCreate(){
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "rwAwE0m6QrSUlrwst2r6KB8U4yb2btTMZI3vZeeC", "SK3VnMVMx5lTPKKe9cyv7T53ndtpHA04tMEwSJA2");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
