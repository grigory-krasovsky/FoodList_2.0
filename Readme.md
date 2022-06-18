### Delete app container
docker-compose rm -f app
### Recreate app container
docker-compose up --build app

### ES
create ingredient index:

```PUT http://localhost:9200/ingredient
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
}```