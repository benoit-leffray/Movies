# Available Endpoints

### Find a movie by primary or original name

http://localhost:8080/title/find-title

### Find top rated movies for a genre

http://localhost:8080/title/find-genre

### Is an actor typecasted?

http://localhost:8080/principal/is-typecasted

### Find the coincidences

http://localhost:8080/title/find-coincidence

 
## Limitations

I haven't had enough time to implemented all
the features.

- Titles are limited to 1 genre instead of maximum 3 genres.  
- I don't import all the tables with the batch jobs.  
- The batch jobs import their tsv file from the local filesystem. It would be better to download them from their well-known URI.  
- I didn't have the time to implement the 6 degrees algorithm.  

 