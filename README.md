#  Min Triangle Path

Reads a text-format triangle from standard input and outputs a minimal path to the standard output.

## Requirements

- Java 8
- sbt 1.6.2
- Scala 3.1.0

## Build

Build a fat jar using:

`sbt assembly`

The file will be located at `target/scala-3.1.0/MinTrianglePath.jar`.

The jar is also available as a [release](./releases/latest).

## Run

Run the jar for interactive input using:
`java -jar MinTrianglePath.jar`

You can also feed a file using:
`cat file | java -jar MinTrianglePath.jar`

or in Windows PowerShell:
`Get-Content file | java -jar MinTrianglePath.jar`


You can also run without building using:
`sbt run`

However, feeding a file does not always work through sbt.

## Test

Some testing is available via:
`sbt test`

## Benchmark

You can benchmark the algorithm by providing a numerical argument when running. 
e.g.:
`java -jar MinTrianglePath.jar 500`

This will run the algorithm on a random triangle of the given size and print out the duration of the execution.

## Usage

The input (whether interactive or via a file) should include a valid triangle of integers.

Example:
```
    7
   6 3
  3 8 5
11 2 10 9
```

The output will be a message describing the minimal path:
`Minimal path is: 7 + 6 + 3 + 2 = 18`

Leading and trailing whitespace is optional. 

The size of the triangle can be arbitrary, though a maximum size of ~2000 is recommended to keep running time reasonable (depending on the machine).
