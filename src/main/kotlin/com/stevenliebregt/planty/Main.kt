package com.stevenliebregt.planty

import tornadofx.App
import tornadofx.launch

class Main : App(MainView::class)

fun main(args: Array<String>) {
    launch<Main>(args)
}