package com.example.petstore

import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit

fun main() {
    // Function to check if the application is up
    fun isApplicationUp(port: Int): Boolean {
        return try {
            val url = URL("http://localhost:$port/pets/1")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 1000
            connection.readTimeout = 1000
            connection.responseCode == 200
        } catch (e: Exception) {
            false
        }
    }

    // Start the application
    println("Starting the application...")
    val gradleProcess = ProcessBuilder("./gradlew", "bootRun").directory(File(".")).start()

    // Wait for the application to start (adjust timeout as needed)
    val maxWaitTime = 60L // seconds
    val startTime = System.currentTimeMillis()
    while (!isApplicationUp(8080)) {
        if (System.currentTimeMillis() - startTime > maxWaitTime * 1000) {
            println("Application failed to start within $maxWaitTime seconds")
            gradleProcess.destroy()
            System.exit(1)
        }
        Thread.sleep(1000)
    }
    println("Application started successfully")

    // Run Specmatic Docker container
    println("Running Specmatic contract tests...")
    val specmaticProcess = ProcessBuilder(
        "docker", "run",
        "-v", "${System.getProperty("user.dir")}/specmatic.yaml:/usr/src/app/specmatic.yaml",
        "-e", "HOST_NETWORK=host",
        "--network", "host",
        "znsio/specmatic",
        "test", "--host", "http://localhost", "--port", "8080"
    ).start()

    // Capture and print Specmatic output
    val reader = specmaticProcess.inputStream.bufferedReader()
    val output = StringBuilder()
    var line: String?
    while (reader.readLine().also { line = it } != null) {
        println(line)
        output.append(line).append("\n")
    }

    // Wait for Specmatic process to finish
    val exitCode = specmaticProcess.waitFor()

    // Stop the application
    gradleProcess.destroy()
    gradleProcess.waitFor(10, TimeUnit.SECONDS)

    // Interpret results
    if (exitCode == 0) {
        println("Contract tests passed successfully!")
        System.exit(0)
    } else {
        println("Contract tests failed. Exit code: $exitCode")
        System.exit(1)
    }
}