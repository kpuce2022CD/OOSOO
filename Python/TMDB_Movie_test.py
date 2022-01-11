import requests
import json

# 마지막으로 추가된 영화 번호 불러오기
response = requests.get(
    "https://api.themoviedb.org/3/movie/latest?api_key=315914a9c98a6d7345c3dc4ec25aa2c4&language=ko-KR")
latest_json = json.loads(response.text)
latest = latest_json["id"]
print(latest)
print()

# 영화 목록 불러오기
for i in range(1, 100):
    url = "https://api.themoviedb.org/3/movie/{}?api_key=315914a9c98a6d7345c3dc4ec25aa2c4&language=ko-KR".format(i)

    response = requests.get(url)
    movie = response.text

    movie_json = json.loads(movie)

    if "title" in movie_json:
        # print(movie_json)
        print(i)
        print(movie_json["title"])

        genre = ""
        for k in movie_json["genres"]:
            genre += k["name"] + ", "
        print(genre)

        country = ""
        for j in movie_json["production_countries"]:
            country += j["name"] + ", "
        print(country)

        print(movie_json["release_date"])
        print()
        print()
