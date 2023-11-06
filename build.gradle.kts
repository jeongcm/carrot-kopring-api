import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.graalvm.buildtools.native") version "0.9.27"
    kotlin("jvm") version "1.8.22" // kotlin version
    kotlin("plugin.spring") version "1.8.22" // Kotlin Spring compiler plugin version
}

group = "com.travelit"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    // Mongo DB
//    implementation("org.springframework.boot:spring-boot-starter-data-mongodb") // mongo db

    // Graphql
    implementation("org.springframework.boot:spring-boot-starter-graphql")

    // OAuth 2.0 Client
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client") // oauth
    // Jakarta Persistence API
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")

    // token
    implementation("io.jsonwebtoken:jjwt-api:0.12.0")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.0")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.0")

    // Postgresql
    runtimeOnly("org.postgresql:postgresql")  // postgresql driver

    // Web
    testImplementation("org.springframework:spring-webflux")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.graphql:spring-graphql-test")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    testCompileOnly("org.projectlombok:lombok:1.18.22")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.22")

    // etc
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
