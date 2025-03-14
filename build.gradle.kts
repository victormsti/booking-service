plugins {
	java
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.bookstar"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val jjwtVersion: String by extra("0.9.1")

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation ("org.springframework.boot:spring-boot-starter-security")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	implementation("javax.xml.bind:jaxb-api:2.3.1")
	implementation ("com.fasterxml.jackson.core:jackson-databind")
	implementation ("io.jsonwebtoken:jjwt:$jjwtVersion")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// https://mvnrepository.com/artifact/jakarta.xml.bind/jakarta.xml.bind-api
	implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.2")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
