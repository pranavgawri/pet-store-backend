plugins {
	kotlin("jvm") version "1.9.20"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "21"  // Set Kotlin JVM target to 21
        freeCompilerArgs += "-Xjsr305=strict"
    }
}

kotlin {
    jvmToolchain(21)
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<JavaExec>("runContractTest") {
    group = "verification"
    description = "Runs the contract tests using Specmatic"

    mainClass.set("com.example.petstore.ContractTestKt")
    classpath = sourceSets["test"].runtimeClasspath

    // Add these if you're using Kotlin
    dependsOn("compileTestKotlin")
    kotlin.sourceSets["test"].kotlin.srcDir("src/test/kotlin")
}

tasks.register("printVersions") {
    doLast {
        println("Gradle version: ${gradle.gradleVersion}")
        println("Java version: ${System.getProperty("java.version")}")
    }
}
