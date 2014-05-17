SpheroBot
=========
A convenient Android turtle module for Sphero

Usage
-----

Initialize your Sphero with the context and, optionally, the name of the Sphero you want to connect to

    SpheroBot sphero = new SpheroBot(context);

Start specifying the block of actions for your Sphero. The last parameter represents the action delay (expressed in milliseconds) before starting the next action.

    sphero.drive(90.0f, 1.0f, 500);
    sphero.setColor(0, 0, 255, 2000)
    sphero.drive(270.0f, 1.0f, 500);

And finally start it! Your Sphero will execute the actions you declared in the correct order. Optionally you can specify a SpheroBotListener for the start method

    sphero.start();

An example:

    import android.app.Activity;
    import android.os.Bundle;
    import android.view.Menu;

    import com.atooma.spherobot.SpheroBot;

    public class MainActivity extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            SpheroBot sphero = new SpheroBot(this);
            sphero.drive(90.0f, 1.0f, 500);
            sphero.drive(270.0f, 1.0f, 500);
            sphero.start();
        }

    }

Relased under MIT license, Copyright (c) 2014 Atooma Team