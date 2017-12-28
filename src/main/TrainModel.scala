import org.apache.spark.{SparkConf,SparkContext}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.classification.{NaiveBayes}
import java.io.InputStream
//import TrainingUtils

object TrainModel {
	def train(sc: SparkContext): Unit={
		
		//val sc=new SparkContext(sparkConf)
		//get stop words		
		//val stream: InputStream =  getClass.getResourceAsStream("stopwords.txt")

		val lines=scala.io.Source.fromFile("/home/hduser/sentimentAnalysis/src/main/scala/stopwords.txt").getLines.toSet
		val stopWords=sc.broadcast(lines).value
		

		//load training dataset from local file system
		val trainingData=scala.io.Source.fromFile("/home/hduser/sentimentAnalysis/src/main/scala/training.csv").getLines.drop(1).take(100).toList
		val trainingRDD=sc.parallelize(trainingData)
		val trainingDataSet=trainingRDD.map(lines=> TrainingUtils.toTuple(lines)).map(x=> (x._1, TrainingUtils.filterStopWords(x._2, stopWords))).map(x=> (x._1,TrainingUtils.featureVectorization(x._2))).map(x=> new LabeledPoint(x._1.toDouble, x._2))


		//train the Naive Bayes model
		val model=NaiveBayes.train(trainingDataSet,lambda=1.0, modelType="multinomial")
		
//add steps to validate model


		//save model in HDFS
		model.save(sc, "/user/hduser/model")

		//sc.stop()
	}
}
		
