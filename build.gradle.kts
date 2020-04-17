plugins {
    kotlin("jvm") version "1.3.71"
}

group = "ml.cartamc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://repo.wea-ondara.net/repository/public/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.mattmalec.Pterodactyl4J:Pterodactyl4J:1.0")
    compileOnly("net.md-5:bungeecord-api:1.15-SNAPSHOT")
    compileOnly("net.alpenblock:BungeePerms:4.0-dev-97")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}