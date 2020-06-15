package com.stevenliebregt.planty.preview

import com.stevenliebregt.planty.event.RenderEvent
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import tornadofx.*

class PreviewView : View() {
    private val renderer = Renderer()
    private lateinit var imageView: ImageView

    override val root = stackpane {
        imageView = imageview { }
    }

    init {
        subscribe<RenderEvent> { event ->
            if (event.content == null) {
                imageView.image = Image(Renderer::class.java.classLoader.getResourceAsStream("default-preview.png"))
            } else {
                imageView.image = renderer.render(event.content)
            }
        }
    }
}