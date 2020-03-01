#Lumberjack

## Description
Lumberjack is an ETL-pipeline. 

It extracts data from both batch sources such as
disk and streaming sources like Apache Kafka. This data is then prepared for
the Transform phase and moved to the desired output location, such as disk or
Apache Kafka. Finally, the data is loaded in the target system of choice.
The modules of lumberjack are described below.

Lumberjack will support at least the following sources and destinations:
* Disk (extract, transform, load)
* Apache Kafka (extract, transform, load)
* Apache Accumulo (load)

## lumberjack-shared
Like a Spar is used to skid other trees to the landing, this module contains
code which is used by other modules. 

## lumberjack-metrics
Much like a yarder operator, this module is used by other modules to generate
metrics.

## lumberjack-extract
Like a whistle punk is responsible for the safety of other elements further
down the pipeline, this module loads data from source and moves it after
preparation into the pipeline for further processing.   