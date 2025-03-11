tasks.jar {
    enabled = false
}

dependencies {
    implementation(project(":core"))
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-quartz")

    testImplementation("io.mockk:mockk:1.13.17")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.springframework.batch:spring-batch-test")
}

tasks.register("prepareKotlinBuildScriptModel"){}