package com.stevenliebregt.planty.event

import tornadofx.FXEvent
import java.io.File

class RenderEvent(val content: String?, val file: File? = null) : FXEvent()