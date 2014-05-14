SpheroBot
=========
A convenient Android turtle module for Sphero

Usage
-----

Initialize your Sphero with the context and, optionally, the name of the Sphero you want to connect to

    SpheroBot sphero = new SpheroBot(this);

Start specifying the block of actions for your Sphero. The last parameter represents the action delay (expressed in milliseconds) before starting the next action.

    sphero.drive(90.0f, 1.0f, 500);
    sphero.drive(270.0f, 1.0f, 500);

And finally start it! Your Sphero will execute the actions you declared in the correct order.
Optionally you can specify a SpheroBotListener for the start method

    sphero.start();


Relased under MIT license, Copyright (c) 2014 Atooma Team