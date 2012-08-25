package  
{
	import com.adobe.images.PNGEncoder;
	import com.elimak.HBR.VisualFC;
	import flash.display.BitmapData;
	import flash.geom.Matrix;
	import flash.net.FileReference;
	import flash.text.AntiAliasType;
	import flash.text.TextFormat;
	import flash.text.TextFormatAlign;
	import flash.utils.ByteArray;
	
	/**
	 * ...
	 * @author valerie.elimak - blog.elimak.com
	 */
	public class Visual extends VisualFC 
	{
		[Embed(source="../bin/output.csv", mimeType="application/octet-stream")]
		private var DataSource:Class;
		
		public function Visual() {
			
			var output:String = parseText();
			var b:ByteArray = new DataSource();
			var s:String = b.readUTFBytes(b.length);
			var format:TextFormat = new TextFormat();
			format.align = TextFormatAlign.JUSTIFY;

			dataText.tlfMarkup = '<TextFlow fontFamily="Arial" xmlns="http://ns.adobe.com/textLayout/2008"><p align="right">' + output + '</p></TextFlow>';
			dataText.antiAliasType = AntiAliasType.ADVANCED;
			dataText.antiAliasType = AntiAliasType.ADVANCED;
			dataText.setTextFormat(format);
			
			exportImage();
		}
		
		private function parseText(): String {
			
			var b:ByteArray = new DataSource();
			var s:String = b.readUTFBytes(b.length);
			var csvVo : CsvVO = new CsvVO(s);
			
			var result : String = "";
			for (var i:int = 0; i < csvVo.header.length; i++) {
				result += '<span color="#45B6AE" fontSize="11" fontWeight="bold">' + csvVo.header[i] + '</span>' + getWords(csvVo.data[i], csvVo.header[i]);
			}
			
			return result;
		}
		
		private function getWords( wordsInString : String, year: String ) : String {
			var dataWords	: Array = wordsInString.split("|");
			var spanWords 	: Array = [];
			
			var objHighLights : Object = { };
			objHighLights["1927"] = "world war 1";
			objHighLights["1937"] = "labor union";
			objHighLights["1942"] = "war production";
			objHighLights["1952"] = "taft hartley";
			objHighLights["1962"] = "pert";
			objHighLights["1972"] = "affirmative action";
			objHighLights["1982"] = "robot";
			objHighLights["1987"] = "economic challenges ahead";
			objHighLights["1997"] = "internet";
			objHighLights["2002"] = "terrorist attack";
			objHighLights["2007"] = "climate change";
			objHighLights["2009"] = "financial crisis";
			objHighLights["2010"] = "twitter";
			
			var color : String = "#ec725f";
			var white : String = "#ffffff";

			for (var i:int = 0; i < dataWords.length; i++) {
				
				var colorValue : String = ( objHighLights[year] == dataWords[i] )? white : color;
				var value : String = '<span color="'+colorValue+'" fontSize="11" fontWeight="bold"> ' + String(dataWords[i]).toUpperCase() + ' </span>';
				spanWords.push(value);
			}
			return spanWords.join("");
		}
		
		private function exportImage():void {
			
			/*var scale:Number = 9;
			var matrix:Matrix = new Matrix();
			matrix.scale(scale, scale);
			
			var b: BitmapData = new BitmapData( 850 *scale, 1015 *scale, false, 0xAF1E25);  
            b.draw(this, matrix, null, null, null, true);    
            var ba : ByteArray = PNGEncoder.encode(b);
     
			var fileReference:FileReference=new FileReference();
			fileReference.save(ba, "90years.png");   */
		}
	}

}