# Java GL Playground

A testing ground for playing with LWJGL, OpenGL and GLFW.

## Demos

These demos can be built and run using the following commands on Windows (x64):
```bash
git pull https://github.com/mathewdacosta/java-gl-playground
cd java-gl-playground
gradlew.bat run --args='<demo name>'
```

On other platforms/architectures, you will need to adjust the `lwjglNatives` value in `build.gradle.kts` - see [here](https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.2.3/) for possible values.

On macOS or Linux, you will need to replace `gradlew.bat` with `./gradlew`.

You can see the current names of demos below.

### `triangle`, `triangle-vsync`: bouncing RGB triangle

This demo draws a triangle with three points that move up and down using sin and cos functions. The middle vertex's Y coordinate can also be changed by clicking and moving the mouse on the window.

### `origami`, `origami-vsync`: point-and-click polygons

This demo draws a single continuous polygon out of given vertices, cycling through multiple colours for each vertex.

![Origami screenshot 1](https://i.imgur.com/bzYYuLrm.png) 
![Origami screenshot 2](https://i.imgur.com/nYzKyECm.png)

- Right click to add a vertex
- Middle click to remove a vertex
- Left click and drag to move a vertex
