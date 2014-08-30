# Achtern Engine v0.2.2

Java Game Engine by the Captain of Games - DaGardner ;)

More detailed explanations and code examples, once 0.5 has passed!


    CoreEngine engine = new CoreEngine(new Game() {

           @Override
           public void init(CoreEngine engine) {
                // Setup Scene
           }
    });

    engine.createWindow("MyAwesomeGame", 1280, 720);
    engine.start(60); // at 60fps