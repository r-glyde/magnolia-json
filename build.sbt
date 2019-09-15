name := "json-magnolia"
version := "0.1"
scalaVersion := "2.12.10"

lazy val catsDeps = Seq(
  "cats-core",
  "cats-macros",
  "cats-kernel",
  "cats-testkit"
).map("org.typelevel" %% _ % "1.6.1")

projectDependencies ++= catsDeps ++ Seq(
  "com.propensive"      %% "magnolia"              % "0.11.0",
  "com.chuusai"         %% "shapeless"             % "2.3.3",
  "org.scalatest"       %% "scalatest"             % "3.0.8" % Test,
  "org.scalacheck"      %% "scalacheck"            % "1.14.0" % Test,
  "com.danielasfregola" %% "random-data-generator" % "2.7" % Test,
  "com.ironcorelabs"    %% "cats-scalatest"        % "2.4.1" % Test
)

scalacOptions in Compile ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-unchecked",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-language:experimental.macros",
  "-language:higherKinds",
  "-target:jvm-1.8",
  "-feature",
  "-Ypartial-unification"
)
