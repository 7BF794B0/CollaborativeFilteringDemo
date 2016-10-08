# CollaborativeFilteringDemo
Collaborative Filtering Demo on Java

The cosine measure is used as a measure of similarity:
![diagram](https://raw.githubusercontent.com/CollaborativeFilteringDemo/GFZ/master/CosineMeasure.png)
To store the results used PostgreSQL, the results are stored in JSON format.
```sql
CREATE TABLE public.person
(
    id SERIAL PRIMARY KEY NOT NULL,
    data JSON NOT NULL
);
CREATE UNIQUE INDEX person_id_uindex ON public.person (id);
```

### List of used third-party libraries:
  - [Gson](https://github.com/google/gson)
