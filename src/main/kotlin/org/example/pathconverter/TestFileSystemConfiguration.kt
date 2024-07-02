package org.example.pathconverter

import com.google.common.jimfs.Jimfs
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import java.nio.file.FileSystem
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.convert.converter.Converter
import java.nio.file.Path

@Configuration
class TestFileSystemConfiguration {
    @Bean
    fun fileSystem(): FileSystem = Jimfs.newFileSystem(com.google.common.jimfs.Configuration.unix())

    @Bean
    @Primary
    @ConfigurationPropertiesBinding
    fun fileSystemPathConverter(fileSystem: FileSystem): Converter<String, Path>
        = FileSystemPathConverter(fileSystem)

    class FileSystemPathConverter(
        val fileSystem: FileSystem,
    ) : Converter<String, Path> {
        override fun convert(source: String): Path {
            return fileSystem.getPath(source)
        }
    }
}
