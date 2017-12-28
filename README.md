# SparkStreamingMLLib
Streaming Twitter Sentiment Analysis using Flume, Spark streaming and naive Bayes classification algorithm in Spark MLLib.

1. In the first step, the Naive Bayes model is trained using a large corpus of tweets classified according to sentiments.
2. Twitter statuses are then streamed and analyzed in real-time to predict their sentiments using the trained Classifier Model.

The spark-submit job has to be run with larger number of executors and cores due to it being a memory-intensive job.
Or the training and real-time classification phases could be broken down and built into different jars using SBT or Maven, as per user convenience.
