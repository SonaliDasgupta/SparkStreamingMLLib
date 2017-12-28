import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.linalg.Vector
//import TweetUtils
object TrainingUtils {

	val numFeatures=1000
	val hashingTF=new HashingTF(numFeatures)
	
	def toTuple(line: String)={
		val parts=line.split(",")
		val text=parts.drop(5).mkString(" ")
		(parts(0).replaceAll("^\"|\"$",""),TweetUtils.filterOnlyWords(text))
		}

	def featureVectorization(sentenceData: String): Vector={
		hashingTF.transform(sentenceData.sliding(3).toSeq) //converting text to bigrams

	}
		
	def filterStopWords(s: String, stopWords: Set[String])={
		s.toLowerCase().split("\\W+").filter(!stopWords.contains(_)).mkString(" ")
	}
}
