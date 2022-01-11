import requests
import json
#############################
## 현재 지원 플랫폼 출력까지..  ##
#############################

# 넷플릭스 제공 플랫폼 코드 get
response = requests.get(
    "https://api.themoviedb.org/3/watch/providers/movie?api_key=1e7509b4953de9d2a24a16ebe05fba43&language=ko-KR")
provider_json = json.loads(response.text)
provider_result = provider_json["results"]
#넷플릭스 코드 탐색
for item in provider_result:
    if item["provider_name"] == "Netflix":
        netflix_id = item["provider_id"]
        print("Netflix Code : ",item["provider_id"])
        break
print()

# 넷플릭스에 포함된 영화 목록 불러오기
# Test 100
for i in range(1, 100):
    try:
        #영화 제공 플랫폼 get --> '왜 영화정보에 provider가 없을까...'
        url = "https://api.themoviedb.org/3/movie/"+str(i)+"/watch/providers?api_key=1e7509b4953de9d2a24a16ebe05fba43"
        response = requests.get(url)
        movie_provider_json = json.loads(response.text)
        movie_provider_result = movie_provider_json["results"]

        #한국 지원 컨텐츠 필터링
        if "KR" in movie_provider_result:
            kr_movie_provider = movie_provider_result["KR"]

            #영화정보 get
            url = "https://api.themoviedb.org/3/movie/{}?api_key=315914a9c98a6d7345c3dc4ec25aa2c4&language=ko-KR".format(
                i)
            response_sub = requests.get(url)
            movie = response_sub.text

            movie_json = json.loads(movie)

            #영화정보 출력
            if "title" in movie_json:
                # print(movie_json)
                print("ID :",str(i))
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

                #구독, 대여, 구매 정보 확인
                if "flatrate" in kr_movie_provider:
                    flatrate = kr_movie_provider["flatrate"]
                    print("구독 : ", end="")
                    for provider in flatrate:
                        print(provider["provider_name"], end=", ")

                if "rent" in kr_movie_provider:
                    rent = kr_movie_provider["rent"]
                    print("대여 : ", end="")
                    for provider in rent:
                        print(provider["provider_name"], end=", ")

                if "buy" in kr_movie_provider:
                    buy = kr_movie_provider["buy"]
                    print("구매 : ", end="")
                    for provider in buy:
                        print(provider["provider_name"], end=", ")

                print()
                print()

    except:
        pass
