# Achtern Engine v0.4-SNAPSHOT

[![Build Status](https://travis-ci.org/achtern/AchternEngine.svg?branch=develop)](https://travis-ci.org/achtern/AchternEngine)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.achtern/AchternEngine/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/org.achtern/AchternEngine)
[![Documentation Status](https://readthedocs.org/projects/achternengine/badge/?version=latest)](http://docs.achtern.org)

## Java Game Engine by the Captain of Achtern - DaGardner ;)

More detailed explanations and code examples, once 0.5 has passed!

You can easily start the engine by using the following code:

```java

public class MyGame extends Game {
    public static void main(String... args) {
        CoreEngine engine = new CoreEngine(new AchternGame(), new LWJGLBindingProvider());
        engine.start(60); // 60 fps
        System.exit(0);
    }
    
    @Override
    public Dimension getWindowDimensions() {
        return Dimension.HD_720;
    }
    
    @Override
    public void init(CoreEngine engine) {
        add(new Node("Camera")
            .add(new Camera()) // the main camera
            .add(new MouseLook(1)) // allows you to look around
            .add(new HumanMover(10)); // allows you to walk
            .add(new FlyMover(10)); // allows you to fly. Awesome \o/
        );
        add(new FigureProvider("Meshes", "floor").get()); // add a basic plane mesh

        Node light = new DirectionalLight(Color.WHITE, 1).boxed(); // some light!

        // adjust the position a bit
        light.getTransform().setPosition(new Vector3f(5, 5 ,5));
        light.getTransform().rotate(Transform.X_AXIS, 45);
        light.getTransform().rotate(Transform.Z_AXIS, 45);
        light.getTransform().rotate(Transform.Y_AXIS, 90);


        add(new FogGenerator().boxed()); // maybe some fog would be cool..?!



    }
    
    @Override
    public void update(float delta) {
    }
    
    @Override
    public void render(RenderEngine renderEngine) {
    }
    
}

```

## How to install

AchternEngine uses maven! Simply add this to your dependencies:

```xml
<dependency>
    <groupId>org.achtern</groupId>
    <artifactId>AchternEngine</artifactId>
    <version>0.4-SNAPSHOT</version>
</dependency>
```

But in order to be able to run the OpenGL binding, you need to have the natives in your classpath.

This is easily done with maven as well:

under `<build><plugins>` add this build plugin

```xml
<plugin>
    <groupId>com.googlecode.mavennatives</groupId>
    <artifactId>maven-nativedependencies-plugin</artifactId>
    <version>0.0.7</version>
    <executions>
        <execution>
            <id>unpacknatives</id>
            <phase>generate-resources</phase>
            <goals>
                <goal>copy</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

In addition to that AchternEngine uses slf4j as logging framework, you need to provide the implementation for that.
You can use whatever you want and fits your project, but I recommend to use logback!

```xml
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.0.13</version>
</dependency>
```

And add the following basic configuration file `logback.xml`:

```xml
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <!-- encoders are assigned the type
             ch.qos.logback.classic.encoder.PatternLayoutEncoder by default -->
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="info">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```

The logging levels debug and trace are used to debug the engine itself, not your game!

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
with LWJGL 2, but it easy to make it work with LWJGL 3 or JOGL, just implement a few wrappers and you're set.


## Contributing

__All__ contributions are welcome: bug reports, patches, documentation (!) and everything you can think of.


If and only if you want to contribute code to this projects you have to be aware of the fact, that this project is using [Project Lombok](http://projectlombok.org/) so your IDE should support this.

Please send PR only to the `develop` branch.

