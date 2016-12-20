#90 Years of the Harvard Business Review

[![HBR 90 years visualization](/hbr.jpg "HBR 90 years visualization")](/hbr.png)

-> [Open the the high def picture](/hbr.png)  

`This is a visualization of 90 years of Harvard Business Review, by text mining all abstracts from the articles published in its columns *from 1922 to 2012*.`  

##How to read the poster?
The bottom of the poster shows the oldest issues of the HBR: the vocabulary used in 1922 onwards. Then, going up, we move to the present, in 5 years intervals (shown in blue).  

Making this visualization, we were struck to see *landmarks from American history* emerge: vocabulary related to the development of the Federal government in the 1920s, then the New Deal, then World War II and its aftermath, the social movements of the Sixties followed by the stagflation in the 1970s and the intensifying competition with Japan in the 1980s, up to the development of the knowledge economy in the 1990s and 2000s.  

This work is a collaboration between Clement Levallois and Valerie Alloix, in 2012. At the time, Clement was a post-doc in sociology of science at Erasmus University Rotterdam and Valerie was a freelance programmer specialized in data visualization and game development.  

*Our goal was to reveal the connection between business and society in the historical record of the HBR*. Our corpus was based on the abstracts from articles, editorials and opinion pieces available in the dataset. This selection was refined by leaving out the articles mentioning "fictional case" or "workshop event", as we found they were related to educational initiatives not of direct concern.  

We used a modified version of Clement's cowo software (https://github.com/seinecle) to process these texts in a number of steps: merging the singular and plural forms of terms ("lemmatization"), then extensive removal of the most common terms from the English language (based on a list of 5000 frequent terms), detection of terms composed of multiple words ("n-gram detection"), to finally arrive at the identification of the 10 most frequent terms for each year. Years preceding 2000 were grouped in 5 years as the HBR published less issues then.  

The next step was to manually inspect these 10 most frequent terms for each year or group of 5 years.  

We purposely removed the general vocabulary from business and management (such as "organizational behavior", "shareholder value" or "manufactured" - in total, several hundreds removed by manual inspection!) in order to leave room for terms more representative of the singular events of the given time period.  

We were happily surprised to see that this method revealed names of important players, brands, places, companies and historical events - in close connection with the business issues of their days.  

The definition of business itself appears to change, as shown by the increasing mentions of "health care" as a business topic in the recent decades. Check by yourself!  

For the visualization, we chose to facilitate the exploration of this diversity of terms through a timeline layout, showing on the picture the most frequent terms for each time interval. A handful of interesting key terms were highlighted in white to guide the reading.  

The terms surround the HBR logo, marking 90 years of publishing.  

The natural language processing (NLP) of the text was implemented in Java by Clement. The visualization was developed in Flash by Valerie. The code we created in Java and Flash for this project is freely available at: https://github.com/seinecle/90years  

[Clement Levallois](http://clementlevallois.net) and [Valerie Alloix](http://www.elimak.com)