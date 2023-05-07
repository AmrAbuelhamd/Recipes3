package com.blogspot.soyamr.domain.common.extentions

fun String.replaceParagraphs(): String = this.replace("¶¶", "\n")
