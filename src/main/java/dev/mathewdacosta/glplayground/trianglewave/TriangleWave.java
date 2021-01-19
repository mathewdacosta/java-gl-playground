package dev.mathewdacosta.glplayground.trianglewave;

import dev.mathewdacosta.glplayground.common.WindowedGraphicsDemo;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class TriangleWave extends WindowedGraphicsDemo {

    private static final int FRAME_RATE = 60;
    private static final int WINDOW_WIDTH = 640;
    private static final int WINDOW_HEIGHT = 480;

    private final double[] xPositions = {-0.8, 0, 0.7};
    private final double[] yPositions = {0, 0, 0};

    private boolean isMouseHeld = false;

    public TriangleWave(boolean vsync) {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, FRAME_RATE, vsync);
    }

    @Override
    protected void registerGlfwCallbacks() {
        glfwSetMouseButtonCallback(window, (window1, button, action, mods) -> {
            switch (action) {
                case GLFW_PRESS:
                    isMouseHeld = true;
                    break;
                case GLFW_RELEASE:
                    isMouseHeld = false;
                    break;
                default:
                    break;
            }
        });
    }

    protected void update() {
        glfwPollEvents();

        double currentTime = glfwGetTime();

        yPositions[0] = Math.sin(currentTime * 0.9);
        yPositions[1] = Math.cos(currentTime * 0.6);
        yPositions[2] = Math.sin(currentTime * 0.8);

        if (isMouseHeld) {
            double[] xPos = {0};
            double[] yPos = {0};
            glfwGetCursorPos(window, xPos, yPos);

            double yScaled = 1 - ((yPos[0] / WINDOW_HEIGHT) * 2);
            if (yScaled >= -1 && yScaled <= 1) {
                yPositions[1] = yScaled;
            }
        }
    }

    protected void render() {
        // Clear framebuffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glBegin(GL_POLYGON);
        glColor3f(1.0f, 0.0f, 0.0f);
        glVertex2d(xPositions[0], yPositions[0]);
        glColor3f(0.0f, 1.0f, 0.0f);
        glVertex2d(xPositions[1], yPositions[1]);
        glColor3f(0.0f, 0.0f, 1.0f);
        glVertex2d(xPositions[2], yPositions[2]);
        glEnd();

        // Swap colour buffers
        glfwSwapBuffers(window);
    }

}
