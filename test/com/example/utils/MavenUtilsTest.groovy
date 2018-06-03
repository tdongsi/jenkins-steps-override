package com.example.utils

import org.apache.maven.model.Model
import org.apache.maven.model.io.xpp3.MavenXpp3Reader
import org.junit.Test

import static org.junit.Assert.assertEquals

/**
 * Created by tdongsi on 6/3/18.
 */
class MavenUtilsTest {
  public final static List<String> ARGS_COMMON = Collections.unmodifiableList(Arrays.asList(
      'hbase.phoenix.client.test',
      'hbase.phoenix.schema',
      'hbase.phoenix.client.version',
  ))

  @Test
  void test_getVersionArgString_standardUsage() {
    // Minimal POM except the properties are from enterprise-controlplane
    def minimalPom = '''\
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>gd.wa</groupId>
    <artifactId>minimal-pom</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>minimal-pom</name>
    <url>http://maven.apache.org</url>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <dropwizard.metrics.version>4.0.2</dropwizard.metrics.version>
        <dropwizard.funnel.reporter.version>0.0.4</dropwizard.funnel.reporter.version>
        <argus.sdk.version>2.3.0</argus.sdk.version>
        <nexus.repo.id>nexus</nexus.repo.id>
        <hbase.phoenix.client.test>1.1.0-SNAPSHOT</hbase.phoenix.client.test>
        <hbase.phoenix.schema>1.0.0-SNAPSHOT</hbase.phoenix.schema>
        <hbase.phoenix.client.version>1.2.0-SNAPSHOT</hbase.phoenix.client.version>
        <spring.jdbc.version>${spring.version}</spring.jdbc.version>
        <immutables.version>2.5.5</immutables.version>
        <fasterxml.jackson.version>2.8.1</fasterxml.jackson.version>
        <wiremock.version>2.1.7</wiremock.version>
        <skip.checks>true</skip.checks>
        <skipUnitTests>false</skipUnitTests>
        <skipFunctionalTests>false</skipFunctionalTests>
        <skipIntegrationTests>true</skipIntegrationTests>
        <flyway.core.version>4.2.0</flyway.core.version>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
'''
    Model input = new MavenXpp3Reader().read(new StringReader(minimalPom))

    // expected output is sorted in alphabetical order
    assertEquals(
        MavenUtils.getVersionArgString(input, ARGS_COMMON),
        ['hbase.phoenix.client.test',
         'hbase.phoenix.schema',
         'hbase.phoenix.client.version']
    )

  }

  /**
   * None in <properties> tag matches the given list
   * POM file retrieved from https://gist.github.com/torgeir/6742158
   */
  @Test
  void test_getVersionArgString_noMatchedProperties() {
    def minimalPom = '''\
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>gd.wa</groupId>
    <artifactId>minimal-pom</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>minimal-pom</name>
    <url>http://maven.apache.org</url>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <java.version>1.8</java.version>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
'''
    Model input = new MavenXpp3Reader().read(new StringReader(minimalPom))

    // expected output is sorted in alphabetical order
    assertEquals(
        MavenUtils.getVersionArgString(input, ARGS_COMMON),
        []
    )
  }

  /**
   * No <properties> tag found in POM file
   */
  @Test
  void test_getVersionArgString_noProperties() {
    def minimalPom = '''\
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>gd.wa</groupId>
    <artifactId>minimal-pom</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
'''
    Model input = new MavenXpp3Reader().read(new StringReader(minimalPom))

    // expected output is sorted in alphabetical order
    assertEquals(
        MavenUtils.getVersionArgString(input, ARGS_COMMON),
        []
    )
  }

  /**
   * Empty list of property names
   */
  @Test
  void test_getVersionArgString_emptyList() {
    def minimalPom = '''\
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>gd.wa</groupId>
    <artifactId>minimal-pom</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
'''
    Model input = new MavenXpp3Reader().read(new StringReader(minimalPom))

    // expected output is sorted in alphabetical order
    assertEquals(
        MavenUtils.getVersionArgString(input, Collections.emptyList()),
        []
    )
  }
}
