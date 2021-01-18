package dev.mathewdacosta.glplayground;

import com.google.common.collect.ImmutableMap;
import dev.mathewdacosta.glplayground.origami.Origami;
import dev.mathewdacosta.glplayground.trianglewave.TriangleWave;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

public class Main {

    private static final Map<String, Consumer<String[]>> demos = ImmutableMap.<String, Consumer<String[]>>builder()
            .put("triangle", args -> new TriangleWave(false).start())
            .put("triangle-vsync", args -> new TriangleWave(true).start())
            .put("origami", args -> new Origami(false).start())
            .put("origami-vsync", args -> new Origami(true).start())
            .build();

    public static void main(String[] args) {
        if (args == null || args.length < 1) {
            System.out.println("No demo specified!");
            listDemos();
            return;
        }

        Consumer<String[]> demo = demos.get(args[0].toLowerCase(Locale.ROOT));
        if (demo == null) {
            System.out.println("No such demo " + args[0] + "!");
            listDemos();
            return;
        }

        demo.accept(Arrays.copyOfRange(args, 1, args.length));
    }

    private static void listDemos() {
        System.out.println("Available demos:");
        demos.keySet().forEach(name -> System.out.println("  - " + name));
    }

}
