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
[error] java.lang.RuntimeException: compromised CAs are in use, or we encountered a network error
[error]   at scala.sys.package$.error(package.scala:27)
[error]   at CheckCompromisedCa$.test(CheckCompromisedCa.scala:36)
[error]   at CheckCompromisedCa$.main(CheckCompromisedCa.scala:32)
[error]   at main.main(CheckCompromisedCa.scala:22)
[error]   at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
[error]   at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
[error]   at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
[error]   at java.lang.reflect.Method.invoke(Method.java:498)
[error] stack trace is suppressed; run last Compile / run for the full output
[error] (Compile / run) compromised CAs are in use, or we encountered a network error
```

## Distrust compromised CA certs manually

This repo also contains a script to distrust the compromised certs manually:

```yaml
    - name: Distrust compromised certs manually
      shell: bash
      run: |
      if [ "$RUNNER_OS" = "Windows" ]; then
        bash bin/distrust-emudhra.sh "$JAVA_HOME"
      else
        sudo -E bash bin/distrust-emudhra.sh "$JAVA_HOME"
      fi
```

## License

Licensed under the [Apache License, Version 2.0](LICENSE).
