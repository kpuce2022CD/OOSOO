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


for i in range(101, 150): #1~150
    try:
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

                            s_id = season_json["_id"]

                            c_id = "t_" + str(tv_provider_json["id"])

                            s_air_date = season_json["air_date"]

                            s_name = season_json["name"]

                            s_number = season_json["season_number"]

                            s_overview = season_json["overview"]

                            s_poster_path = season_json["poster_path"]

                            # contents_seasons 데이터 입력
                            try:
                                cur = conn.cursor()
                                use_db_query = "use oosooDB"
                                insert_query = "INSERT INTO contents_seasons(_id, c_id, air_date, title, number, overview, poster_path) " \
                                               "VALUES(?, ?, ?, ?, ?, ?, ?) "

                                cur.execute(use_db_query)
                                cur.execute(insert_query, (s_id, c_id, s_air_date, s_name, s_number, s_overview, s_poster_path))
                                conn.commit()

                            except mariadb.Error as e:
                                print(f"Error: {e}")


                            for r in season_json["episodes"]:
                                e_id = str(r["id"])

                                e_c_id = "t_" + str(tv_provider_json["id"])

                                e_season_num = season_json["season_number"]

                                e_air_date = r["air_date"]
                                if e_air_date == '':
                                    e_air_date = "0000-00-00"

                                e_title = r["name"]

                                e_number = r["episode_number"]

                                e_VC = r["vote_count"]

                                e_VA = r["vote_average"]

                                e_overview = r["overview"]

                                e_sp = r["still_path"]

                                crew = ""
                                for j in r["crew"]:
                                    crew += j["name"] + ", "

                                guests = ""
                                for v in r["guest_stars"]:
                                    guests += v["name"] + ", "

                                #contents_episodes 데이터 입력
                                try:
                                    cur = conn.cursor()
                                    use_db_query = "use oosooDB"
                                    insert_query = "INSERT INTO contents_episodes(_id, c_id, season_num, title, number, air_date, " \
                                                   "vote_count, vote_average, overview, still_path, crew, guests) " \
                                                   "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                                    cur.execute(use_db_query)
                                    cur.execute(insert_query,
                                                (e_id, e_c_id, e_season_num, e_title, e_number, e_air_date, e_VC, e_VA, e_overview, e_sp, crew, guests))
                                    conn.commit()

                                except mariadb.Error as e:
                                    print(f"Error: {e}")

                    except:
                        pass

    except:
        pass

conn.close()
