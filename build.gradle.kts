plugins {
    java
    application
}

group = "dev.mathewdacosta"
version = "1.0-SNAPSHOT"

val guavaVersion = "30.1-jre"
val lwjglVersion = "3.2.3"
val lwjglNatives = "natives-windows"

repositories {
    mavenCentral()
}

dependencies {
    // Guava
    implementation("com.google.guava:guava:$guavaVersion")

    // LWJGL BOM
    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

    // LWJGL APIs
    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-opengl")
    implementation("org.lwjgl", "lwjgl-glfw")

    // LWJGL natives
    runtimeOnly("org.lwjgl", "lwjgl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = lwjglNatives)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(15))
    }
}

application {
    mainClass.set("dev.mathewdacosta.glplayground.Main")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(listOf("--release", "15", "--enable-preview"))
}

tasks.withType<Test> {
    jvmArgs("--enable-preview")
}

tasks.withType<JavaExec> {
    jvmArgs("--enable-preview")
}
