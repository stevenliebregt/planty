package com.stevenliebregt.planty

import net.sourceforge.plantuml.FileFormat
import net.sourceforge.plantuml.FileFormatOption
import net.sourceforge.plantuml.SourceStringReader
import java.io.File
import java.io.FileOutputStream

class Exporter {
    fun export(format: FileFormat, content: String, file: File) {
        val reader = SourceStringReader(content)
        val outputStream = FileOutputStream(file)

        reader.generateImage(outputStream, FileFormatOption(format))
        outputStream.close()
    }
}