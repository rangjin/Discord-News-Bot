tasks.jar {
	enabled = true
}

tasks.bootJar {
	enabled = false
}

dependencies {
	api("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("com.mysql:mysql-connector-j")
}

tasks.register("prepareKotlinBuildScriptModel"){}