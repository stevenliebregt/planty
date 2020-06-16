package com.stevenliebregt.planty.preview

import javafx.scene.image.Image
import net.sourceforge.plantuml.FileFormat
import net.sourceforge.plantuml.FileFormatOption
import net.sourceforge.plantuml.SourceStringReader
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File

class Renderer {
    fun render(content: String, directory: File? = null): Image {
        if (content == "") {
            return Image(Renderer::class.java.classLoader.getResourceAsStream("default-preview.png"))
        }

        val reader = SourceStringReader(content, directory)
        val outputStream = ByteArrayOutputStream()

        reader.generateImage(outputStream, FileFormatOption(FileFormat.PNG))
        outputStream.close()

        return Image(ByteArrayInputStream(outputStream.toByteArray()))
    }
}