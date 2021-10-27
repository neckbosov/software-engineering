package ui.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.awt.Desktop
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.util.*
import javax.imageio.ImageIO

fun loadNetworkImage(link: String): ImageBitmap? {
    return try {
        val url = URL(link)
        val connection = url.openConnection() as HttpURLConnection
        connection.connect()

        val inputStream = connection.inputStream
        val bufferedImage = ImageIO.read(inputStream)

        val stream = ByteArrayOutputStream()
        ImageIO.write(bufferedImage, "png", stream)
        val byteArray = stream.toByteArray()

        org.jetbrains.skija.Image.makeFromEncoded(byteArray).asImageBitmap()
    } catch (e: Exception) {
        null
    }
}
