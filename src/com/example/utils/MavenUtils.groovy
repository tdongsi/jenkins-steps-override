package com.example.utils

import org.apache.maven.model.Model

import javax.annotation.Nonnull

/**
 * Created by tdongsi on 6/3/18.
 */
class MavenUtils {
  /**
   * Based on the given POM file, find parameters that define dependency versions with SNAPSHOT.
   *
   * Example:
   * the POM file has the following strings that define SNAPSHOT dependencies
   *
   * <properties>
   *         <hbase.phoenix.client.test>1.0.0-SNAPSHOT</hbase.phoenix.client.test>
   *         <hbase.phoenix.schema>1.0.0-SNAPSHOT</hbase.phoenix.schema>
   * </properties>
   *
   * If 'hbase.phoenix.schema' is in the list 'args', then return the string
   *
   *  hbase.phoenix.schema
   *
   * @param pom: Model object representing the parsed POM file, using Pipeline step readMavenPom.
   * @param args: List of property names
   * @return List of strings
   */
  static def getVersionArgString(@Nonnull Model pom, @Nonnull args) {

    def props = pom.properties
    def params = []
    if (props) {
      for (Map.Entry entry: props) {
        String key = (String) entry.key
        String value = (String) entry.value

        if (args.contains(key) && value.contains('-SNAPSHOT')) {
          // Only add if it is SNAPSHOT version
          println "Found ${key}: ${value}"
          params.add(key)
        }
      }
    }

    // Sorting is not required but helps validation.
    return params.sort()
  }
}
