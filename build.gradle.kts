plugins {
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("io.micronaut.application") version "1.3.4"
}

version = "0.1"
group = "org.voronoy.edu"

repositories {
    mavenCentral()
    jcenter()
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("org.voronoy.edu.*")
    }
}

dependencies {
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-runtime")
    implementation("javax.annotation:javax.annotation-api")
    implementation("io.micronaut:micronaut-http-client")
    implementation("org.apache.logging.log4j:log4j-core:2.12.1")

    runtimeOnly("org.apache.logging.log4j:log4j-api:2.12.1")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.12.1")

    testImplementation("org.assertj:assertj-core:3.19.0")
}


application {
    mainClass.set("org.voronoy.edu.Application")
}

java {
    sourceCompatibility = JavaVersion.toVersion("15")
    targetCompatibility = JavaVersion.toVersion("15")
}



