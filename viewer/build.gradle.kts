/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2016-2017,2021  Trejkaz, Hex Project
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

apply(plugin = "java-library")

description = "Hex viewer and related components"

dependencies {
    "implementation"(platform(project(":hex-dependencies")))
    "implementation"(project(":hex-anno"))
    "implementation"(project(":hex-binary"))
    "implementation"(project(":hex-interpreter"))
    "implementation"(project(":hex-util"))
    "implementation"("com.google.code.findbugs:jsr305")
    "implementation"("org.swinglabs.swingx:swingx-action")
    "implementation"("org.swinglabs.swingx:swingx-common")
    "implementation"("org.swinglabs.swingx:swingx-core")
    "implementation"("org.swinglabs.swingx:swingx-painters")
    "implementation"("org.swinglabs.swingx:swingx-plaf")

    "testImplementation"("junit:junit")
    "testImplementation"("org.hamcrest:hamcrest")
}
