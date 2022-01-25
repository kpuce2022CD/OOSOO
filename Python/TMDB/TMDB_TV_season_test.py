import requests
import json

##소프라노스, 왕좌의 게임

for i in range(1398, 1400):
    try:
        print()
        url = "https://api.themoviedb.org/3/tv/{}/watch/providers?api_key=10a7f29e3ddeda7d28ed875cac1a069f".format(i)
        response = requests.get(url)
        tv_provider_json = json.loads(response.text)
        tv_provider_result = tv_provider_json["results"]

        if "KR" in tv_provider_result:
            # TV 프로그램정보 get
            url = "https://api.themoviedb.org/3/tv/{}?api_key=10a7f29e3ddeda7d28ed875cac1a069f&language=ko-KR".format(i)
            response_sub = requests.get(url)
            tv = response_sub.text

            tv_json = json.loads(tv)

            if "name" in tv_json:

                season_num = ""
                for k in tv_json["seasons"]:
                    season_num = k["season_number"]

                    # 시즌 탐색
                    try:
                        season_url = "https://api.themoviedb.org/3/tv/" + str(i) + "/season/" + str(season_num) + "?api_key=10a7f29e3ddeda7d28ed875cac1a069f&language=ko-KR"
                        url_season = season_url.format(i)

                        response_sub2 = requests.get(url_season)
                        season = response_sub2.text

                        season_json = json.loads(season)

                        if "name" in season_json:

                            print("")
                            print("ID: " + season_json["_id"])
                            print("프로그램 이름: " + tv_json["name"] + season_json["name"])
                            print("방영일: " + season_json["air_date"])
                            print("시즌 번호: " + str(season_json["season_number"]))
                            print("설명: " + str(season_json["overview"]))
                            print("포스터패스: " + season_json["poster_path"])

                            for r in season_json["episodes"]:
                                print("air_date: " + r["air_date"])
                                print("에피소드" + str(r["episode_number"]) + " " + r["name"])
                                print("vote_average: " + str(r["vote_average"]))

                                for j in r["crew"]:
                                    print("-------CREW PRINT ------")
                                    print("department: " + j["department"])
                                    print("crew 이름: " + j["name"])
                                    print("job: " + j["job"])
                                    print("gender: " + str(j["gender"]))
                                    print("")

                                for v in r["guest_stars"]:
                                    print("----게스트 스타들----")
                                    print("게스트 이름: " + v["name"])
                                    print("게스트 설명: " + v["overview"])
                                    print("")

                    except:
                        pass

    except:
        pass