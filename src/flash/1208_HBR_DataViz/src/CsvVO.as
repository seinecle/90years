package  
{
	/**
	 * ...
	 * @author valerie.elimak - blog.elimak.com
	 */
	public class CsvVO 
	{
		
		public var data 	: Array = [];
		public var header 	: Array = [];
		
		public function CsvVO( inData: String ) 
		{
			data = inData.split('$');

			var sHeader : String = data.shift();
			header = sHeader.split(',');
			
			data = data.join("").split(",");
			trace(data.length +" // "+header.length)
			data.reverse();
			header.reverse();
		}
	}

}