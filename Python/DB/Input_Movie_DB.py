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

for i in range(90001, 100000): #100000까지 함
    try:
        # 영화 제공 플랫폼 get
        url = "https://api.themoviedb.org/3/movie/" + str(
            i) + "/watch/providers?api_key=1e7509b4953de9d2a24a16ebe05fba43"
        response = requests.get(url)
        movie_provider_json = json.loads(response.text)
        movie_provider_result = movie_provider_json["results"]

        # 한국 지원 컨텐츠 필터링
        if "KR" in movie_provider_result:
            kr_movie_provider = movie_provider_result["KR"]

            # 영화정보 get
            url = "https://api.themoviedb.org/3/movie/{}?api_key=315914a9c98a6d7345c3dc4ec25aa2c4&language=ko-KR".format(
                i)
            response_sub = requests.get(url)
            movie = response_sub.text

            movie_json = json.loads(movie)

            # 영화정보 입력
            if "title" in movie_json:

                id = "m_" + str(movie_provider_json["id"])

                type = "movie"

                title = movie_json["title"]

                genre = ""
                for k in movie_json["genres"]:
                    genre += k["name"] + ", "

                country = ""
                for j in movie_json["production_countries"]:
                    country += j["name"] + ", "

                vote_average = movie_json["vote_average"]

                vote_count = movie_json["vote_count"]

                release_date = movie_json["release_date"]
                if release_date == '':
                    release_date = '0000-00-00'

                adult = movie_json["adult"]

                poster_path = movie_json["poster_path"]

                runtime = movie_json["runtime"]

                overview = movie_json["overview"]

                subs = ""
                # 구독, 대여, 구매 정보 확인
                if "flatrate" in kr_movie_provider:
                    flatrate = kr_movie_provider["flatrate"]
                    for provider in flatrate:
                        subs += provider["provider_name"] + ", "

                rentrent = ""
                if "rent" in kr_movie_provider:
                    rent = kr_movie_provider["rent"]
                    for provider in rent:
                        rentrent += provider["provider_name"] + ", "

                buybuy = ""
                if "buy" in kr_movie_provider:
                    buy = kr_movie_provider["buy"]
                    for provider in buy:
                        buybuy += provider["provider_name"] + ", "

                # DB Input Get Cursor

                try:
                    cur = conn.cursor()
                    use_db_query = "use oosooDB"
                    insert_query = "INSERT INTO contents(id, _type, title, genre, production_countries, vote_count, vote_average, release_date, adult, poster_path, runtime, overview, flatrate, rent, buy) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "

                    cur.execute(use_db_query)
                    cur.execute(insert_query, (
                    id, type, title, genre, country, vote_count, vote_average, release_date,
                    adult, poster_path, runtime, overview, subs, rentrent, buybuy))
                    conn.commit()

                except mariadb.Error as e:
                    print(f"Error: {e}")
                    print(id)
    except:
        pass

conn.close()