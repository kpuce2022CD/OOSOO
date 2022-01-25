import requests
import mariadb
import json
import sys

# Connect to MariaDB Platform
try:
    conn = mariadb.connect(
        user="jinho",
        password="2017150011",
        host="oosoords.cqo7bp08tx6h.ap-northeast-2.rds.amazonaws.com",
        port=3306
    )

except mariadb.Error as e:
    print(f"failed: Error connecting to Mariadb: {e}")
    sys.exit(1)

for i in range(1, 1400):
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

            # TV 프로그램정보 입력
            if "name" in tv_json:

                id = int(tv_provider_json["id"])

                title = tv_json["name"]

                genre = ""
                for k in tv_json["genres"]:
                    genre += k["name"] + ", "

                overview = tv_json["overview"]

                poster_path = tv_json["poster_path"]

                vote_average = tv_json["vote_average"]

                vote_count = tv_json["vote_count"]

                country = ""
                for j in tv_json["production_countries"]:
                    country += j["name"] + ", "

                num_seasons = tv_json["number_of_seasons"]

                num_episodes = tv_json["number_of_episodes"]

                first_air_date = tv_json["first_air_date"]

                last_air_date = tv_json["last_air_date"]

                homepage = tv_json["homepage"]

                status = tv_json["status"]

                """for u in tv_json["seasons"]:
                    print("시즌 아이디: " + str(u["id"]) + "\t\t시즌 이름: " + u["name"] + "\t\t시즌 출시일: " + u["air_date"] +
                          "\t\t시즌 설명: " + u["overview"] + "\t\t시즌의 에피소드 개수:" + str(u["episode_count"]) + "\t\t시즌 번호: " + str(u["season_number"])
                          )"""

            kr_tv_provider = tv_provider_result["KR"]
            # 구독, 대여, 구매 정보 확인

            subs = ""
            if "flatrate" in kr_tv_provider:
                flatrate = kr_tv_provider["flatrate"]
                for provider in flatrate:
                    subs += provider["provider_name"] + ", "

            rentrent = ""
            if "rent" in kr_tv_provider:
                rent = kr_tv_provider["rent"]
                for provider in rent:
                    rentrent += provider["provider_name"] + ", "

            buybuy = ""
            if "buy" in kr_tv_provider:
                buy = kr_tv_provider["buy"]
                for provider in buy:
                    buybuy += provider["provider_name"] + ", "

            # DB Input Get Cursor
            try:
                cur = conn.cursor()
                use_db_query = "use tmdb"
                insert_query = "INSERT INTO contents_tv(id, title, genre, production_countries, vote_average, vote_count, " \
                               "overview, homepage, number_of_seasons, number_of_episodes, now_status, first_air_date, last_air_date," \
                               "poster_path, flatrate, rent, buy) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "

                cur.execute(use_db_query)
                cur.execute(insert_query, (
                    id, title, genre, country,  vote_average, vote_count, overview, homepage,
                    num_seasons, num_episodes, status, first_air_date, last_air_date, poster_path, subs, rentrent, buybuy))
                conn.commit()

            except mariadb.Error as e:
                print(f"Error: {e}")
    except:
        pass

conn.close()