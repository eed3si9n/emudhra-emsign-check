This is a Scala port of [CheckCompromisedCa.java](https://glaforge.dev/posts/2026/10/21/the-emudhra-ca-compromise-why-your-jvm-is-vulnerable-and-how-to-fix-it/) by Guillaume Laforge, which was adopted from Mark Struberg's original.
The it required JDK 11+, but I've updated it to work with JDK 8 as well.

To check your JDK, `sbt run`:

```bash
$ sbt run
[info] welcome to sbt 1.13.0 (Azul Systems, Inc. Java 1.8.0_402)
[info] running main
Java 1.8.0_402 (/Users/xxx/.sdkman/candidates/java/zulu8.76.0.17-ca-jdk8.0.402-macosx_aarch64/zulu-8.jdk/Contents/Home/jre)
VULNERABLE (HTTP 200) https://dv-g3b-emudhra.we-have-a-problem.com/
VULNERABLE (HTTP 200) https://dv-g3c-emudhra.we-have-a-problem.com/
VULNERABLE (HTTP 200) https://ov-g3b-emudhra.we-have-a-problem.com/
VULNERABLE (HTTP 200) https://ov-g3c-emudhra.we-have-a-problem.com/
VULNERABLE (HTTP 200) https://ev-g3b-emudhra.we-have-a-problem.com/
VULNERABLE (HTTP 200) https://ev-g3c-emudhra.we-have-a-problem.com/
VULNERABLE (HTTP 200) https://dv-g3b-aaa-emudhra.we-have-a-problem.com/
VULNERABLE (HTTP 200) https://dv-g3c-aaa-emudhra.we-have-a-problem.com/
VULNERABLE (HTTP 200) https://ov-g3b-aaa-emudhra.we-have-a-problem.com/
VULNERABLE (HTTP 200) https://ov-g3c-aaa-emudhra.we-have-a-problem.com/
VULNERABLE (HTTP 200) https://ev-g3b-aaa-emudhra.we-have-a-problem.com/
VULNERABLE (HTTP 200) https://ev-g3c-aaa-emudhra.we-have-a-problem.com/
```
