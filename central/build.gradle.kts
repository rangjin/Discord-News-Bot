tasks.jar {
    enabled = false
}

dependencies {
    implementation(project(":core"))
    implementation("org.jsoup:jsoup:1.18.3")

    testImplementation("io.mockk:mockk:1.13.17")
}

tasks.register("prepareKotlinBuildScriptModel"){}