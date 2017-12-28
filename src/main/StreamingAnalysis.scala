//import TrainModel
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.flume._
import org.apache.spark.streaming._
import org.apache.spark.mllib.classification.NaiveBayesModel
import org.apache.spark.{SparkConf, SparkContext}
object StreamingAnalysis {

	def main(args: Array[String])={
	val sparkConf=new SparkConf().setAppName("sentiment analysis naive bayes").setMaster("yarn")
		val sc=new StreamingContext(sparkConf,Seconds(5))
		val tweetStream=FlumeUtils.createStream(sc,"localhost",9988)
		val tweets=tweetStream.map(e=> new String(e.event.getBody.array))
	tweets.print()
	//train the model
//	TrainModel.train(sc.sparkContext)
	
	//local the model
	val model=NaiveBayesModel.load(sc.sparkContext,"/user/hduser/model/")
	
	//predict using model
	val pred=tweets.map(t=> (model.predict(TrainingUtils.featureVectorization(t))))
//	pred.print()
	val predText=pred.map(p=> ( p match {
	case 0=> "negative" 
	case 2=> "neutral" 
	case 4=>"positive"})) 
	predText.print()
	sc.start()
	sc.awaitTermination()
		
	}
}
