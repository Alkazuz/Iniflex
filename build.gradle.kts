plugins {
    id("java")
    id("application")
}

group = "website.marcosfernandes"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("website.marcosfernandes.Main")
}

tasks.run.get().doFirst {
    System.setProperty("file.encoding", "UTF-8")
}