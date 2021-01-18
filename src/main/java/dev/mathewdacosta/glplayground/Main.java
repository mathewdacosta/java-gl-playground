package dev.mathewdacosta.glplayground;

import com.google.common.collect.ImmutableMap;
import dev.mathewdacosta.glplayground.trianglewave.TriangleWave;

import java.util.Locale;
import java.util.Map;

public class Main {

    private static final Map<String, Runnable> demos = ImmutableMap.<String, Runnable>builder()
            .put("triangle", () -> new TriangleWave(false).start())
            .put("triangle-vsync", () -> new TriangleWave(true).start())
            .build();

    public static void main(String[] args) {
        if (args == null || args.length < 1) {
            System.out.println("No demo specified!");
            listDemos();
            return;
        }

        Runnable demo = demos.get(args[0].toLowerCase(Locale.ROOT));
        if (demo == null) {
            System.out.println("No such demo " + args[0] + "!");
            listDemos();
            return;
        }

        demo.run();
    }

    private static void listDemos() {
        System.out.println("Available demos:");
        demos.keySet().forEach(name -> System.out.println("  - " + name));
    }

}
