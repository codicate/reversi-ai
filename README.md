# reversi-ai

Developed an AI for playing [reversi](https://en.wikipedia.org/wiki/Reversi), using heuristic [minimax](https://en.wikipedia.org/wiki/Minimax) with [alpha-beta pruning](https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning).

For the heuristic, we calculated base on number of keys captured and number of edge keys, as edge keys are more difficult to be captured, thus more desirable.

### Building the project:
```bash
javac *.java
```

### Running the project:
```bash
java Main
```
