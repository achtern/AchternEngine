# Achtern Engine v0.4-SNAPSHOT

[![Build Status](https://travis-ci.org/achtern/AchternEngine.svg?branch=develop)](https://travis-ci.org/achtern/AchternEngine)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.achtern/AchternEngine/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/org.achtern/AchternEngine)

## Java Game Engine by the Captain of Achtern - DaGardner ;)

More detailed explanations and code examples, once 0.5 has passed!

You can easily start the engine by using the following code:

```java

public class MyGame extends Game {
    public static void main(String... args) {
        CoreEngine engine = new CoreEngine(new MyGame());
        engine.start(60); // 60fps
    }
    
    @Override
    public Dimension getWindowDimensions() {
        return new Dimension(1280, 720);
    }
    
    @Override
    public String getWindowTitle() {
        return "MyGame";
    }
    
    @Override
    public void init(CoreEngine engine) {
        add(new Node("Camera").add(new Camera()));
        add(ResourceLoader.getFigure("floor").boxed());
    }
    
    @Override
    public void update(float delta) {
    }
    
    @Override
    public void render(RenderEngine renderEngine) {
    }
    
}

```

## Here are some screenshots, 'cause everyone loves screenshots

![Basic scene](https://i.imgur.com/YObt8xi.jpg)

![Custom FOV](https://i.imgur.com/T362bJY.jpg)

* Complex mesh types
* Texture mapping
* Lighting with multiple light types and unlimited* light source
* Basic Shadow Mapping
* Dynamic Fog System
* Skybox
* Mesh Generators (in this image: Grid)
* Dynamic and realtime FOV adjustments

\* well, limited by your GPU power ;)


## More Information

AchternEngine tries to be independent of the Graphics Binding.
It currently works best with OpenGL, but I guess any system could work.

The core engine is independent of the java binding. Currently it gets shipped
with LWJGL 2, but it easy to make it work with JOGL, just implement a few wrappers and you're set.

There are no util classes used, beside the ones stated in the `pom.xml`.

## Contributing

I'm happy if somebody wants to write code for the engine.

This project is using [Lombok](http://projectlombok.org/) so your IDE should support this.

Please send PR only to the develop branch.

