#!/bin/bash

TEST_PORT=8000

# http://fitnesse.org/FitNesse.UserGuide.StartingAndStoppingFitNesse
java -jar fitnesse-20111026.jar -p $TEST_PORT -d . -e 0
