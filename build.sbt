name := "aws-lambda"

version := "1.0"

scalaVersion := "2.12.1"

val awsLVersion = "1.1.0"
val circeVersion = "0.7.0"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-lambda-java-core"   % awsLVersion % Provided,
  "com.amazonaws" % "aws-lambda-java-events" % awsLVersion % Provided,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion
)


    