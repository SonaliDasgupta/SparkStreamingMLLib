object TweetUtils {
	def filterOnlyWords(text: String):String={
		text.split(" ").filter(_.matches("^[a-zA-Z0-9.]+$")).fold("")((a,b)=> a+" "+b)
	}
}
