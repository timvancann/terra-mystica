# Terra Mystica

[![Build Status](https://travis-ci.org/timvancann/terra-mystica.svg?branch=master)](https://travis-ci.org/timvancann/terra-mystica)
[![Coverage Status](https://coveralls.io/repos/github/timvancann/terra-mystica/badge.svg?branch=master)](https://coveralls.io/github/timvancann/terra-mystica?branch=master)


Terra Mystica is an attempt to model the Terra Mystica board game to scala (without expansions). It provides helper methods to figure out possible moves for a player, or A.I. to make. 

The generated moves are functions that map a `GameState` to a `GameState` and are, as such, lazy to reduce memory footprints.

The implemented A.I. is a version of MCTS using the Paranoid approach.

---

# References

- [Paranoid Search](https://project.dke.maastrichtuniversity.nl/games/files/phd/Nijssen_thesis.pdf)