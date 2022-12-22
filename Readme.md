### Run docker-compose container
1) build .jar ```package -DskipTests```
2) copy .jar file to docker dir
3) docker-compose build app
4) ```docker-compose -p food-list up```

### ES
create ingredient index:

```
PUT http://localhost:9200/ingredient
Content-Type: application/json

{
  "settings": {
    "analysis": {
      "analyzer": {
        "custom_analyzer": {
          "tokenizer": "standard",
          "filter": [
            "lowercase",
            "ru_RU",
            "my_stemmer"
          ],
          "char_filter": [
            "html_strip"
          ]
        }
      },
      "filter": {
        "my_stemmer": {
          "type": "stemmer",
          "language": "russian"
        },
        "ru_RU": {
          "type": "hunspell",
          "locale": "ru_RU"
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "name": {
        "type": "text",
        "analyzer": "custom_analyzer"
      }
    }
  }
}
```
### Postgres
#### Dump
1) in container: `pg_dump -U postgres -E UTF-8 -f dump.sql`
2) copy db container id
3) docker cp containerId:/dump.sql /local/machine/path
4) 