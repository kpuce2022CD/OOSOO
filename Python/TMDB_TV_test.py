import requests
import json

# 마지막으로 추가된 TV 프로그램 번호 불러오기
response = requests.get(
    "https://api.themoviedb.org/3/tv/latest?api_key=315914a9c98a6d7345c3dc4ec25aa2c4&language=ko-KR")
latest_json = json.loads(response.text)
latest = latest_json["id"]
print(latest)
print()

# TV 프로그램 목록 불러오기
for i in range(1, 100):
    url = "https://api.themoviedb.org/3/tv/{}?api_key=315914a9c98a6d7345c3dc4ec25aa2c4&language=ko-KR".format(i)

    response = requests.get(url)
    tv = response.text

    tv_json = json.loads(tv)

    if "name" in tv_json:
        # print(tv_json)
        print(i)
        print(tv_json["name"])

        genre = ""
        for k in tv_json["genres"]:
            genre += k["name"] + ", "
        print(genre)

        country = ""
        for j in tv_json["production_countries"]:
            country += j["name"] + ", "
        print(country)

        print(tv_json["first_air_date"])
        print()
        print()
