/* Copyright 2022 ENGEL Austria GmbH */
package org.example.pathconverter

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import java.nio.file.FileSystem
import java.nio.file.Path

@EnableConfigurationProperties
@SpringBootTest(
    classes = [
        TestFileSystemConfiguration::class,
        TestFileSystemConfigurationTests.TestConfiguration::class,
    ],
    properties = [
        "test.filesystem.path=/tmp",
    ],
)
class TestFileSystemConfigurationTests(
    @Autowired val jimfs: FileSystem,
    @Autowired val testConfigurationWithPath: TestConfiguration,
) {
    @Configuration
    @ConfigurationProperties("test.filesystem")
    class TestConfiguration {
        lateinit var path: Path
    }

    @Test
    fun checkConfigurationPropertiesPath() {
        assertThat(testConfigurationWithPath.path.fileSystem).isSameAs(jimfs)
    }
}
