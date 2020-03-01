#Lumberjack

## Description
Lumberjack is an ETL-pipeline. 

It extracts data from both batch sources such as
disk and streaming sources like Apache Kafka. This data is then prepared for
the Transform phase and moved to the desired output location, such as disk or
Apache Kafka. Finally, the data is loaded in the target system of choice.

Lumberjack will support at least the following sources and destinations:
* Disk (extract, transform, load)
* Apache Kafka (extract, transform, load)
* Apache Accumulo (load)