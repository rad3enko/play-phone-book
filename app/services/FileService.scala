package services

import java.io.{File, PrintWriter}

import javax.inject.Singleton

import scala.io.Source

@Singleton
class FileService {

  val directoryPath = "contacts"
  val extension = ".json"

  def readById(id: String): Option[String] = {
    val file = new File(constructPath(directoryPath, id))
    if (file.exists()) {
      Some(Source.fromFile(file).getLines().reduce((l1, l2) => l1 + l2))
    } else {
      None
    }
  }

  def writeToFile(fileName: String, text: String): Boolean = {
    val directory = new File(directoryPath)
    if (!directory.exists()) directory.mkdir()

    val file = new File(constructPath(directoryPath, fileName))
    file.createNewFile()
    val writer = new PrintWriter(file)

    try {
      writer.write(text)
      writer.close()
      true
    } catch {
      case _: Throwable => false
    } finally {
      writer.close()
    }
  }

  def readAllInDirectory: Map[String, String] = {
    val directory = new File(directoryPath)
    if (!directory.exists()) directory.mkdir()
    directory.listFiles()
      .filter(file => file.isFile)
      .map(file => (file.getName ->
        readById(file.getName.substring(0, file.getName.length - extension.length)).getOrElse("")))
      .toMap
  }

  private def constructPath(dir: String, file: String): String = {
    s"${dir}${File.separator}${file}${extension}"
  }
}
