# Terra Mystica

[![Build Status](https://travis-ci.org/timvancann/terra-mystica.svg?branch=master)](https://travis-ci.org/timvancann/terra-mystica)
[![Coverage Status](https://coveralls.io/repos/github/timvancann/terra-mystica/badge.svg?branch=master)](https://coveralls.io/github/timvancann/terra-mystica?branch=master)


Terra Mystica is an attempt to model the Terra Mystica board game to scala (without expansions). It provides helper methods to figure out possible moves for a player, or A.I. to make. The goal is to experiment with different heuristics and algorithms to construct a powerful Terra Mystica player.

The generated moves are functions that map a `GameState` to a `GameState` and are, as such, lazy to reduce memory footprint.

The implemented A.I. will be a version of MCTS using the MCTS-max<sup>n</sup> approach. This is a powerful look-ahead simulation algorithm trying to find the best possible move given available moves, random play-out until the end of the game and efficient pruning of the tree.

Future versions will contain an attempt to build black-box models such as Genetic algorithms and/or Neural Nets.

An investigation is going on into the [Play Framework](https://www.playframework.com/) to see if that can be used to provide a good REST interface and possible UI for the board game.

---

# References

- [MCTS for Multiplayer games](https://project.dke.maastrichtuniversity.nl/games/files/phd/Nijssen_thesis.pdf)