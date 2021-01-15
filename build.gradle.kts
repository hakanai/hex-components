import de.thetaphi.forbiddenapis.gradle.CheckForbiddenApisExtension
import de.thetaphi.forbiddenapis.gradle.ForbiddenApisPlugin

/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2016-2017,2021  Hakanai, Hex Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath("de.thetaphi:forbiddenapis:3.1")
    }
}

allprojects {
    repositories {
        jcenter()
        maven {
            url = uri("https://ephemeral-laboratories.bintray.com/maven")
        }
    }

    version = "0.8.1-SNAPSHOT"
    group = "org.trypticon.hex"
    extra["copyright"] = "Copyright \u00A9 2009-2014,2016-2017,2021  Hex Project"

    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    configure<PublishingExtension> {
        repositories {
            maven {
                url = uri(if (version.toString().contains("SNAPSHOT")) {
                    "https://oss.sonatype.org/content/repositories/snapshots"
                } else {
                    "https://oss.sonatype.org/service/local/staging/deploy/maven2"
                })
                credentials {
                    username = System.getenv("DEPLOY_USER")
                    password = System.getenv("DEPLOY_PASS")
                }
            }
        }

        publications {
            register<MavenPublication>("mavenJava") {
                pom {
                    name.set("Hex Components")
                    description.set("A collection of UI components for rendering binary files as hexadecimal")
                    inceptionYear.set("2009")
                    url.set("https://github.com/hakanai/hex-components")
                    licenses {
                        license {
                            name.set("GNU Lesser General Public License, Version 3")
                            url.set("https://www.gnu.org/licenses/lgpl-3.0.en.html")
                        }
                    }
                    developers {
                        developer {
                            id.set("hakanai")
                            name.set("Hakanai")
                            email.set("hakanai@ephemeral.garden")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/hakanai/hex-components.git")
                        developerConnection.set("scm:git:ssh://github.com/hakanai/hex-components.git")
                        url.set("http://github.com/hakanai/hex-components/")
                    }
                }
            }
        }
    }

    configure<SigningExtension> {
        sign(extensions.getByType<PublishingExtension>().publications["mavenJava"])
    }

    plugins.withType<JavaPlugin> {
        plugins.apply(ForbiddenApisPlugin::class)

        configure<JavaPluginExtension> {
            sourceCompatibility = JavaVersion.VERSION_11
            withJavadocJar()
            withSourcesJar()
        }

        tasks.withType<JavaCompile>().configureEach {
            // How is this still not the default, Gradle?!
            options.encoding = "UTF-8"
            options.compilerArgs = listOf("-Xlint:all")
        }

        configure<CheckForbiddenApisExtension> {
            bundledSignatures = setOf("jdk-unsafe", "jdk-deprecated", "jdk-system-out")
        }

        tasks.withType<Test>().configureEach {
            useJUnitPlatform()
        }
        dependencies {
            "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine")
        }

        tasks.named<Jar>("jar") {
            manifest {
                attributes("Copyright" to project.extra["copyright"])
            }
        }
    }
}

