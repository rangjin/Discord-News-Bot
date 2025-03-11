tasks.jar {
    enabled = false
}

dependencies {
    implementation(project(":core"))
    implementation("org.jsoup:jsoup:1.18.3")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("net.dv8tion:JDA:5.2.2")

    testImplementation("io.mockk:mockk:1.13.17")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.register("prepareKotlinBuildScriptModel"){}