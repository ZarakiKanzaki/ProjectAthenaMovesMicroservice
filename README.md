# CoM Moves Microservice

[![codecov](https://codecov.io/gh/ZarakiKanzaki/ProjectAthenaMovesMicroservice/branch/main/graph/badge.svg?token=X0BGHSLS4G)](https://codecov.io/gh/ZarakiKanzaki/ProjectAthenaMovesMicroservice) [![ZarakiKanzaki](https://circleci.com/gh/ZarakiKanzaki/ProjectAthenaMovesMicroservice.svg?style=svg)](https://app.circleci.com/pipelines/github/ZarakiKanzaki/ProjectAthenaMovesMicroservice) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ZarakiKanzaki_ProjectAthenaMovesMicroservice&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=ZarakiKanzaki_ProjectAthenaMovesMicroservice)


## Introduction

This microservice will be used by the main api gateway in order to *display* all the Cinematic and Basic Moves. For any reference, take a look at the ***City of Mist Manual.***

### Use Cases

- A user can send a command to initiate a *Core Move*.
- A user can send a command to initiate a *Cinematic Move*.
- A user can send a command to receive the list of all *Core/Cinematic Moves* (the command will be splitted).
- A user as **admin** role can create a *Custom Move*.