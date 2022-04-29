# Assumptions made

## Values

> The numbers are integers (positive or negative) within the range of `Int.MinValue` (-2147483648) and `Int.MaxValue` (2147483647).

- This should be sufficient range of values.
- The code can be refactored to accept a much larger range using `Long`, but this would lead to significantly higher memory requirements.

## Paths

> The sum of any path in any provided triangle is within that range too.

- This is harder to validate beforehand, but given a rough size of up to 500 rows for the triangle, you can safely use values below 4500000 (absolute), or if you use higher values avoid having too many of them in the same path. 
- The path cost could be refactored to accept a much larger range using `Long`, but as above, there is a corresponding cost in memory usage.

## Validation feedback

> No feedback is required when invalid input is provided.

- There is no context provided for the usage of the algorithm (e.g. whether failure logs are useful).
- I chose a balance between always assuming correct input and providing full feedback. Input validation is implemented, but simply fails with a terse message.

## Project

> The exercise should be treated as a small project, with some validation, testing, code/scaldoc comments, and dealing with effects properly.

- This was chosen to showcase knowledge of Scala and cats effect.
- A working algorithm was implemented rather quickly, with the rest of the effort put in the other aspects based on this assumption.

## Efficiency

> The ability to deal with large triangles is desirable.

- A triangle of size 500 was suggested, but some optimisation and consideration for time and space complexity is typically welcome.
