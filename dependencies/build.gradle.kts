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

plugins {
    id("my.convention.java-platform")
}

dependencies {
    constraints {
        val jsr305Version = "3.0.2"
        "api"("com.google.code.findbugs:jsr305:$jsr305Version")

        val errorProneVersion = "2.19.1"
        "api"("com.google.errorprone:error_prone_annotations:$errorProneVersion")

        val icu4jVersion = "68.2"
        "api"("com.ibm.icu:icu4j:$icu4jVersion")
        "api"("com.ibm.icu:icu4j-charset:$icu4jVersion")

        val swingXVersion = "1.6.6-SNAPSHOT"
        "api"("org.swinglabs.swingx:swingx-action:$swingXVersion")
        "api"("org.swinglabs.swingx:swingx-common:$swingXVersion")
        "api"("org.swinglabs.swingx:swingx-core:$swingXVersion")
        "api"("org.swinglabs.swingx:swingx-painters:$swingXVersion")
        "api"("org.swinglabs.swingx:swingx-plaf:$swingXVersion")

        val junitVersion = "5.8.1"
        "api"("org.junit.jupiter:junit-jupiter-api:$junitVersion")
        "api"("org.junit.jupiter:junit-jupiter-params:$junitVersion")
        "api"("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

        val hamcrestVersion = "2.2"
        "api"("org.hamcrest:hamcrest:$hamcrestVersion")

        val jmockVersion = "2.12.0"
        "api"("org.jmock:jmock:$jmockVersion")
        "api"("org.jmock:jmock-junit4:$jmockVersion")
    }
}