# Achtern Engine v0.3-SNAPSHOT

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