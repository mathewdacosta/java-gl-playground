package dev.mathewdacosta.glplayground.common;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public abstract class WindowedGraphicsDemo {

    private final int windowWidth;
    private final int windowHeight;
    private final long frameLength;
    private final boolean vsync;

    private final GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);
    protected long window;

    protected WindowedGraphicsDemo(int windowWidth, int windowHeight, int frameRate, boolean vsync) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.frameLength = 1000L / frameRate;
        this.vsync = vsync;
    }

    public final void start() {
        init();
        gameLoop();
        shutdown();
    }

    private void init() {
        glfwSetErrorCallback(errorCallback);

        // Init glfw and window
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialise GLFW");
        }

        // Register window hints
        registerGlfwWindowHints();

        // Create window
        window = glfwCreateWindow(windowWidth, windowHeight, "Window demo", NULL, NULL);
        if (window == NULL) {
            glfwTerminate();
            throw new RuntimeException("Window creation failed!");
        }

        // Register callbacks
        registerGlfwCallbacks();

        // Set context to window
        glfwMakeContextCurrent(window);
        // Enable vsync
        if (vsync) glfwSwapInterval(1);

        // Set up opengl
        GL.createCapabilities();
        glClearColor(0.08f, 0.08f, 0.08f, 0.0f);

        postInit();
    }

    protected void registerGlfwWindowHints() {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_SAMPLES, 4);
    }

    protected abstract void registerGlfwCallbacks();

    protected void postInit() {};

    private void gameLoop() {
        while (!glfwWindowShouldClose(window)) {
            double frameStart = glfwGetTime();
            update();
            render();
            double frameEnd = glfwGetTime();
            sleep(frameEnd - frameStart);
        }
    }

    protected abstract void update();

    protected abstract void render();

    private void sleep(double delta) {
        if (vsync) return;

        long sleepTime = (long) (frameLength - (delta * 1000));
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void shutdown() {
        beforeShutdown();
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        errorCallback.free();
    }

    protected void beforeShutdown() {}

}
