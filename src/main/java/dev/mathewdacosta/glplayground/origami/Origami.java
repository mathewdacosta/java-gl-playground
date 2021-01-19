package dev.mathewdacosta.glplayground.origami;

import dev.mathewdacosta.glplayground.common.Color3f;
import dev.mathewdacosta.glplayground.common.Point2d;
import dev.mathewdacosta.glplayground.common.WindowedGraphicsDemo;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Origami extends WindowedGraphicsDemo {

    private static final int FRAME_RATE = 60;
    private static final int WINDOW_WIDTH = 640;
    private static final int WINDOW_HEIGHT = 480;
    private static final Color3f[] COLORS = {
            new Color3f(255, 0, 0),
            new Color3f(0, 255, 0),
            new Color3f(0, 0, 255),
            new Color3f(255, 255, 255),
            new Color3f(255, 255, 0),
            new Color3f(255, 0, 255),
            new Color3f(0, 255, 255)
    };

    private final List<Point2d> points = new ArrayList<>();
    private int currentDragPoint = -1;
    private int currentDrawMode = GL_POLYGON;

    public Origami(boolean vsync) {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, FRAME_RATE, vsync);
    }

    @Override
    protected void postInit() {
        glPointSize(5.0f);
    }

    private static Point2d getCursorPos(long window) {
        double[] xWindowPos = new double[1];
        double[] yWindowPos = new double[1];
        glfwGetCursorPos(window, xWindowPos, yWindowPos);

        double xScaledPos = (2 * xWindowPos[0] / WINDOW_WIDTH) - 1;
        double yScaledPos = 1 - (2 * yWindowPos[0] / WINDOW_HEIGHT);
        return new Point2d(xScaledPos, yScaledPos);
    }

    @Override
    protected void registerGlfwCallbacks() {
        glfwSetMouseButtonCallback(window, (window1, button, action, mods) -> {
            switch (button) {
                case GLFW_MOUSE_BUTTON_LEFT:
                    if (action == GLFW_PRESS)
                        grabPoint();
                    else
                        releasePoint();
                    break;
                case GLFW_MOUSE_BUTTON_RIGHT:
                    if (action == GLFW_PRESS)
                        addPoint();
                    break;
                case GLFW_MOUSE_BUTTON_MIDDLE:
                    if (action == GLFW_PRESS)
                        removePoint();
                    break;
                default:
                    break;
            }
        });

        glfwSetKeyCallback(window, (window1, key, scancode, action, mods) -> {
            switch (key) {
                case GLFW_KEY_1:
                    currentDrawMode = GL_POLYGON;
                    break;
                case GLFW_KEY_2:
                    currentDrawMode = GL_LINE_STRIP;
                    break;
                case GLFW_KEY_3:
                    currentDrawMode = GL_LINE_LOOP;
                    break;
                case GLFW_KEY_4:
                    currentDrawMode = GL_POINTS;
                    break;
                case GLFW_KEY_5:
                    currentDrawMode = GL_TRIANGLE_STRIP;
                    break;
                case GLFW_KEY_6:
                    currentDrawMode = GL_QUAD_STRIP;
                    break;
                default:
                    break;
            }
        });
    }

    private int findNearPoint() {
        Point2d cursor = getCursorPos(window);

        for (int i = 0, pointsSize = points.size(); i < pointsSize; i++) {
            Point2d point = points.get(i);
            double distance = Math.hypot(Math.abs(cursor.x() - point.x()), Math.abs(cursor.y() - point.y()));
            if (distance < 0.05f) {
                return i;
            }
        }
        return -1;
    }

    private void grabPoint() {
        currentDragPoint = findNearPoint();
    }

    private void releasePoint() {
        currentDragPoint = -1;
    }

    private void addPoint() {
        Point2d cursor = getCursorPos(window);
        points.add(cursor);
    }

    private void removePoint() {
        int index = findNearPoint();
        if (index >= 0)
            points.remove(index);
    }

    @Override
    protected void update() {
        glfwPollEvents();

        if (currentDragPoint >= 0) {
            points.set(currentDragPoint, getCursorPos(window));
        }
    }

    @Override
    protected void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        int count = points.size();
        int mode = count > 2 ? currentDrawMode : GL_POINTS;

        glBegin(mode);
        for (int i = 0; i < count; i++) {
            Point2d point = points.get(i);
            Color3f color = COLORS[i % COLORS.length];
            glColor3f(color.r(), color.g(), color.b());
            glVertex2d(point.x(), point.y());
        }
        glEnd();

        glfwSwapBuffers(window);
    }
}
