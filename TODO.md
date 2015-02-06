#TODO
- Shader
    Make shader source file not dependet on the renderpasses,
    there has to be a system in place, which dynamically injects code into the shader source files.
    

## New Shadersystem

Currently the engine doesn't allow the developer to specify shaders on a per material basis
all objects must use the same shader per light. The new system should fix this.
From a performance point of view it should be similar to the current one. At the same time
the engine should be this type of forward renderer, one shader pass per light.

**Current Algorithm**

    render()
    - initial Pass (Ambient)
    - for each Light:
        * bind Shader of _Light_
        * for each Object
            - draw Object
            
**New Algorithm**

    render()
    - inital Pass (Ambient)
    - for each Light:
        * for each Object
            - bind Shader of _Material_
            - draw Object


The difference is suddle, instead of binding the shader for all objects, each object binds it's own shader.
This is exactly as performand as the currently deployed version, since there won't be shader **rebinds**,
the abstraction layer takes away this overhead.

In order to implement that each Light must have an unique identifier, so the object can decide which shader to bind.
This will be an injectable enum, so the developer can add light types as needed.

### Roadmap

* Create ShaderSuits which hold shaders for all Lighttypes. A Suite is for example "Phong"
* Material should hold a Shader
* Lights should hold an unique identifier
* Material binds shader based on Light ID