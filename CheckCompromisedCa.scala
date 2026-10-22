import java.io.IOException
import java.net.URI
import java.security.Security
import javax.net.ssl.{HttpsURLConnection, SSLException}

object CheckCompromisedCa:
  private val testUrls = List(
    "https://dv-g3b-emudhra.we-have-a-problem.com/",
    "https://dv-g3c-emudhra.we-have-a-problem.com/",
    "https://ov-g3b-emudhra.we-have-a-problem.com/",
    "https://ov-g3c-emudhra.we-have-a-problem.com/",
    "https://ev-g3b-emudhra.we-have-a-problem.com/",
    "https://ev-g3c-emudhra.we-have-a-problem.com/",
    "https://dv-g3b-aaa-emudhra.we-have-a-problem.com/",
    "https://dv-g3c-aaa-emudhra.we-have-a-problem.com/",
    "https://ov-g3b-aaa-emudhra.we-have-a-problem.com/",
    "https://ov-g3c-aaa-emudhra.we-have-a-problem.com/",
    "https://ev-g3b-aaa-emudhra.we-have-a-problem.com/",
    "https://ev-g3c-aaa-emudhra.we-have-a-problem.com/",
  )

  @main
  def main(args: String*): Unit =
    if args.toList.contains("--revocation") then
      // Must be set before the first TLS context is created
      Security.setProperty("ocsp.enable", "true")
      sys.props("com.sun.net.ssl.checkRevocation") = "true"
      sys.props("com.sun.security.enableCRLDP") = "true"
    test

  def test: Unit =
    println(s"Java ${sys.props("java.version")} (${sys.props("java.home")})")
    if testUrls.map(verifyTls).contains(false) then
      sys.error("compromised CAs are in use, or we encountered a network error")

  def verifyTls(url: String): Boolean =
    val conn = URI.create(url).toURL.openConnection().asInstanceOf[HttpsURLConnection]
    conn.setConnectTimeout(10000)
    conn.setReadTimeout(15000)
    try
      val status = conn.getResponseCode
      // Handshake succeeded -> certificate chain was accepted
      println(s"${red("VULNERABLE")} (HTTP $status) $url")
      false
    catch
      case e: IOException =>
        findSslException(e) match
          case Some(ssl) =>
            // Handshake was rejected -> problem detected
            println(s"${green("OK")} $url")
            println(s"  ${ssl.getClass.getSimpleName}: ${ssl.getMessage}")
            true
          case None =>
            // DNS, timeout, connection refused etc. -> says nothing about TLS
            println(s"${yellow("ERROR")} $url")
            println(s"  ${e.getClass.getSimpleName}: ${e.getMessage}")
            false
    finally conn.disconnect()

  def red(s: String): String =
    Console.RED + s + Console.RESET

  def green(s: String): String =
    Console.GREEN + s + Console.RESET

  def yellow(s: String): String =
    Console.YELLOW + s + Console.RESET

  // The SSL exception may be wrapped, so walk the cause chain
  def findSslException(t: Throwable): Option[SSLException] =
    Iterator
      .iterate(t)(_.getCause)
      .takeWhile(_ != null)
      .collectFirst { case ssl: SSLException => ssl }
end CheckCompromisedCa
