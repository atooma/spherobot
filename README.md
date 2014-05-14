SpheroBot
=========

Usage
-----

Initialize your Sphero with the context and, optionally, the name of the Sphero you want to connect to

    SpheroBot sphero = new SpheroBot(this);

Start to specify the block of actions for your Sphero

    sphero.drive(90.0f, 1.0f, 500);
    sphero.drive(270.0f, 1.0f, 500);

And finally start. Optionally you can specify a SpheroBotListener for the start method

    sphero.start();

-------
Relased under MIT license, Copyright (c) 2014 Atooma Team