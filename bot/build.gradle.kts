tasks.jar {
    enabled = false
}

dependencies {
    implementation(project(":core"))
}

tasks.register("prepareKotlinBuildScriptModel"){}