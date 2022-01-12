import requests
import json

for i in range(1390, 1490):
    try:
        url = "https://api.themoviedb.org/3/tv/{}/watch/providers?api_key=315914a9c98a6d7345c3dc4ec25aa2c4".format(i)
        response = requests.get(url)
        tv_provider_json = json.loads(response.text)
        tv_provider_result = tv_provider_json["results"]

        if "KR" in tv_provider_result:
            # TV 프로그램정보 get
            url = "https://api.themoviedb.org/3/tv/{}?api_key=315914a9c98a6d7345c3dc4ec25aa2c4&language=ko-KR".format(i)
            response_sub = requests.get(url)
            tv = response_sub.text

            tv_json = json.loads(tv)

            # TV 프로그램정보 출력
            if "name" in tv_json:
                # print(tv_json)
                print("ID :", str(i))
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

            kr_tv_provider = tv_provider_result["KR"]
            # 구독, 대여, 구매 정보 확인
            if "flatrate" in kr_tv_provider:
                flatrate = kr_tv_provider["flatrate"]
                print("구독 : ", end="")
                for provider in flatrate:
                    print(provider["provider_name"], end=", ")

            if "rent" in kr_tv_provider:
                rent = kr_tv_provider["rent"]
                print("대여 : ", end="")
                for provider in rent:
                    print(provider["provider_name"], end=", ")

            if "buy" in kr_tv_provider:
                buy = kr_tv_provider["buy"]
                print("구매 : ", end="")
                for provider in buy:
                    print(provider["provider_name"], end=", ")
            print()
            print()
    except:
        pass

