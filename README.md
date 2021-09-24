# Movie-Search-Engine
### Overview
A tree based Search engine built as part of COMP6442 course work. This app searches a static IMDB database using the provided user Query. 

### Description:
We built a search engine to search a movie database using given query. The database used was a static one exported from IMDB. This database contains information like Year of Release, Director, Genre, Language of Release, Producer etc. 
We filtered the data that we felt sufficient for the application. We considered only static data for complexity purposes as the processing time is expected to be more for real time data and huge data as it is a mobile based application. 
The query entered was cleaned in-order to be used for the search engine. We forced the user to built a query using mandotory structure. 
we framed a grammar to parse throught the query. The grammar was written such that the grammar understand the query even though the keywords in the fixed structure are jumbled. Once parsed, then the query is encoded using SoundEx algorithm and used as a key value.
The data is stored as Binary Search Tree with a unqiue value generated using SoundEx algorithm for each movie record. The tree is traversed using the key values and the top 10 relevant keyv alues were displayed as query results. We came up with a new traversal strategy to find the top 10 relevant values.

Also, we built a sign in and sign up page with credentials being stored and authorised by firebase. All pages inside the app were built for Portrait and Landscape views.

Tasks:
- Built a Parser
- Built a binary Tree based Search Engine
- Pre-processed the static IMBD data
- Frammed our own grammar wrt to query language
- Implemented a compiler
- Made pages for both Landscape and Portrait viewa
- Made Sign in and Sign Up Pages
- Integrated Firebase to store credentials.
